package com.english.rest;

import javax.servlet.http.HttpServletRequest;

import com.english.dto.ReadingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.english.service.impl.ReadingServiceImpl;

@RestController
@RequestMapping("/api/admin/readings")
public class RestReadingController {

    @Autowired
    private ReadingServiceImpl readingService;

    @GetMapping()
    public ResponseEntity<Page<ReadingDto>> findAllByPartAndlevel(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "") String part, @RequestParam(defaultValue = "") String level) {
        return new ResponseEntity<>(readingService.findAllByPartAndLevel(page, 5, part, level), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadingDto> findById(@PathVariable long id) {
        ReadingDto dto = readingService.findById(id);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> save(@ModelAttribute ReadingDto read, HttpServletRequest request) {
        try {
            ReadingDto reading = readingService.save(read, request);
//            readingService.saveFile(read, request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @ModelAttribute ReadingDto read, HttpServletRequest request) {
        try {
            ReadingDto reading = readingService.save(read, request);
//            readingService.saveFile(read, request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        readingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
