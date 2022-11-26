package com.nchudinov;

import com.nchudinov.entity.Payment;
import com.nchudinov.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.sql.SQLException;

@Slf4j
public class HibernateRunner {

	@Transactional
	public static void main(String[] args) throws SQLException {
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession()) {
			
			// 0 - нативный способ
			session.createNativeQuery("set transaction read only").executeUpdate();
			
			// 1
			session.setDefaultReadOnly(true);
			session.beginTransaction();
			var payment = session.createQuery("select p from Payment p", Payment.class)
					// 2
							.setReadOnly(true) //предпочтительный способ
					// 3
									.setHint(QueryHints.HINT_READONLY, true);
			// 4
			session.setReadOnly(payment, true);
			session.getTransaction().commit();

		}
	}
}
