package com.english.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.english.dto.ReadingDto;
import com.english.entities.Reading;
import com.english.service.ReadingService;
import com.english.util.ModelMapperObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.english.entities.QuestionReading;
import com.english.repository.ReadingRepository;
import com.english.util.ExcelUtilDoc;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReadingServiceImpl implements ReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private QuestionReadingServiceImpl questionReadingService;

    @Autowired
    private ExcelUtilDoc excelUtilDoc;

    private final EntityManager entityManager;

    private final ModelMapper modelMapper;

    private final ModelMapperObject modelMapperObject;

    public ReadingServiceImpl(EntityManager entityManager, ModelMapper modelMapper, ModelMapperObject modelMapperObject) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.modelMapperObject = modelMapperObject;
    }

    @Override
    public Page<ReadingDto> findAllByPartAndLevel(int page, int size, String part, String level) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reading> query = criteriaBuilder.createQuery(Reading.class);
        Root<Reading> root = query.from(Reading.class);
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(level)) {
            predicates.add(criteriaBuilder.equal(root.get("level"), Integer.parseInt(level.trim()))) ;
        }
        if (StringUtils.hasText(part)) {
            predicates.add(criteriaBuilder.equal(root.get("part"), Integer.parseInt(part.trim())));
        }
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        int position = size * (page - 1);
        // check empty
        List<Reading> rs = entityManager.createQuery(query).getResultList();
        if (!ObjectUtils.isEmpty(rs)) {
            List<Reading> readings = entityManager.createQuery(query).setMaxResults(size).setFirstResult(position).getResultList();
            List<ReadingDto> dtos = modelMapperObject.mapToList(readings, ReadingDto.class);
            Pageable pageable = PageRequest.of(page - 1, size);
            List<Reading> lst = entityManager.createQuery(query).getResultList();
            return new PageImpl<>(dtos, pageable, lst.size());
        }
        return null;

    }

    @Override
    public void saveFile(ReadingDto dto, HttpServletRequest request) throws IOException {
        MultipartFile fileImage = dto.getPhoto();
        MultipartFile fileExcel = dto.getFileQuestion();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        Path pathImage = Paths.get(rootDirectory + "/resources/file/images/reading" + dto.getId() + ".png");
        Path pathExcel = Paths.get(rootDirectory + "/resources/file/excel/reading" + dto.getId() + ".xlsx");
        fileImage.transferTo(new File(pathImage.toString()));
        fileExcel.transferTo(new File(pathExcel.toString()));
    }

    public ReadingDto findById(long id) {

        return (ReadingDto) modelMapperObject.mapToObject(readingRepository.findById(id), ReadingDto.class);
    }

    @Override
    @Transactional
    public ReadingDto save(ReadingDto dto, HttpServletRequest request) {
        List<QuestionReading> questionReadings ;
        Reading reading = (Reading) modelMapperObject.mapToObject(dto, Reading.class);

        try {
            questionReadings = excelUtilDoc.getListCauHoiFromFileExcel(reading.getFileQuestion().getInputStream());
            if(ObjectUtils.isEmpty(dto.getId())){
                reading = readingRepository.save(reading);
            }
            Reading finalReading = reading;
            questionReadings.forEach(questionReading -> questionReading.setReading(finalReading));
            reading.setQuestionReadings(questionReadings);
            reading = readingRepository.save(reading);
            dto.setId(reading.getId());
            saveFile(dto, request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (ReadingDto) modelMapperObject.mapToObject(reading, ReadingDto.class);

    }

    @Override
    public void delete(long id) {
        readingRepository.deleteById(id);
    }

}
