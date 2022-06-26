package com.nchudinov;

import com.nchudinov.entity.Birthday;
import com.nchudinov.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.xml.transform.Result;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {
	
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
				.username("Max")
				.firstname("Maximov")
				.lastname("Maximovich")
				.birthDate(new Birthday(LocalDate.of(2000, 11, 11)))
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