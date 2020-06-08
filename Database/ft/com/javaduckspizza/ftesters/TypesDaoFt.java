package com.javaduckspizza.ftesters;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.dao.TypesDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.TypesVo;

public class TypesDaoFt extends BasicDaoFt {
//	private SessionFactory sf;
//	private final Logger logger = new ;
	private TypesVo typesVo;

	public static void main(String[] args) {
		TypesDaoFt typesDaoFt = new TypesDaoFt();
		typesDaoFt.runTests();
	}

	@Override
	protected void runTests() {
		try {
			//hardcoding is bad, put filename in a properties file
//			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("../properties/hibernate.cfg.xml")).build();
//			Configuration configuration = getConfiguration();
//			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
//			sessionFactory = md.getSessionFactoryBuilder().build();
//			sf = configuration.buildSessionFactory();
//			sf.get

//			System.out.println("sf is " + ((sf == null) ? "null" : "not null"));

			typesVo = generateTypesVo();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetBySequenceCodeId();
			testGetByCategory();
			testDelete();
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
//			if(sf != null) {
//				sf.close();
//			} else {
//				System.err.println("Failed to build SessionFactory.");
//			}
		}
	}

	protected TypesVo generateTypesVo() {
		TypesVo typesVo = new TypesVo();
		typesVo.setActive(true);
		typesVo.setCategory("TEST");
		typesVo.setDescription("FT TEST DESCRIPTON");
		typesVo.setSequenceCode("TEST_0001");
		return typesVo;
	}

	@Override
	protected void testInsert() {
		Session session = null;
		try {
			session = SessionUtil.getInstance().openSession();
			TypesDao typesDao = new TypesDao();
			long id = typesDao.insert(typesVo, session);
			System.out.println("New TypesVo record has ID: " + id);
			typesVo.setId(id);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testUpdate() {
		System.out.println("Calling update");
		Session session = null;
		try {
			session = SessionUtil.getInstance().openSession();
			TypesDao typesDao = new TypesDao();
			TypesVo typesVoClone = typesVo.clone();
			typesVoClone.setActive(false);
			int updatedRows = typesDao.update(typesVoClone, session);

			TypesVo typesVoAfterUpdate = typesDao.get(typesVo.getId(), session);
			System.out.println("typesVoClone: " + typesVoClone);
			System.out.println("Was record updated? " + (typesVoClone.equals(typesVoAfterUpdate)));
			System.out.println("After update: " + typesVoAfterUpdate.toString());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\nIn getByPrimaryKey()...");
		Session session = null;
		try {
			TypesDao td = new TypesDao();
			session = SessionUtil.getInstance().openSession();
			TypesVo tv = td.get(typesVo.getId(), session);
			System.out.println("For id " + typesVo.getId() + ", tv is: " + ((tv == null) ? "null." : tv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetBySequenceCodeId() {
		Session session = null;
		try {
			TypesDao td = new TypesDao();
			session = SessionUtil.getInstance().openSession();
			TypesVo tv = td.getBySequenceCode(typesVo.getSequenceCode(), session);
			System.out.println("tv is : " + ((tv == null) ? "null." : tv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByCategory() {
		Session session = null;
		try {
			TypesDao td = new TypesDao();
			session = SessionUtil.getInstance().openSession();
			List<TypesVo> lstTypesVos = td.getByCategory(typesVo.getCategory(), session);
			System.err.println("lstTypesVos is : " + ((lstTypesVos == null) ? "null." : " not null."));
			for (TypesVo typesVo : lstTypesVos) {
				System.out.println(typesVo.toString());
			}
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testDelete() {
		Session session = null;
		try {
			session = SessionUtil.getInstance().openSession();
			TypesDao typesDao = new TypesDao();
			int rows = typesDao.delete(typesVo.getId(), session);
			System.out.println("Rows deleted: " + rows + " for ID " + typesVo.getId());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	//handling configuration in the code in order to avoid spending a ton of time trying to figure out how to use a file
//	private Configuration getConfiguration() {
//		Configuration configuration = new Configuration();
//		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
//		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//		configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/JAVADUCKS_PIZZA");
//		configuration.setProperty("hibernate.connection.username", "pgroot");
//		configuration.setProperty("hibernate.connection.password", "This_1_is_PGROOTs_Password.");
//		configuration.setProperty("hibernate.connection.pool_size", "5");
//		configuration.setProperty("hibernate.connection.autocommit", "false");
//		configuration.setProperty("hibernate.show_sql", "true");
//
//		configuration.addAnnotatedClass(CustomerVo.class);
//		configuration.addAnnotatedClass(ModifierVo.class);
//		configuration.addAnnotatedClass(OrdersVo.class);
//		configuration.addAnnotatedClass(PizzaToppingAssociationVo.class);
//		configuration.addAnnotatedClass(PizzaVo.class);
//		configuration.addAnnotatedClass(TypesModifierAssociationVo.class);
//		configuration.addAnnotatedClass(TypesVo.class);
//
//		return configuration;
//	}
}
