package com.javaduckspizza.ftesters;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.CustomersDao;
import com.javaduckspizza.vo.CustomersVo;

public class CustomersDaoFt extends BasicDaoFt {
//	private SessionFactory sf;
	private Session session;
	private CustomersDao customersDao;
	private CustomersVo customersVo;

	public CustomersDaoFt() {
		customersDao = new CustomersDao();
	}

	public static void main(String[] args) {
		CustomersDaoFt cdf = new CustomersDaoFt();
		cdf.runTests();
	}

	@Override
	protected void runTests() {
		SessionFactory sf = null;
		session = null;
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
			sf = md.getSessionFactoryBuilder().build();
			session = sf.openSession();

			System.out.println("sf is " + ((sf == null) ? "null" : "not null"));
			
			customersVo = generateCustomersVo();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetByEmail();
			testDelete();
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			if(sf != null) {
				sf.close();
			} else {
				System.err.println("Failed to build SessionFactory.");
			}

			if(session != null) {
				session.close();
			}
		}
	}

	private CustomersVo generateCustomersVo() {
		CustomersVo customersVo = new CustomersVo();
		customersVo.setCity("City");
		customersVo.setEmail("user@domain.com");
		customersVo.setFirstName("First");
		customersVo.setLastName("Last");
		customersVo.setMiddleName("Middle");
		customersVo.setPhone("1234567890");
		customersVo.setState(1L);
		customersVo.setStreetAddress1("22 Acacia Ave.");
		customersVo.setStreetAddress2("");
		customersVo.setZipcode5("12345");
		customersVo.setZipcode4("1234");
		return customersVo;
	}

	@Override
	protected void testInsert() {
		System.out.println("\nIn testInsert...");
		long id = customersDao.insert(customersVo, session);
		customersVo.setId(id);
		System.out.println("customersVo: " + customersVo);
	}

	@Override
	protected void testUpdate() {
		System.out.println("\nIn testUpdate...");
		CustomersVo cvClone = customersVo.clone();
		cvClone.setPhone("1234569999");
		cvClone.setZipcode4("");
		int rows = customersDao.update(cvClone, session);
		CustomersVo cvAfterUpdate = customersDao.getById(customersVo.getId(), session);

		System.out.println("customersVo: " + customersVo);
		System.out.println("cvClone: " + cvClone);
		System.out.println("cvAfterUpdate: " + cvAfterUpdate);
	}

	@Override
	protected void testDelete() {
		System.out.println("\nIn testDelete...");
		int rows = customersDao.delete(customersVo.getId(), session);
		CustomersVo cvAfterDelete = customersDao.getById(customersVo.getId(), session);
		System.out.println("cvAfterDelete: " + cvAfterDelete);
	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\nIn testGetByPrimaryKey...");
		CustomersVo cv = customersDao.getById(customersVo.getId(), session);
		System.out.println("cv: " + cv);
	}

	protected void testGetByEmail() {
		System.out.println("\nIn testGetByEmail...");
		CustomersVo cv = customersDao.getByEmail(customersVo.getEmail(), session);
		System.out.println("cv: " + cv);
	}
}