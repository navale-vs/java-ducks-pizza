package com.javaduckspizza.ftesters;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.CustomerDao;
import com.javaduckspizza.vo.CustomerVo;

public class CustomerDaoFt extends BasicDaoFt {
	private Session session;
	private CustomerDao customerDao;
	private CustomerVo customerVo;

	public CustomerDaoFt() {
		customerDao = new CustomerDao();
	}

	public static void main(String[] args) {
		CustomerDaoFt cdf = new CustomerDaoFt();
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
			
			customerVo = generateCustomerVo();
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

	private CustomerVo generateCustomerVo() {
		CustomerVo customersVo = new CustomerVo();
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
		long id = customerDao.insert(customerVo, session);
		customerVo.setId(id);
		System.out.println("customersVo: " + customerVo);
	}

	@Override
	protected void testUpdate() {
		System.out.println("\nIn testUpdate...");
		CustomerVo cvClone = customerVo.clone();
		cvClone.setPhone("1234569999");
		cvClone.setZipcode4("");
		int rows = customerDao.update(cvClone, session);
		CustomerVo cvAfterUpdate = customerDao.getById(customerVo.getId(), session);

		System.out.println("customersVo: " + customerVo);
		System.out.println("cvClone: " + cvClone);
		System.out.println("cvAfterUpdate: " + cvAfterUpdate);
	}

	@Override
	protected void testDelete() {
		System.out.println("\nIn testDelete...");
		int rows = customerDao.delete(customerVo.getId(), session);
		CustomerVo cvAfterDelete = customerDao.getById(customerVo.getId(), session);
		System.out.println("cvAfterDelete: " + cvAfterDelete);
	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\nIn testGetByPrimaryKey...");
		CustomerVo cv = customerDao.getById(customerVo.getId(), session);
		System.out.println("cv: " + cv);
	}

	protected void testGetByEmail() {
		System.out.println("\nIn testGetByEmail...");
		CustomerVo cv = customerDao.getByEmail(customerVo.getEmail(), session);
		System.out.println("cv: " + cv);
	}
}