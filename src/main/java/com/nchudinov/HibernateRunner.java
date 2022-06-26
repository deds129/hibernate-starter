package com.nchudinov;

import com.nchudinov.entity.Birthday;
import com.nchudinov.entity.Role;
import com.nchudinov.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

	public static void main(String[] args) throws SQLException {
		Configuration configuration = new Configuration();
		//configuration.addAttributeConverter(new BirthdayConverter(), true);
		configuration.registerTypeOverride(new JsonBinaryType());
		configuration.configure(); //path to config file

		try (SessionFactory sessionFactory = configuration.buildSessionFactory();
			 //configuration.addAnnotatedClass(User.class);
		Session session = sessionFactory.openSession()) {
			
			session.beginTransaction();
			
			User user = User.builder()
					.username("Max")
					.firstname("Maximov")
					.lastname("Maximovich")
					.birthDate(new Birthday(LocalDate.of(2000, 11, 11)))
					.role(Role.ADMIN)
					.info("""
       						{
       							"name" : "Ivan",
       							"id" : 22
       						}
							""")
					.build();
			session.save(user);

			session.getTransaction().commit();
		}

	}
}
