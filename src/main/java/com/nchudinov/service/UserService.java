package com.nchudinov.service;

import com.nchudinov.entity.User;
import com.nchudinov.mappers.Mapper;
import com.nchudinov.repository.UserRepository;
import com.nchudinov.dto.UserReadDto;
import com.nchudinov.mappers.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final UserReadMapper userReadMapper;
	
	@Transactional
	public boolean delete(Long id) {
		var optUser = userRepository.findById(id);
		optUser.ifPresent(user -> userRepository.delete(id));
		return optUser.isPresent();
	}
	
	@Transactional
	public Optional<UserReadDto> findById(Long id) {
		return findById(id, userReadMapper);
	}
	
	public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
		Map<String, Object> properties = Map.of(
				GraphSemantic.LOAD.getJpaHintName(), userRepository.getEntityManager().getEntityGraph("withCompany")
		);
		return userRepository.findById(id, properties)
				.map(mapper::mapFrom);
	}
}
