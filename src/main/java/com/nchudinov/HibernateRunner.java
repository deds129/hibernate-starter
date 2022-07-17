package com.nchudinov;

import com.nchudinov.entity.Birthday;
import com.nchudinov.entity.Company;
import com.nchudinov.entity.PersonalInfo;
import com.nchudinov.entity.User;
import com.nchudinov.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

	public static void main(String[] args) throws SQLException {

		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
			try (Session session1 = sessionFactory.openSession()) {
				session1.beginTransaction();

				// сначала сохраняем компанию
				Company company = Company.builder()
						.name("Amazon")
						.build();
				session1.save(company);

				User user = User.builder()
						.username("IvanGrig")
						.personalInfo(PersonalInfo.builder()
								.firstname("Oleg")
								.lastname("Grigoriev")
								.birthDate(new Birthday(LocalDate.of(1998, 12, 1)))
								.build())
						.company(company)
						.build();
				session1.save(user);
				session1.get(User.class, 1L);
			//	Company company = session1.get(Company.class, 1);

				session1.getTransaction().commit();
			}
		}
	}

}
