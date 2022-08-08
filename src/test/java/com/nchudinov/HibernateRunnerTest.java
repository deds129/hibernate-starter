package com.nchudinov;

import com.nchudinov.entity.*;
import com.nchudinov.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {


	@Test
	void localeInfo() {
		try (var sessionFactory = HibernateUtil.buildSessionFactory();
			 var session = sessionFactory.openSession()) {
			session.beginTransaction();

			Company company = session.get(Company.class, 1);
			
			company.getUsers().forEach((k,v) -> System.err.println(k + " : " + v));
			session.getTransaction().commit();
		}
	}

	@Test
	void checkManyToMany() {
		try (var sessionFactory = HibernateUtil.buildSessionFactory();
			 var session = sessionFactory.openSession()) {
			session.beginTransaction();

			var user = session.get(User.class, 1L);
			var chat = session.get(Chat.class, 1);
			
			var userChat = UsersChat.builder()
							.createdAt(Instant.now())
									.createdBy(user.getUsername())
											.build();
			userChat.setUser(user);
			userChat.setChat(chat);
			session.save(userChat);
			
			session.getTransaction().commit();
		}
	}

	@Test
	void checkOneToOne() {
		try (var sessionFactory = HibernateUtil.buildSessionFactory();
			 var session = sessionFactory.openSession()) {
			session.beginTransaction();

            var user = User.builder()
                    .username("test24gmail.com")
                    .build();
            var profile = Profile.builder()
                    .language("ru")
                    .street("Kolasa 18")
                    .build();
			profile.setUser(user);
			
            session.save(user);
            

			session.getTransaction().commit();
		}
	}

	@Test
	void checkOrphanRemoval() {
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession()) {

			session.beginTransaction();
			Company company = session.getReference(Company.class, 1); //получаем прокcи-объект
		//	company.getUsers().removeIf(user -> user.getId().equals(1L));

			session.getTransaction().commit();
		}
	}
	
	@Test
	void checkLazyInitialization() {
		Company company = null;
		try ( var sessionFactory = HibernateUtil.buildSessionFactory();
			  var session = sessionFactory.openSession()) {

			Transaction transaction = session.beginTransaction();
			company = session.get(Company.class,1);

			transaction.commit();
		}

		var	users = company.getUsers();
		System.out.println(users.size());
		
	}
	
	@Test
	void addUserToNewCompany(){
		//закрываем ресурсы без try-catch
		@Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
		@Cleanup var session = sessionFactory.openSession();
//		var company = Company.builder()
//					.name("FaceBook")
//						.build();

		User user = User.builder()
				.username("newUser")
				.personalInfo(PersonalInfo.builder()
						.firstname("Olga")
						.lastname("Bobova")
						.birthDate(new Birthday(LocalDate.of(1988,12,1)))
						.build())
				.build();
		session.beginTransaction();
		//сохраняется компания, затем пользователь
		Company company = session.get(Company.class, 1);
		company.addUser(user);
		
		//session.save(company);
		
		session.getTransaction().commit();
		System.out.println(company);
	}
	
	@Test
	void oneToMany() {
		//закрываем ресурсы без try-catch
		@Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
		@Cleanup var session = sessionFactory.openSession();
		var company = session.get(Company.class,1);
		System.out.println(company);
	}
	
	@Test
	void checkGetReflection() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.getString("username");
		resultSet.getString("firstname");
		resultSet.getString("lastname");

		Class<User> clazz = User.class;
		Constructor<User> constructor = clazz.getConstructor();
		User user = constructor.newInstance();
		Field username = clazz.getDeclaredField("username");
		username.setAccessible(true);
		username.set(user, resultSet.getString("username"));
	}
	
	@Test
	void checkReflectionApi() throws SQLException, IllegalAccessException {
		User user = User.builder()
				.username("max")
				.personalInfo(PersonalInfo.builder()
						.firstname("Max")
						.lastname("Maximov")
						.birthDate(new Birthday(LocalDate.of(2000,01,01)))
						.build())
				.build();
		
		String sql = """
							insert
							into
							%s
							(%s)
							values
							(%s)
				""";
		String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
				.map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
				.orElse(user.getClass().getName());

		Field[] declaredFields = user.getClass().getDeclaredFields();
		
		String columnNames = Arrays.stream(declaredFields)
				.map(field -> ofNullable(field.getAnnotation(Column.class))
				.map(Column::name)
				.orElse(field.getName()))
				.collect(joining(","));
		
		String columnValues = Arrays.stream(declaredFields)
						.map(field -> "?")
						.collect(joining(","));

		System.out.println(sql.formatted(tableName, columnNames, columnValues));

		Connection connection = null;
		PreparedStatement preparedStatement =  connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
		for (Field field : declaredFields) {
			field.setAccessible(true);
			preparedStatement.setObject(1, field.get(user));
		}
	
	}

}