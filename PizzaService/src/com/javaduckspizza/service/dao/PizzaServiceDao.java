package com.javaduckspizza.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.dao.PizzaDao;
import com.javaduckspizza.vo.PizzaVo;

public class PizzaServiceDao {
	private PizzaDao pizzaDao = new PizzaDao();

	public PizzaVo getById(long id, Session session) {
		return pizzaDao.get(id, session);
	}

	public List<PizzaVo> getByOrderId(long id, Session session) {
		return pizzaDao.getByOrderId(id, session);
	}

	public List<PizzaVo> getByOrderIdAndStatus(long id, long status, Session session) {
		return pizzaDao.getByOrderIdAndStatus(id, status, session);
	}

	public List<PizzaVo> getByStatus(long id, Session session) {
		return pizzaDao.getByStatus(id, session);
	}
}
