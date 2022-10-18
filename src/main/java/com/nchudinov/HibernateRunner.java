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

			User user = session.get(User.class, 1L);

			System.out.println(user.getUsername());
			
			session.getTransaction().commit();
        } catch (RuntimeException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
    }












}
