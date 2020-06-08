package com.javaduckspizza.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.IOrderDao;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.TypesVo;

public class OrdersDao implements IOrderDao {
	private static final String HQL_UPDATE = "UPDATE OrdersVo " +
			"SET CUSTOMER_ID = :customerId, METHOD = :method, TOTAL = :total, STATUS = :status, DATE_TIME_PLACED = :dateTimePlaced, " +
			"DATE_TIME_DUE = :dateTimeDue, DATE_TIME_READY = :dateTimeReady, DATE_TIME_COMPLETED = :dateTimeCompleted " +
			"WHERE ID = :id";
	private static final String HQL_GET_BY_ID = "FROM OrdersVo WHERE ID = :id";
	private static final String HQL_GET_BY_CUSTOMER_ID = "FROM OrdersVo WHERE CUSTOMER_ID = :customerId";
	private static final String HQL_GET_BY_STATUS = "FROM OrdersVo WHERE STATUS = :statusId";
	private static final String HQL_DELETE = "DELETE FROM OrdersVo WHERE ID = :id";

	@Override
	public long insert(OrdersVo ov, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (Long) session.save(ov);
		txn.commit();
		return id;
	}

	@Override
	public OrdersVo get(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query query = session.createQuery(HQL_GET_BY_ID);
		query.setParameter("id", id);
		List<OrdersVo> lstOv = query.list();
		txn.commit();
		return (((lstOv == null) || lstOv.isEmpty()) ? null : lstOv.get(0));
	}

	@Override
	public List<OrdersVo> getByCustomerId(long customerId, Session session) {
		Transaction txn = session.beginTransaction();
		Query query = session.createQuery(HQL_GET_BY_CUSTOMER_ID);
		query.setParameter("customerId", customerId);
		List<OrdersVo> lstOv = query.list();
		txn.commit();
		return lstOv;
	}

	@Override
	public List<OrdersVo> getByStatusId(long statusId, Session session) {
		Transaction txn = session.beginTransaction();
		Query query = session.createQuery(HQL_GET_BY_STATUS);
		query.setParameter("statusId", statusId);
		List<OrdersVo> lstOv = query.list();
		txn.commit();
		return lstOv;
	}

	@Override
	public int update(OrdersVo ov, Session session) {
		System.out.println("In TypesDao update");
		Transaction txn = session.beginTransaction();
		Query query = session.createQuery(HQL_UPDATE);
		query.setParameter("customerId", ov.getCustomerId());
		query.setParameter("method", ov.getMethod());
		query.setParameter("total", ov.getTotal());
		query.setParameter("status", ov.getStatus());
		query.setParameter("dateTimePlaced", ov.getDateTimePlaced());
		query.setTimestamp("dateTimeDue", ov.getDateTimeDue());
		query.setParameter("dateTimeReady", ov.getDateTimeReady());
		query.setTimestamp("dateTimeCompleted", ov.getDateTimeCompleted());
		query.setParameter("id", ov.getId());
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}

	@Override
	public int delete(long id, Session session) {
		Query<TypesVo> query = session.createQuery(HQL_DELETE);
		Transaction txn = session.beginTransaction();
		query.setParameter("id", id);
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}
}