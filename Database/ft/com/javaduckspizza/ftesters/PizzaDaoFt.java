package com.javaduckspizza.ftesters;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.PizzaDao;
import com.javaduckspizza.vo.PizzaVo;

public class PizzaDaoFt extends BasicDaoFt {
	private SessionFactory sf;
//	private final Logger logger = new ;
	private PizzaVo pizzaVo;

	public PizzaDaoFt() {
	}

	public static void main(String[] args) {
		PizzaDaoFt pizzaDaoFt = new PizzaDaoFt();
		pizzaDaoFt.runTests();
	}

	@Override
	protected void runTests() {
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
			sf = md.getSessionFactoryBuilder().build();

			System.out.println("sf is " + ((sf == null) ? "null" : "not null"));

			pizzaVo = generatePizza();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetByOrderId();
			testGetByOrderIdAndStatus();
			testGetByStatus();
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

	protected PizzaVo generatePizza() {
		PizzaVo pv = new PizzaVo();
		pv.setCrust(1L);
		pv.setId(1L);
		pv.setOrderId(1L);
		pv.setPrice(BigDecimal.valueOf(8.09));
		pv.setSauce(1L);
		pv.setSize(1L);
		pv.setStatus(1L);

		return pv;
	}

	@Override
	protected void testInsert() {
		Session session = null;
		try {
			session = sf.openSession();
			PizzaDao pd = new PizzaDao();
			long pizzaId = pd.insert(pizzaVo, session);
			pizzaVo.setId(pizzaId);
			System.out.println("insert result: " + pizzaId);
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
			PizzaDao pizzaDao = new PizzaDao();
			PizzaVo updatedPizzaVo = pizzaVo.clone();
			updatedPizzaVo.setCrust(5L);
			int result = pizzaDao.update(updatedPizzaVo, session);
			System.out.println("update result: " + result);

			testGetByPrimaryKey();
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
			session = sf.openSession();
			PizzaDao pd = new PizzaDao();
			int result = pd.delete(pizzaVo.getId(), session);
			System.out.println("delete result: " + result);
		} finally {
			if(session != null) {
				session.close();
			}
		}	
	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\nIn testGetByPrimaryKey()...");
		Session session = null;

		try {
			PizzaDao pizzaDao = new PizzaDao();
			session = sf.openSession();
			PizzaVo pvGet = pizzaDao.get(pizzaVo.getId(), session);
			System.out.println("When getting by id, pizzaVo is : " + ((pvGet == null) ? "null." : pvGet.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByOrderId() {
		Session session = null;
		try {
			PizzaDao td = new PizzaDao();
			session = sf.openSession();
			List<PizzaVo> lstPv = td.getByOrderId(pizzaVo.getOrderId(), session);
			System.out.println("When getting by order id, pizzaVo is : " + ((lstPv == null) ? "null." : lstPv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByOrderIdAndStatus() {
		Session session = null;
		try {
			PizzaDao pd = new PizzaDao();
			session = sf.openSession();
			List<PizzaVo> lstPizzaVo = pd.getByOrderIdAndStatus(pizzaVo.getId(), pizzaVo.getStatus(), session);
			System.out.println("When getting by order id and status, PizzaVo is : " + ((lstPizzaVo == null) || lstPizzaVo.isEmpty() ? "null." : lstPizzaVo.get(0).toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByStatus() {
		Session session = null;
		try {
			PizzaDao td = new PizzaDao();
			session = sf.openSession();
			List<PizzaVo> lstPv = td.getByStatus(pizzaVo.getId(), session);
			System.out.println("When getting by status, PizzaVo is : " + ((lstPv == null) ? "null." : lstPv.get(0).toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}