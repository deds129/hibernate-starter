package com.nchudinov;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class HibernateRunner {

	public static void main(String[] args) throws SQLException {
		Configuration configuration = new Configuration();
		configuration.configure(); //path to config file

		try (SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession()) {
					
		}

	}
}
