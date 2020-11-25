package com.javaduckspizza.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.IPizzaToppingAssociation;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;

public class PizzaToppingAssociationDao implements IPizzaToppingAssociation {
	@Override
	public long insert(PizzaToppingAssociationVo ptav, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (long) session.save(ptav);
		txn.commit();
		return id;
	}

	@Override
	public PizzaToppingAssociationVo get(long id, Session session) {
//		Transaction txn = session.beginTransaction();
//		CriteriaBuilder cb = session.getCriteriaBuilder();
//		CriteriaQuery<PizzaToppingAssociationVo> cq = cb.createQuery(PizzaToppingAssociationVo.class);
//		Root<PizzaToppingAssociationVo> root = cq.from(PizzaToppingAssociationVo.class);
//		Query<PizzaToppingAssociationVo> query = session.createQuery(cq);
//		cq.where(cb.equal(root.get("id"), id));
//		List<PizzaToppingAssociationVo> lstPtavs = query.list();
//		txn.commit();
//
//		return (((lstPtavs == null) || lstPtavs.isEmpty()) ? null : lstPtavs.get(0));
		System.out.println("id: " + id);
		Transaction txn = session.beginTransaction();
		Query<PizzaToppingAssociationVo> query = session.createQuery("FROM PizzaToppingAssociationVo WHERE ID = :id");
		query.setParameter("id", id);
		List<PizzaToppingAssociationVo> lstPtav = query.list();
		txn.commit();

		return (((lstPtav == null) || lstPtav.isEmpty()) ? null : lstPtav.get(0));
	}

	@Override
	public List<PizzaToppingAssociationVo> getByPizzaId(long pizzaId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<PizzaToppingAssociationVo> cq = cb.createQuery(PizzaToppingAssociationVo.class);
		Root<PizzaToppingAssociationVo> root = cq.from(PizzaToppingAssociationVo.class);
		cq.where(cb.equal(root.get("pizzaId"), pizzaId));
		Query<PizzaToppingAssociationVo> query = session.createQuery(cq);

		List<PizzaToppingAssociationVo> lstPtavs = query.list(); //new ArrayList<PizzaToppingAssociationVo>();
		//query.list();
		txn.commit();
		
		return lstPtavs;
	}

	@Override
	public int update(PizzaToppingAssociationVo ptav, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaUpdate<PizzaToppingAssociationVo> criteriaUpdate =
				criteriaBuilder.createCriteriaUpdate(PizzaToppingAssociationVo.class);
		Root<PizzaToppingAssociationVo> root = criteriaUpdate.from(PizzaToppingAssociationVo.class);
		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), ptav.getId()));

		criteriaUpdate.set("left", ptav.getLeft());
		criteriaUpdate.set("pizzaId", ptav.getPizzaId());
		criteriaUpdate.set("right", ptav.getRight());
		criteriaUpdate.set("toppingsId", ptav.getToppingsId());

		Query<PizzaToppingAssociationVo> query = session.createQuery(criteriaUpdate);
		int rows = query.executeUpdate();
		txn.commit();
		
		return rows;
	}

	@Override
	public int delete(long id, Session session) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<PizzaToppingAssociationVo> criteriaDelete =
				criteriaBuilder.createCriteriaDelete(PizzaToppingAssociationVo.class);
		Root<PizzaToppingAssociationVo> root = criteriaDelete.from(PizzaToppingAssociationVo.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

		Transaction txn = session.beginTransaction();
		Query<PizzaToppingAssociationVo> query = session.createQuery(criteriaDelete);
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}
}