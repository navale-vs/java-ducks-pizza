package com.javaduckspizza.service.dao;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.dao.PizzaDao;
import com.javaduckspizza.dao.PizzaToppingAssociationDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.PizzaVo;

public class PizzaServiceDao {
	private PizzaDao pizzaDao = new PizzaDao();
	private PizzaToppingAssociationDao ptad = new PizzaToppingAssociationDao();

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

	public List<PizzaToppingAssociationVo> getByPizza(long id) {
		Session session = SessionUtil.getInstance().openSession();
		List<PizzaToppingAssociationVo> lstPtav = ptad.getByPizzaId(id, session);
		session.close();

		return lstPtav;
	}

	public long addPizza(PizzaVo pizzaVo) {
		Session session = SessionUtil.getInstance().openSession();
		long pizzaId = pizzaDao.insert(pizzaVo, session);
		session.close();

		return pizzaId;
	}

	public long addPizzaToppingAssociation(PizzaToppingAssociationVo ptav) {
		Session session = SessionUtil.getInstance().openSession();
		long id = ptad.insert(ptav, session);
		session.close();

		return id;
	}
}