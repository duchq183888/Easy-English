package com.english.rest;

import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.english.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english.dto.ResponseObject;
import com.english.dto.UserDto;

@RestController
@RequestMapping("/api/admin/account")
public class RestUserController {

	private final UserService userService;

	public RestUserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/all")
	public Page<UserDto> getByRole(@RequestParam(value = "role",required = false,defaultValue = "1") int role, @RequestParam(defaultValue = "1") int page) {
		return userService.findAll(page);
	}

	@PostMapping("/save")
	public ResponseObject save(@RequestBody @Valid UserDto dto, BindingResult result, Model model) {
		ResponseObject responseObject = new ResponseObject();

		if (dto.getId()==null && userService.findByEmail(dto.getEmail()) != null) {
			result.rejectValue("email", "error.email", "Email đã được đăng ký");
		}
		if (!dto.getConfirmPassword().equals(dto.getPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword", "Xác nhận mật khẩu không đúng");
		}

		if (result.hasErrors()) {
			setErrorsForResponseObject(result, responseObject);
		} else {
			responseObject.setStatus("success");
			userService.save(dto);
		}
		return responseObject;
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		userService.deleteById(id);
	}

	public void setErrorsForResponseObject(BindingResult result, ResponseObject object) {
		Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		object.setErrorMessages(errors);
		object.setStatus("fail");
	}
}
