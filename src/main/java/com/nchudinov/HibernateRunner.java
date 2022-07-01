package com.nchudinov;

import com.nchudinov.entity.User;
import com.nchudinov.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner {
	
	public static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

	public static void main(String[] args) throws SQLException {


		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
			//Entity in transient state for all sessions
			User user = User.builder()
					.username("max")
					.lastname("maximov")
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
			try (Session session2 = sessionFactory.openSession()) {

				
				// пользователь не находится в состоянии Persist для session2, сущность не изменена в БД
				user.setFirstname("Oleg");
				
				// пользователь находится в состоянии Persist для session2, свойства объекта НЕ изменяются
				//значения в базе данных важнее
				//Dirty session
				session2.refresh(user);

				// пользователь находится в состоянии Persist для session2, свойства объекта ИЗМЕНЯЮТСЯ
				//cвойства Entity важнее
				//Clean session
				user.setFirstname("Oleg");
				session2.merge(user);

				//получить пользователя + удалить пользователя
				// Объект переходит в постоянное состояние, а после удаления переходит в удаленное состояние
				session2.delete(user);
				if (session2.getTransaction().getStatus().equals(TransactionStatus.ACTIVE))
				session2.getTransaction().commit();
			} catch (Exception e) {
				log.error("Exception occurred", e);
				throw e;
			}

		}

	}
}
