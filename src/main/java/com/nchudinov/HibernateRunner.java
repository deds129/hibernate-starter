package com.nchudinov;

import com.nchudinov.entity.User;
import com.nchudinov.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

public class HibernateRunner {

	public static void main(String[] args) throws SQLException {


		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
			//Entity in transient state for all sessions
			User user = User.builder()
					.username("max")
					.lastname("maximov")
					.build();

			try (Session session1 = sessionFactory.openSession()) {
				//Entity is in persist state for session1 and transient for session2
				session1.saveOrUpdate(user);
				session1.getTransaction().commit();
			}

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
				session2.getTransaction().commit();
			}

		}

	}
}
