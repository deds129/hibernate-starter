package com.nchudinov;

import com.nchudinov.entity.Payment;
import com.nchudinov.util.HibernateUtil;
import com.nchudinov.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

@Slf4j
public class HibernateRunner {

	@Transactional
	public static void main(String[] args)  {
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession()) {
			
			TestDataImporter.importData(sessionFactory); 
			session.beginTransaction();
			
			var payment = session.find(Payment.class, 1L);
			payment.setAmount(payment.getAmount() * 2);
			session.save(payment);
			
			session.getTransaction().commit();
		}
	}
}
