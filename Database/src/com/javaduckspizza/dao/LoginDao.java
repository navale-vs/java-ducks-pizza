package com.javaduckspizza.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.ILoginDao;
import com.javaduckspizza.vo.LoginVo;

/**
 * Implemented using Criteria for practice.
 * @author navale-vs
 *
 */
public class LoginDao implements ILoginDao {
	@Override
	public Long insert(LoginVo loginVo, Session session) {
		Transaction txn = session.beginTransaction();
		Long customerId = (Long) session.save(loginVo);
		txn.commit();

		return customerId;
	}

	@Override
	public LoginVo getByCustomerAndPassword(LoginVo loginVo, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<LoginVo> criteriaQuery = criteriaBuilder.createQuery(LoginVo.class);
		Root<LoginVo> root = criteriaQuery.from(LoginVo.class);
		Query<LoginVo> query = session.createQuery(criteriaQuery);

		criteriaQuery.where(criteriaBuilder.equal(root.get("customerId"), loginVo.getCustomerId()));
		criteriaQuery.where(criteriaBuilder.equal(root.get("password"), loginVo.getPassword()));
		List<LoginVo> lstLoginVo = query.list();
		txn.commit();

		return (( (lstLoginVo != null) && !lstLoginVo.isEmpty() ) ? lstLoginVo.get(0) : null);
	}

	@Override
	public int delete(LoginVo loginVo, Session session) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<LoginVo> criteriaDelete = criteriaBuilder.createCriteriaDelete(LoginVo.class);
		Root<LoginVo> root = criteriaDelete.from(LoginVo.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("customerId"), loginVo.getCustomerId()));
		criteriaDelete.where(criteriaBuilder.equal(root.get("password"), loginVo.getPassword()));

		Transaction txn = session.beginTransaction();
		Query<LoginVo> query = session.createQuery(criteriaDelete);
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}
}