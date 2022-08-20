package com.nchudinov;

import com.nchudinov.entity.*;
import com.nchudinov.util.HibernateTestUtil;
import org.junit.jupiter.api.Test;

class HibernateRunnerTest {

	@Test
	void checkDockerPostgres() {
		try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
			 var session = sessionFactory.openSession()) {
			session.beginTransaction();

			Company company = Company.builder()
					.id(1)
					.name("TestCompany")
					.build();
			session.save(company);
			
			Programmer programmer = Programmer.builder()
					.username("Ivan")
					.language(Language.JAVA)
					.company(company)
					.build();
			session.save(programmer);

			Manager manager = Manager.builder()
					.username("Sveta")
					.projectName("JavaREST")
					.company(company)
					.build();

			session.save(manager);
			session.flush();
			session.clear();

			Programmer programmer1 = session.get(Programmer.class, 1L);
			User manager1 = session.get(User.class, 2L);

			session.getTransaction().commit();
			
		}
	}

}