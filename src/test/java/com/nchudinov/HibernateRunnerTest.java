package com.nchudinov;

import com.nchudinov.entity.Birthday;
import com.nchudinov.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {
	
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