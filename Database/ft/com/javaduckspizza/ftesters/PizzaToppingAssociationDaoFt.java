package com.javaduckspizza.ftesters;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.PizzaToppingAssociationDao;
import com.javaduckspizza.dao.TypesDao;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.TypesVo;

public class PizzaToppingAssociationDaoFt extends BasicDaoFt {
	private SessionFactory sf;
//	private final Logger logger = new ;
	private PizzaToppingAssociationVo pizzaToppingAssociationVo;
	private PizzaToppingAssociationDao pizzaToppingAssociationDao;

	public PizzaToppingAssociationDaoFt() {
		pizzaToppingAssociationDao = new PizzaToppingAssociationDao();
	}

	public static void main(String[] args) {
		PizzaToppingAssociationDaoFt pizzaToppingsAssociationDaoFt = new PizzaToppingAssociationDaoFt();
		pizzaToppingsAssociationDaoFt.runTests();
	}
	@Override
	protected void runTests() {
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
			sf = md.getSessionFactoryBuilder().build();
			pizzaToppingAssociationVo = generatePizzaToppingAssociationVo();

			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetByPizzaId();
			testDelete();
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			if(sf != null) {
				sf.close();
			} else {
				System.err.println("Failed to build SessionFactory.");
			}
		}
	}

	private PizzaToppingAssociationVo generatePizzaToppingAssociationVo() {
		System.out.println("\ngeneratePizzaToppingAssociationVo()");
		PizzaToppingAssociationVo ptav = new PizzaToppingAssociationVo();
		ptav.setLeft(true);
		ptav.setPizzaId(12L);
		ptav.setRight(true);
		ptav.setToppingsId(9L);

		return ptav;
	}

	@Override
	protected void testInsert() {
		System.out.println("\ntestInsert():");
		Session session = null;
		try {
			session = sf.openSession();
			long id = pizzaToppingAssociationDao.insert(pizzaToppingAssociationVo, session);
			pizzaToppingAssociationVo.setId(id);
			System.out.println("insert result: " + id);
			System.out.println("New PizzaToppingAssociationVo: " + pizzaToppingAssociationVo);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testUpdate() {
		Session session = null;
		try {
			session = sf.openSession();
			PizzaToppingAssociationVo ptavClone = pizzaToppingAssociationVo.clone();
			ptavClone.setLeft(!ptavClone.getLeft());
			ptavClone.setRight(!ptavClone.getRight());
			int rowsUpdated = pizzaToppingAssociationDao.update(pizzaToppingAssociationVo, session);

			PizzaToppingAssociationVo ptavAfterUpdate =
					pizzaToppingAssociationDao.get(pizzaToppingAssociationVo.getId(), session);

			PizzaToppingAssociationVo typesVoAfterUpdate = pizzaToppingAssociationDao.get(pizzaToppingAssociationVo.getId(), session);
			System.out.println("clone: " + ptavClone);
			System.out.println("Was record updated? " + (ptavClone.equals(typesVoAfterUpdate)));
			System.out.println("After update: " + ptavAfterUpdate.toString());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testDelete() {
		System.out.println("\ntestDelete()...");
		Session session = null;
		try {
			session = sf.openSession();
			int rows = pizzaToppingAssociationDao.delete(pizzaToppingAssociationVo.getId(), session);
			System.out.println("Rows deleted: " + rows + " for ID " + pizzaToppingAssociationVo.getId());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\ntestGetByPrimaryKey()...");
		Session session = null;

		try {
			session = sf.openSession();
			System.out.println("Looking for: " + pizzaToppingAssociationVo);
			PizzaToppingAssociationVo ptavGet =
					pizzaToppingAssociationDao.get(pizzaToppingAssociationVo.getId(), session);
			System.out.println("Retrieved: " + ptavGet);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByPizzaId() {
		System.out.println("\ntestGetByPrimaryKey()...");
		Session session = null;
		try {
			session = sf.openSession();
			PizzaToppingAssociationVo ptavGet =
					pizzaToppingAssociationDao.get(pizzaToppingAssociationVo.getPizzaId(), session);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}