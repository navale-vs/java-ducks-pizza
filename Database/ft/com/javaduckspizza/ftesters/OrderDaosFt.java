package com.javaduckspizza.ftesters;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.OrdersDao;
import com.javaduckspizza.vo.OrdersVo;

public class OrderDaosFt extends BasicDaoFt {
	private SessionFactory sf;
	//	private final Logger logger = new ;
	private OrdersVo ordersVo;
	private OrdersDao ordersDao;

	public OrderDaosFt() {
		ordersDao = new OrdersDao();
	}

	public static void main(String[] args) {
		OrderDaosFt ordersDaoFt = new OrderDaosFt();
		ordersDaoFt.runTests();
	}

	@Override
	protected void runTests() {
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
			sf = md.getSessionFactoryBuilder().build();

			System.out.println("sf is " + ((sf == null) ? "null" : "not null"));

			ordersVo = generateOrdersVo();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetByCustomerId();
			testGetByStatusId();
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

	private OrdersVo generateOrdersVo() {
		OrdersVo ordersVo = new OrdersVo();
		ordersVo.setCustomerId(1L);
		ordersVo.setDateTimeCompleted(null);
		ordersVo.setDateTimeDue(null);
		ordersVo.setDateTimePlaced(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		ordersVo.setDateTimeReady(null);
		ordersVo.setRetrievalMethod(1L);
		ordersVo.setStatus(1L);
		ordersVo.setTotal(BigDecimal.valueOf(10.01));
		return ordersVo;
	}

	@Override
	protected void testInsert() {
		Session session = null;
		try {
			session = sf.openSession();
			long id = ordersDao.insert(ordersVo, session);
			System.out.println("New OrdersVo record has ID: " + id);
			ordersVo.setId(id);
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
			session = sf.openSession();
			OrdersVo ordersVoClone = ordersVo.clone();
			ordersVoClone.setStatus(5L);
			ordersVoClone.setDateTimeReady(new Timestamp(Calendar.getInstance().getTimeInMillis()));

			System.out.println("ordersVo: " + ordersVo);
			System.out.println("ordersVoClone: " + ordersVoClone);

			int updatedRows = ordersDao.update(ordersVoClone, session);
			OrdersVo ordersVoAfterUpdate = ordersDao.get(ordersVo.getId(), session);
			System.out.println("After update: " + ordersVoAfterUpdate.toString());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testGetByPrimaryKey() {
		Session session = null;
		try {
			session = sf.openSession();
			OrdersVo ov = ordersDao.get(ordersVo.getId(), session);
			System.out.println("For id ov is: " + ((ov == null) ? "null." : ov.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByStatusId() {
		Session session = null;
		try {
			session = sf.openSession();
			List<OrdersVo> lstOrdersVos = ordersDao.getByStatusId(ordersVo.getStatus(), session);
			System.out.println("tv is : " + ((lstOrdersVos == null) ? "null." : lstOrdersVos.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByCustomerId() {
		Session session = null;
		try {
			session = sf.openSession();
			List<OrdersVo> lstOrdersVos = ordersDao.getByCustomerId(ordersVo.getCustomerId(), session);
			System.err.println("lstOrdersVos is : " + ((lstOrdersVos == null) ? "null." : " not null."));
			for (OrdersVo typesVo : lstOrdersVos) {
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
			session = sf.openSession();
			int rows = ordersDao.delete(ordersVo.getId(), session);
			System.out.println("Rows deleted: " + rows + " for ID " + ordersVo.getId());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}