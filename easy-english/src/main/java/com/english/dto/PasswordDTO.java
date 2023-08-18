package com.english.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
@NoArgsConstructor
public class PasswordDTO {
	@NotEmpty(message = "Phải nhập mật khẩu cũ")
	private String oldPassword;

	@NotEmpty(message = "Phải nhập mật khẩu mới")
	@Length(min = 6, max = 32, message = "Mật khẩu phải dài 6-32 ký tự")
	private String newPassword;

	@NotEmpty(message = "Phải Xác nhận mật khẩu mới")
	private String confirmNewPassword;

}
