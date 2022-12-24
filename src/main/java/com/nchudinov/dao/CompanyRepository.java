package com.nchudinov.dao;

import com.nchudinov.entity.Company;

import javax.persistence.EntityManager;

public class CompanyRepository extends RepositoryBase<Integer, Company> {

	public CompanyRepository(EntityManager entityManager) {
		super(Company.class, entityManager);
	}
}
