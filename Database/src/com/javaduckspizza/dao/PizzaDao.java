package com.javaduckspizza.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.IPizzaDao;
import com.javaduckspizza.vo.PizzaVo;

/*
 * I chose to implement this class using Criteria instead of HQL just to try it out.
 * While not having to debug * HQL is nice (especially not having to list all of the
 * fields for an update), I do find the syntax a bit verbose. It goes without saying
 * that mixing the two styles in enterprise-level code would be bad practice. 
 */
public class PizzaDao implements IPizzaDao {
	public PizzaDao() {
	}

	@Override
	public long insert(PizzaVo pizzaVo, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (Long) session.save(pizzaVo);
		txn.commit();

		return id;
	}

	@Override
	public PizzaVo get(long id, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<PizzaVo> criteriaQuery = criteriaBuilder.createQuery(PizzaVo.class);
		Root<PizzaVo> root = criteriaQuery.from(PizzaVo.class);
		Query<PizzaVo> query = session.createQuery(criteriaQuery);

		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
		List<PizzaVo> lstPizzas = query.list();
		txn.commit();

		return (((lstPizzas == null) || lstPizzas.isEmpty()) ? null : lstPizzas.get(0));
	}

	@Override
	public List<PizzaVo> getByOrderId(long orderId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<PizzaVo> criteriaQuery = criteriaBuilder.createQuery(PizzaVo.class);

		Root<PizzaVo> root = criteriaQuery.from(PizzaVo.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("orderId"), orderId));
		Query<PizzaVo> query = session.createQuery(criteriaQuery);

		List<PizzaVo> lstPizzas = query.list();
		txn.commit();

		return lstPizzas;
	}

	@Override
	public List<PizzaVo> getByOrderIdAndStatus(long orderId, long statusId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<PizzaVo> criteriaQuery = criteriaBuilder.createQuery(PizzaVo.class);
		Root<PizzaVo> root = criteriaQuery.from(PizzaVo.class);
		Query<PizzaVo> query = session.createQuery(criteriaQuery);

		criteriaQuery.where(criteriaBuilder.equal(root.get("orderId"), orderId));
		criteriaQuery.where(criteriaBuilder.equal(root.get("status"), statusId));
		List<PizzaVo> lstPizzas = query.list();
		txn.commit();

		return lstPizzas;
	}

	@Override
	public List<PizzaVo> getByStatus(long statusId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<PizzaVo> criteriaQuery = criteriaBuilder.createQuery(PizzaVo.class);
		Root<PizzaVo> root = criteriaQuery.from(PizzaVo.class);
		Query<PizzaVo> query = session.createQuery(criteriaQuery);

		criteriaQuery.where(criteriaBuilder.equal(root.get("status"), statusId));
		List<PizzaVo> lstPizzas = query.list();
		txn.commit();

		return lstPizzas;
	}

	@Override
	public int update(PizzaVo pizzaVo, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaUpdate<PizzaVo> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(PizzaVo.class);
		Root<PizzaVo> root = criteriaUpdate.from(PizzaVo.class);
		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), pizzaVo.getId()));

		criteriaUpdate.set("crust", pizzaVo.getCrust());
		criteriaUpdate.set("sauce", pizzaVo.getSauce());
		criteriaUpdate.set("size", pizzaVo.getSize());
		criteriaUpdate.set("orderId", pizzaVo.getOrderId());
		criteriaUpdate.set("status", pizzaVo.getStatus());
		criteriaUpdate.set("price", pizzaVo.getPrice());
		criteriaUpdate.set("id", pizzaVo.getId());
		criteriaUpdate.set("cheese", pizzaVo.getCheese());

		Query<PizzaVo> query = session.createQuery(criteriaUpdate);
		int rows = query.executeUpdate();
		txn.commit();
		
		return rows;
	}

	@Override
	public int delete(long id, Session session) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<PizzaVo> criteriaDelete = criteriaBuilder.createCriteriaDelete(PizzaVo.class);
		Root<PizzaVo> root = criteriaDelete.from(PizzaVo.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

		Transaction txn = session.beginTransaction();
		Query<PizzaVo> query = session.createQuery(criteriaDelete);
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}

}