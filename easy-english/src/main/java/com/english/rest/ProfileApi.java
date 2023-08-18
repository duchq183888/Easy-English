package com.english.api.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.english.dto.UserDto;
import com.english.entities.User;
import com.english.util.ModelMapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english.dto.PasswordDTO;
import com.english.dto.ResponseObject;
import com.english.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/admin/profile")
public class ProfileApi {

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ModelMapperObject modelMapper;

	@GetMapping("/{id}")
	public UserDto getById(@PathVariable long id) {
		return userServiceImpl.findById(id);
	}

//	@PostMapping("/change-password")
//	public ResponseObject changePassword(@RequestBody @Valid PasswordDTO dto, BindingResult result, HttpServletRequest request) {
//		UserDto currentUser = (UserDto) modelMapper.mapToObject(getSessionUser(request),UserDto.class);
//		ResponseObject responseObject = new ResponseObject();
//		if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
//			result.rejectValue("oldPassword", "error.oldPassword", "Mật khẩu cũ không đúng");
//		}
//		if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
//			result.rejectValue("confirmNewPassword", "error.confirmNewPassword", "Xác nhận mật khẩu mới không đúng");
//		}
//		if (result.hasErrors()) {
//			Map<String, String> errors = new HashMap<>();
//			List<FieldError> errorsList = result.getFieldErrors();
//			for (FieldError error : errorsList) {
//				errors.put(error.getField(), error.getDefaultMessage());
//			}
//			responseObject.setErrorMessages(errors);
//			responseObject.setStatus("fail");
//		} else {
//			userServiceImpl.changePassword(currentUser, dto.getNewPassword());
//			responseObject.setStatus("success");
//		}
//		return responseObject;
//	}
//
//	public User getSessionUser(HttpServletRequest request) {
//		return (User) request.getSession().getAttribute("loggedInUser");
//	}
}
