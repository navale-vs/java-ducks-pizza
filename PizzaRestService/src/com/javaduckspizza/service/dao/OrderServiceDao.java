package com.javaduckspizza.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.dao.OrdersDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.OrdersVo;

public class OrderServiceDao {
	private OrdersDao orderDao = new OrdersDao();

	public long addOrder(OrdersVo orderVo) {
		Session session = SessionUtil.getInstance().openSession();
		long id = orderDao.insert(orderVo, session);
		session.close();
		return id;
	}

	public OrdersVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		OrdersVo ordersVo = orderDao.get(id, session);
		session.close();

		return ordersVo;
	}

	public List<OrdersVo> getByCustomerId(long id) {
		Session session = SessionUtil.getInstance().openSession();
		List<OrdersVo> lstOrdersVo = orderDao.getByCustomerId(id, session);
		session.close();

		return lstOrdersVo;
	}

	public List<OrdersVo> getByStatusId(long id) {
		Session session = SessionUtil.getInstance().openSession();
		List<OrdersVo> lstOrdersVo = orderDao.getByStatusId(id, session);
		session.close();

		return lstOrdersVo;
	}
}
