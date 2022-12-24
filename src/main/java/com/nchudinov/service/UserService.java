package com.nchudinov.service;

import com.nchudinov.dao.UserRepository;
import com.nchudinov.dto.UserReadDto;
import com.nchudinov.mappers.UserReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final UserReadMapper userReadMapper;
	
	public boolean delete(Long id) {
		var optUser = userRepository.findById(id);
		optUser.ifPresent(user -> userRepository.delete(id));
		return optUser.isPresent();
	}
	
	public Optional<UserReadDto> getUserById(Long id) {
		return userRepository.findById(id)
				.map(userReadMapper::mapFrom);
	}
	
}
