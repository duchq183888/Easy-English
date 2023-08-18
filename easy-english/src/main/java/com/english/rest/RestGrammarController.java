package com.english.rest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.english.dto.GrammarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.english.service.GrammarService;

@RestController
@RequestMapping("/api/admin/grammars")
public class RestGrammarController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GrammarService grammarService;

	@GetMapping("")
	public List<String> showAllGrammar() {

		List<GrammarDto> list = grammarService.getAll();

		List<String> response = new ArrayList<>();

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
			return showAllGrammar();
		}
		List<GrammarDto>  list = grammarService.search(search);
		List<String> response = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String json = "id:" + list.get(i).getId() + "," + "photo:"
					+ list.get(i).getPhoto() + "," + "name:" + list.get(i).getName();

			response.add(json);
		}

		return response;

	}

	@DeleteMapping(value = "/{id}")
	public String deleteById(@PathVariable("id") int id) {
		grammarService.delete(id);
		return "success";
	}

	@GetMapping(value = "/{id}")
	public String getById(@PathVariable("id") int id) {
		GrammarDto grammar = grammarService.getById(id);
		
		String json = "name==" + grammar.getName() + "|" + "photo=="
				+ grammar.getPhoto() + "|" + "content==" + grammar.getContentMarkDown();
		
		return json;
	}
	

	@PostMapping(value = "")
	@ResponseBody
	public List<String> insert(
			@RequestParam("file_image") MultipartFile file_image,
			@RequestParam("name") String name,
			@RequestParam("contentMarkdown") String contentMarkdown,
			@RequestParam("contentHtml") String contentHtml) {

		List<String> response = new ArrayList<>();

		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		try {
			// save file upload to local folder
			Path pathImage = Paths.get(rootDirectory + "/resources/file/images/grammar/" + file_image.getOriginalFilename());
			file_image.transferTo(new File(pathImage.toString()));
			GrammarDto dto = new GrammarDto(null,name,file_image.getOriginalFilename(),contentHtml,contentMarkdown);
			grammarService.save(dto);
		} catch (Exception e) {
			response.add(e.toString());

		}

		return response;
	}
	
	
	@PutMapping(value = "/{id}")
	@ResponseBody
	public List<String> update(
			@PathVariable("id") int id,
			@RequestPart("file_image") MultipartFile file_image,
			@RequestParam("name") String name,
			@RequestParam("contentMarkdown") String contentMarkdown,
			@RequestParam("contentHtml") String contentHtml) {

		List<String> response = new ArrayList<>();

		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		GrammarDto dto = grammarService.getById(id);
		try {
			if(!file_image.isEmpty()) {
			Path pathImage = Paths.get(rootDirectory + "/resources/file/images/grammar/" + "" + dto.getId() + "."
					+ file_image.getOriginalFilename());
			file_image.transferTo(new File(pathImage.toString()));
			dto.setPhoto(dto.getId() + "." + file_image.getOriginalFilename());
			}
			
			dto.setName(name);
			dto.setContentMarkDown(contentMarkdown);
			dto.setContentHTML(contentHtml);
			grammarService.save(dto);

		} catch (Exception e) {
			response.add(e.toString());
		}

		return response;
	}


}
