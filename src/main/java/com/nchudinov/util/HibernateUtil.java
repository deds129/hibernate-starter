package com.nchudinov.util;

import com.nchudinov.entity.Company;
import com.nchudinov.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
	public static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		// регистрируем классы сущностей в конфигурации
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Company.class);
		configuration.configure(); //path to config file
		return configuration.buildSessionFactory();
	}
}
