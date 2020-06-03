package com.javaduckspizza.dao.interfaces;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.OrdersVo;

public interface IOrderDao {
	public long insert(OrdersVo ov, Session session);
	public OrdersVo get(long id, Session session);
	public List<OrdersVo> getByCustomerId(long customerId, Session session);
	public List<OrdersVo> getByStatusId(long statusId, Session session);
	public int update(OrdersVo ov, Session session);
	public int delete(long id, Session session);
}
