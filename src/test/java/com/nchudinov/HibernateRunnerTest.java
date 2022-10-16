package com.nchudinov;

import com.nchudinov.entity.*;
import com.nchudinov.util.HibernateTestUtil;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.Test;

import javax.persistence.FlushModeType;
import java.util.List;

class HibernateRunnerTest {
	
	@Test
	void checkHQL() {
		try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
			 var session = sessionFactory.openSession()) {
			session.beginTransaction();
			//SQL
			//select * from users
			//select u.* from users u where u.firstName = 'Sasha'
			//all actions such as SQL, operate by object mapping
			//HQL
			String name = "Amazon";
			
			List<Company> companies = session
					.createQuery(
							"select c from Company c " +
									"where c.name = :companyName", Company.class)
					.setParameter("companyName", name)
					.setFlushMode(FlushModeType.COMMIT)
					.setHint(QueryHints.FETCH_SIZE, "50") // count of objects
					.list(); //invoke chain patten
			companies.forEach(company1 -> System.err.println(company1.getName()));

			session.getTransaction().commit();

		}
	}
}