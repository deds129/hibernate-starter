package com.nchudinov.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
	public static SessionFactory buildSessionFactory() {
	Configuration configuration = new Configuration();
		configuration.configure(); //path to config file
		return configuration.buildSessionFactory();
	}
}
