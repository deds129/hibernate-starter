package com.nchudinov.util;

import com.nchudinov.entity.Company;
import com.nchudinov.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

/*
configure testcontainers
 */
@UtilityClass
public class HibernateTestUtil {
	
	private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");
	
	// once start when we start tests
	static {
		postgres.start();
	}


	public static SessionFactory buildSessionFactory() {
		Configuration configuration = HibernateUtil.getBuildConfiguration();
		configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
		configuration.setProperty("hibernate.connection.username", postgres.getUsername());
		configuration.setProperty("hibernate.connection.password", postgres.getPassword());
		return configuration.buildSessionFactory();
	}
}
