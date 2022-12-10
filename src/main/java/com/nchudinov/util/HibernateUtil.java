package com.nchudinov.util;


import com.nchudinov.converter.BirthdayConverter;
import com.nchudinov.entity.Audit;
import com.nchudinov.entity.User;
import com.nchudinov.listeners.AuditTableListener;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

		SessionFactory sessionFactory = configuration.buildSessionFactory();
		//registerAudit(sessionFactory);
		return sessionFactory;
    }

	private static void registerAudit(SessionFactory sessionFactory) {
		SessionFactoryImpl sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry listenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
		AuditTableListener auditTableListener = new AuditTableListener();
		listenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
		listenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
	}

	public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Audit.class);
		configuration.addAttributeConverter(new BirthdayConverter());
        configuration.registerTypeOverride(new JsonBinaryType());
        return configuration;
    }
}
