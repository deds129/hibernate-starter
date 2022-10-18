package com.nchudinov.dao;

import com.nchudinov.entity.Payment;
import com.nchudinov.entity.PersonalInfo_;
import com.nchudinov.entity.User;
import com.nchudinov.entity.User_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
		//HQL
//        return session.createQuery("select u from User u", User.class)
//				.list();
		
		//CriteriaAPI
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cbQuery  = cb.createQuery(User.class);
		Root<User> userRoot = cbQuery.from(User.class);
		cbQuery.select(userRoot);
		return session.createQuery(cbQuery)
				.list();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {
//		HQL
//        return session.createQuery("select u from User u where u.personalInfo.firstname =: firstName", User.class)
//				.setParameter("firstName", firstName)
//				.list();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cbQuery  = cb.createQuery(User.class);
		Root<User> userRoot = cbQuery.from(User.class);
		//using hibernate-jpamodelgen
		cbQuery.select(userRoot).where(cb.equal(userRoot.get(User_.personalInfo).get(PersonalInfo_.firstname), firstName));
		return session.createQuery(cbQuery)
				.list();
		
		
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
		//HQL
		//        return session.createQuery("select u from User u order by u.personalInfo.birthDate", User.class)
//				.setMaxResults(limit)
//				//.setHint(QueryHints.FETCH_SIZE, limit)
//				.list();
				return null;
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {
		//HQL
//		return session.createQuery("select u from Company c " +
//						" join c.users u " +
//						" where c.name = :compName", User.class)
//				.setParameter("compName", companyName)
//				.list();
		return null;
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
		//HQL
//        return session.createQuery("select p from Payment p " +
//				" join p.receiver u " +
//				" join u.company c where c.name = :compName " +
//				" order by u.personalInfo.firstname, p.amount", Payment.class).list();
		return null;
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
		//HQL
//        return session.createQuery("select avg(p.amount) from Payment p" +
//				//явный джоин
//				" join p.receiver u" +
//				" where u.personalInfo.firstname = :firstName" +
//				" and u.personalInfo.lastname = :lastName" ,Double.class)
//				.setParameter("firstName", firstName)
//				.setParameter("lastName", firstName)
//				.uniqueResult();
		return null;
		
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return Collections.emptyList();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
	/**
	 * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
	 * больше среднего размера выплат всех сотрудников
	 * Упорядочить по имени сотрудника
	 */
	public List<Object[]> isItPossible(Session session) {
		return session.createQuery("select u, avg(p.amount) from User u " +
						"join u.payments p " +
						"group by u " +
						"having avg(p.amount) > (select avg(p.amount) from Payment p) " +
						"order by u.personalInfo.firstname", Object[].class)
				.list();
	}

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
