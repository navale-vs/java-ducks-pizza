package com.javaduckspizza.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.javaduckspizza.vo.CustomerVo;
import com.javaduckspizza.vo.LoginVo;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesModifierAssociationVo;
import com.javaduckspizza.vo.TypesVo;

public class SessionUtil {
	private static final SessionUtil instance = new SessionUtil();
	private SessionFactory sessionFactory;

	public static SessionUtil getInstance() {
		return instance;
	}

	public Session openSession() {
		Session session = null;

		try {
			session = sessionFactory.openSession();
		} catch(Throwable t) {
			t.printStackTrace();

			if (session != null) {
				session.close();
			}
		}

		return session;
	}

	private SessionUtil() {
		init();
	}

	//handling configuration in the code in order to avoid spending time trying to figure out where to put
	//a properties file on the server.
	private void init() {
		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/JAVADUCKS_PIZZA");
		configuration.setProperty("hibernate.connection.username", "pgroot");
		configuration.setProperty("hibernate.connection.password", "This_1_is_PGROOTs_Password.");
		configuration.setProperty("hibernate.connection.pool_size", "5");
		configuration.setProperty("hibernate.connection.autocommit", "false");
		configuration.setProperty("hibernate.show_sql", "true");

		configuration.addAnnotatedClass(CustomerVo.class);
		configuration.addAnnotatedClass(LoginVo.class);
		configuration.addAnnotatedClass(ModifierVo.class);
		configuration.addAnnotatedClass(OrdersVo.class);
		configuration.addAnnotatedClass(PizzaToppingAssociationVo.class);
		configuration.addAnnotatedClass(PizzaVo.class);
		configuration.addAnnotatedClass(TypesModifierAssociationVo.class);
		configuration.addAnnotatedClass(TypesVo.class);

		sessionFactory = configuration.buildSessionFactory();
	}
}