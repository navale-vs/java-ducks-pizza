package com.javaduckspizza.dao.interfaces;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.PizzaVo;

public interface IPizzaDao {
	public long insert(PizzaVo pv, Session session);
	public int update(PizzaVo pv, Session session);
	public int delete(long id, Session session);
	public PizzaVo get(long id, Session session);
	public List<PizzaVo> getByOrderId(long orderId, Session session);
	public List<PizzaVo> getByOrderIdAndStatus(long orderId, long statusId, Session session);
	public List<PizzaVo> getByStatus(long statusId, Session session);
}
