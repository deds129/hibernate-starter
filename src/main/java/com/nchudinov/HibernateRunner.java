package com.nchudinov;

import com.nchudinov.entity.Birthday;
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
			//Entity in transient state for all sessions
			User user = User.builder()
					.username("max")
					.personalInfo(PersonalInfo.builder()
							.firstname("Max")
							.lastname("Maximov")
							.birthDate(new Birthday(LocalDate.of(2000,01,01)))
							.build())
					.build();
			log.info("User entity in transient state for all sessions, object {}", user);
			Session session1 = sessionFactory.openSession();
			try (session1)  {
				Transaction transaction = session1.beginTransaction();
				log.trace("Transaction is created, {}", transaction);
				
				log.trace("Entity is in state {}, session {}", user, session1);
				session1.saveOrUpdate(user);
				session1.getTransaction().commit();
			}

			log.warn("User is in detached state: {}, session is closed {}", user, session1);
			
			try(Session session2 = sessionFactory.openSession()) {
				PersonalInfo key = PersonalInfo.builder()
						.firstname("Oleg")
						.lastname("Olegov")
						.birthDate(new Birthday(LocalDate.of(2000, 01, 01)))
						.build();

				User user1 = session2.get(User.class, key);

				System.out.println();
			}

		}

	}
}
