package com.english.service.impl;

import javax.transaction.Transactional;

import com.english.dto.UserDto;
import com.english.entities.User;
import com.english.service.UserService;
import com.english.util.ModelMapperObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.english.entities.Role;
import com.english.repository.UserRepository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository repo;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ModelMapperObject modelMapper;

	public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapperObject modelMapper) {
		this.repo = repo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.modelMapper = modelMapper;
	}

	@Override
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}


	@Override
	public UserDto save(UserDto dto) {
		User user = null;

		if (dto.getId() == null) {
			dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
			user = (User) modelMapper.mapToObject(dto, User.class);
		}else {
			if(ObjectUtils.isEmpty(dto.getPassword())){
				user = repo.findById(dto.getId()).get();
				user.setName(dto.getName());
				user.setAddress(dto.getAddress());
				if(dto.getEmail()!=null){
					user.setEmail(dto.getEmail());
				}
				user.setPhone(dto.getPhone());
				if(dto.getRole()!=null){
					user.setRole(dto.getRole());
				}

			}else {
				dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
				user = (User) modelMapper.mapToObject(dto, User.class);
			}
		}

		return (UserDto) modelMapper.mapToObject(repo.save(user), UserDto.class);
	}

	@Override
	public UserDto findById(long id) {
		return (UserDto) modelMapper.mapToObject(repo.findById(id), UserDto.class);
	}

	@Override
	public UserDto update(UserDto dto) {
		return null;
	}

	@Override
	public UserDto changePassword(UserDto dto, String newPassword) {
		dto.setPassword(bCryptPasswordEncoder.encode(newPassword));
		User user = (User) modelMapper.mapToObject(dto, User.class);
		return (UserDto) modelMapper.mapToObject(repo.save(user), UserDto.class);
	}


	@Override
	public Page<UserDto> findAll(int page) {
		Page<User> userPage = repo.findAll(PageRequest.of(page - 1, 6));
		List<User> users = userPage.getContent();
		List<UserDto> userDtos = modelMapper.mapToList(users, UserDto.class);
		return new PageImpl<>(userDtos, userPage.getPageable(), userPage.getTotalElements());
	}

	@Override
	public List<UserDto> findByRole(Role role) {
		List<User> users = repo.findByRole(role);
		return modelMapper.mapToList(users, UserDto.class);
	}

	@Override
	public void deleteById(long id) {
		if (!repo.existsById(id)) return;
		repo.deleteById(id);
	}
}
