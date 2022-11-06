package com.nchudinov;

import com.nchudinov.entity.User;
import com.nchudinov.util.HibernateUtil;
import com.nchudinov.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

@Slf4j
public class HibernateRunner {

	public static void main(String[] args) throws SQLException {
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession()) {
			session.beginTransaction();

//            var user = session.get(User.class, 1L);
//            System.out.println(user.getPayments().size());
//            System.out.println(user.getCompany().getName());
			var users = session.createQuery("select u from User u join fetch u.payments where 1 = 1", User.class)
					.list();
			users.forEach(user -> System.out.println(user.getPayments().size()));
			users.forEach(user -> System.out.println(user.getCompany().getName()));

			session.getTransaction().commit();
		}
	}












}
