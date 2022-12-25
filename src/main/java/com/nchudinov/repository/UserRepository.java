package com.nchudinov.repository;

import com.nchudinov.entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User> {

	public UserRepository(EntityManager entityManager) {
		super(User.class, entityManager);
	}
}
