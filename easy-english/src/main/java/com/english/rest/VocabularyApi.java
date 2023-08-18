package com.english.api.admin;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.english.dto.DetailVocabularyDto;
import com.english.dto.VocabularyLessonDto;
import com.english.util.ModelMapperObject;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english.service.ExerciseVocabularyService;
import com.english.service.DetailVocabularyService;

@RestController
@NoArgsConstructor
@RequestMapping("/api/admin/vocab")
public class VocabularyApi {

	@Autowired
	private HttpServletRequest request;

	@Autowired
    private ExerciseVocabularyService exerciseVocabularyService;
	@Autowired
	private DetailVocabularyService detailVocabularyService;

	@Autowired
	private  ModelMapperObject modelMapperObject;


	@GetMapping("/loadVocab")
	public List<String> showAllVocab() {

		List<VocabularyLessonDto> list = exerciseVocabularyService.findAll();

		List<String> response = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String json = "id:" + list.get(i).getId() + "," + "photo:"
					+ list.get(i).getPhoto() + "," + "name:" + list.get(i).getName();

			response.add(json);
		}

		return response;

	}
	@GetMapping("/search")
	public List<String> search(@RequestParam String search) {

        if(ObjectUtils.isEmpty(search)){
			return showAllVocab();
		}
		List<VocabularyLessonDto> list = exerciseVocabularyService.search(search);
		List<String> response = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String json = "id:" + list.get(i).getId() + "," + "photo:"
					+ list.get(i).getPhoto() + "," + "name:" + list.get(i).getName();

			response.add(json);
		}

		return response;

	}

	@RequestMapping(value = "/delete/{idBaiVocab}")
	public String deleteById(@PathVariable("idBaiVocab") int id) {
		exerciseVocabularyService.delete(id);
		return "success";
	}

	@PostMapping(value = "/save", consumes = "multipart/form-data")
	@ResponseBody
	public List<String> addBaiThiThu(@RequestParam("file_excel") MultipartFile file_excel,
			@RequestParam("file_image") MultipartFile file_image, @RequestParam("name") String name,
			@RequestParam("file_image_question") MultipartFile[] file_image_question,
			@RequestParam("file_listening") MultipartFile[] file_listening) {

		List<String> response = new ArrayList<String>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		VocabularyLessonDto bttuvung = new VocabularyLessonDto();


		try {
			// save file upload to local folder

			bttuvung=exerciseVocabularyService.save(bttuvung);
			Path pathExcel = Paths.get(rootDirectory + "/resources/file/excel/" + "vocab."
					+ bttuvung.getId() + "." + file_excel.getOriginalFilename());
			file_excel.transferTo(new File(pathExcel.toString()));

			Path pathImage = Paths.get(rootDirectory + "/resources/file/images/vocab/" + ""
					+ bttuvung.getId() + "." + file_image.getOriginalFilename());
			file_image.transferTo(new File(pathImage.toString()));

			bttuvung.setName(name);
			bttuvung.setPhoto(bttuvung.getId() + "." + file_image.getOriginalFilename());
			exerciseVocabularyService.save(bttuvung);


			VocabularyApi btt = new VocabularyApi();
			List<DetailVocabularyDto> dtos = btt.getListFromExcel(pathExcel.toString(), bttuvung);

			for (int i = 0; i < dtos.size(); i++) {
				detailVocabularyService.save(dtos.get(i));
			}

		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorReadFileExcel:" + e);

		}

		return response;
	}


	@RequestMapping(value = "/infoVocab/{idBaiVocab}")
	public String infoVocabById(@PathVariable("idBaiVocab") int id) {
		VocabularyLessonDto bttuvung = exerciseVocabularyService.getById(id);

		return bttuvung.getName();
	}

	@PostMapping(value = "/update")
	@ResponseBody
	public List<String> updateBaiVocab(@RequestParam("vocabId") int id, @RequestParam("name") String name,
			@RequestParam("file_image") MultipartFile file_image,
			@RequestParam("file_image_question") MultipartFile[] file_image_question,
			@RequestParam("file_listening") MultipartFile[] file_listening,
			@RequestParam("file_excel") MultipartFile file_excel) {

		List<String> response = new ArrayList<String>();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		VocabularyLessonDto bttuvung = exerciseVocabularyService.getById(id);
		
		if(!ObjectUtils.isEmpty(file_excel)){
			try {
				// save file upload to local folder
				Path pathExcel = Paths.get(rootDirectory + "/resources/file/excel/" + "vocab."
						+ bttuvung.getId() + "." + file_excel.getOriginalFilename());
				file_excel.transferTo(new File(pathExcel.toString()));

				Path pathImage = Paths.get(rootDirectory + "/resources/file/images/vocab/" + ""
						+ bttuvung.getId() + "." + file_image.getOriginalFilename());
				file_image.transferTo(new File(pathImage.toString()));

				VocabularyApi btt = new VocabularyApi();
				List<DetailVocabularyDto> detailVocabularyDtos = btt.getListFromExcel(pathExcel.toString(), bttuvung);
                bttuvung.setDetailVocabularies(detailVocabularyDtos);

			} catch (Exception e) {
				response.add(e.toString());
			}
		}
		bttuvung.setName(name);
		bttuvung.setPhoto(bttuvung.getId() + "." + file_image.getOriginalFilename());
		exerciseVocabularyService.save(bttuvung);


		return response;
	}

	public List<DetailVocabularyDto> getListFromExcel(String path_file_excel, VocabularyLessonDto bttuvung) {
		List<DetailVocabularyDto> list = new ArrayList<>();

		try {
			FileInputStream excelFile = new FileInputStream(new File(path_file_excel));
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet worksheet = workbook.getSheetAt(0);

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				DetailVocabularyDto detailVocabularyDto = new DetailVocabularyDto();

				XSSFRow row = worksheet.getRow(i);

				if (row.getCell(0) != null)
					detailVocabularyDto.setNumber((int) row.getCell(0).getNumericCellValue());

				if (row.getCell(1) != null)
					detailVocabularyDto.setContent(row.getCell(1).getStringCellValue());

				if (row.getCell(2) != null)
					detailVocabularyDto.setTranscribe(row.getCell(2).getStringCellValue());

				if (row.getCell(3) != null)
					detailVocabularyDto.setImage(row.getCell(3).getStringCellValue().toString());

				if (row.getCell(4) != null)
					detailVocabularyDto.setAudiomp3(row.getCell(4).getStringCellValue());

				if (row.getCell(5) != null)
					detailVocabularyDto.setMeaning(row.getCell(5).getStringCellValue());

				if (row.getCell(6) != null)
					detailVocabularyDto.setSentence(row.getCell(6).getStringCellValue());

				detailVocabularyDto.setExerciseVocabulary(bttuvung);

				list.add(detailVocabularyDto);
			}

		} catch (Exception e) {
			System.out.println("errorhere:" + e);
		}
		return list;

	}

}
