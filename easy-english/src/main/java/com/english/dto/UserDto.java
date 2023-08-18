package com.english.dto;

import javax.persistence.Convert;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.english.entities.Role;
import com.english.util.RoleTypeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private Long id;

	@NotEmpty(message = "Phải nhập địa chỉ email")
	@Email(message = "Phải nhập đúng địa chỉ email")
	private String email;

	@Length(min = 6, max = 32, message = "mật khẩu phải dài 6-32 ký tự")
	private String password;

	private String confirmPassword;

	@NotEmpty(message = "Địa chỉ không được trống")
	private String address;

	@NotEmpty(message = "Họ tên không được trống")
	private String name;

	@NotEmpty(message = "Số điện thoại không được trống")
	private String phone;

	private boolean loginOauth2;

	@Convert(converter = RoleTypeConverter.class)
	private Role role;



}
