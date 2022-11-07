package com.nchudinov;

import com.nchudinov.entity.User;
import com.nchudinov.entity.UserChat;
import com.nchudinov.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class HibernateRunner {

	public static void main(String[] args) throws SQLException {
		try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
			 Session session = sessionFactory.openSession()) {
			 session.beginTransaction();
			RootGraph<User> entityGraph = session.createEntityGraph(User.class);
			entityGraph.addAttributeNodes("company", "userChats");
			
			SubGraph<UserChat> subGraph = entityGraph.addSubgraph("userChats", UserChat.class);
			subGraph.addAttributeNodes("chat");

			Map<String, Object> properties = Map.of(GraphSemantic.LOAD.getJpaHintName(), entityGraph);

			var user = session.find(User.class, 1L, properties);
			System.out.println(user.getUserChats().size());
			session.getTransaction().commit();
		}
	}












}
