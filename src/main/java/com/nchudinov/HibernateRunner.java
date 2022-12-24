package com.nchudinov;

import com.nchudinov.dao.PaymentRepository;
import com.nchudinov.dao.UserRepository;
import com.nchudinov.entity.Payment;
import com.nchudinov.entity.User;
import com.nchudinov.mappers.CompanyReadMapper;
import com.nchudinov.mappers.UserReadMapper;
import com.nchudinov.service.UserService;
import com.nchudinov.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.lang.reflect.Proxy;

@Slf4j
public class HibernateRunner {

	@Transactional
	public static void main(String[] args) {
		User user = null;
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {//open session
			//Не можем исполтзовать один и тот же session.
			// Нужно создать прокси
			Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
					(proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
			
			session.beginTransaction();

			UserRepository userRepository = new UserRepository(session);
			CompanyReadMapper companyReadMapper = new CompanyReadMapper();
			UserReadMapper userReadMapper = new UserReadMapper(companyReadMapper);
			
			var usrService = new UserService(userRepository, userReadMapper);
			System.err.println(usrService.getUserById(1L));

			//close session
			session.getTransaction().commit();
		}
	}
}
