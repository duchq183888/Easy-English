package com.english.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.english.util.RoleTypeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String email;

	@JsonIgnore
	private String password;

	@Transient
	@JsonIgnore
	private String confirmPassword;

	private String name;

	private String phone;

	private String address;

	@Transient
	private boolean loginOauth2;

	@Convert(converter = RoleTypeConverter.class)
	private Role role;

}
