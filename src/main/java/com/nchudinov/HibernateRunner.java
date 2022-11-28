package com.nchudinov;

import com.nchudinov.entity.Payment;
import com.nchudinov.util.HibernateUtil;
import com.nchudinov.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

@Slf4j
public class HibernateRunner {

	@Transactional
	public static void main(String[] args)  {
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession();
			 Session session2 = sessionFactory.openSession();) {
			
			TestDataImporter.importData(sessionFactory);
			session.beginTransaction();
			session2.beginTransaction();
			
			//Optimistic lock
			var payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
			payment.setAmount(payment.getAmount() * 2);
			
			var payment2 = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
			payment2.setAmount(payment2.getAmount() * 30);

			session.getTransaction().commit();
			session2.getTransaction().commit();

		}
	}
}
