package com.nchudinov;

import com.nchudinov.entity.User;
import com.nchudinov.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

@Slf4j
public class HibernateRunner {

	@Transactional
	public static void main(String[] args)  {
		User user = null;
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession()) {
			
			//нет кэша -> запрос в БД
			user = session.find(User.class, 1L);
			
			//есть кэш первого уровня -> нет запроса в БД
			var user1 = session.find(User.class, 1L);
			user.getCompany().getName();
			session.beginTransaction();
			session.getTransaction().commit();
		}

		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session1 = sessionFactory.openSession()) {

			// Новая сессия, запроса в БД также нет, кэш - Второго уровня.
			// Получаем новый объет (Сериализация)
			var user2 = session1.find(User.class, 1L);
			//Зависимые сущности не кэшируются,
			// только значение id - нужно кэшировать компанию (она lazy)
			user2.getCompany().getName();
			session1.beginTransaction();
			session1.getTransaction().commit();
		}

	}
}
