package com.nchudinov.dao;

import com.nchudinov.entity.Payment;

import javax.persistence.EntityManager;

public class PaymentRepository extends RepositoryBase<Long, Payment> {

	public PaymentRepository(EntityManager entityManager) {
		super(Payment.class, entityManager);
	}
}