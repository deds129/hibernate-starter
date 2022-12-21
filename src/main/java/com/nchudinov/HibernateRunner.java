package com.nchudinov;

import com.nchudinov.entity.Payment;
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
			session.beginTransaction();
			
			//нет кэша -> запрос в БД
			//TestDataImporter.importData(sessionFactory);
			user = session.find(User.class, 1L);
			//есть кэш первого уровня -> нет запроса в БД
			var user1 = session.find(User.class, 1L);
			user.getCompany().getName();
			
			//Первый запрос -> в БД, 
			session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
							.setParameter("userId", 1L)
					.setCacheable(true) //setHint(QueryHints.CACHEABLE, true)
					.setCacheRegion("queries")
									.getResultList();

			System.out.println(sessionFactory.getStatistics());
			session.getTransaction().commit();
		}
		

		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session1 = sessionFactory.openSession()) {
			session1.beginTransaction();
			// Новая сессия, запроса в БД также нет, кэш - Второго уровня.
			// Получаем новый объет (Сериализация)
			var user2 = session1.find(User.class, 1L);
			//Зависимые сущности не кэшируются,
			// только значение id - нужно кэшировать компанию (она lazy)
			user2.getCompany().getName();
			
			//1) не ставили @Cache над сущностью -> Запрос на каждый id 2) Ставим @Cache -> Получение сущностей из L2 кэша
			//второй запрос -> должны обращаться в кэш
			session1.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
					.setParameter("userId", 1L)
					.setCacheable(true) //setHint(QueryHints.CACHEABLE, true)
					.setCacheRegion("queries")
					.getResultList();
			
			session1.getTransaction().commit();
		}

	}
}
