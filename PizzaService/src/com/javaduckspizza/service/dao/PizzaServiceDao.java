package com.javaduckspizza.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.dao.PizzaDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.PizzaVo;

public class PizzaServiceDao {
	private PizzaDao pizzaDao = new PizzaDao();

	public PizzaVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		PizzaVo pizzaVo = pizzaDao.get(id, session);
		session.close();

		return pizzaVo;
	}
	
	public List<PizzaVo> getByOrderId(long orderId) {
		Session session = SessionUtil.getInstance().openSession();
		List<PizzaVo> lst = pizzaDao.getByOrderId(orderId, session);
		session.close();

		return lst;
	}

//o

	public List<PizzaVo> getByOrderIdAndStatus(long id, long status) {
		Session session = SessionUtil.getInstance().openSession();
		List<PizzaVo> lstPizzaVos = pizzaDao.getByOrderIdAndStatus(id, status, session);
		session.close();
		
		return lstPizzaVos;
	}

	public List<PizzaVo> getByStatus(long id) {
		Session session = SessionUtil.getInstance().openSession();
		List<PizzaVo> lstPizzaVos = pizzaDao.getByStatus(id, session);
		session.close();

		return lstPizzaVos;
	}

	public long addPizza(PizzaVo pizzaVo) {
		Session session = SessionUtil.getInstance().openSession();
		long pizzaId = pizzaDao.insert(pizzaVo, session);
		session.close();

		return pizzaId;
	}
}
