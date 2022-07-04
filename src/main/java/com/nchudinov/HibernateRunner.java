package com.nchudinov;

import com.nchudinov.entity.Birthday;
import com.nchudinov.entity.Company;
import com.nchudinov.entity.PersonalInfo;
import com.nchudinov.entity.User;
import com.nchudinov.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
	
	public static void main(String[] args) throws SQLException {
		
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
			try (Session session1 = sessionFactory.openSession())  {
				Transaction transaction = session1.beginTransaction();

				// сначала сохраняем компанию
				Company company = Company.builder()
						.name("ProgPro")
						.build();
				
				session1.saveOrUpdate(company);
				
				User user = User.builder()
						.username("max")
						.personalInfo(PersonalInfo.builder()
								.firstname("Max")
								.lastname("Maximov")
								.birthDate(new Birthday(LocalDate.of(2000,01,01)))
								.build())
						.company(company)
						.build();
				
				// ����� ���������� �������� ����� �������� ������������ ������ ����-�� �������� � ���-�� �������� ����
				// ���� �� ���������� CASCADE!!!
				session1.saveOrUpdate(user);
				
				session1.getTransaction().commit();
			}
			
		}

	}
}
