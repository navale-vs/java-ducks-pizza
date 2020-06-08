package com.javaduckspizza.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.ITypeDao;
import com.javaduckspizza.vo.TypesVo;

public class TypesDao implements ITypeDao {
	private static final String HQL_UPDATE = "UPDATE TypesVo " +
			"SET DESCRIPTION = :description, IS_ACTIVE = :isActive " +
			"WHERE ID = :id";
	private static final String HQL_GET_BY_ID = "FROM TypesVo WHERE ID = :id";
	private static final String HQL_GET_BY_SEQUENCE_CODE = "FROM TypesVo WHERE SEQUENCE_CODE = :sequenceCode";
	private static final String HQL_GET_BY_CATEGORY = "FROM TypesVo WHERE CATEGORY = :category";
	private static final String HQL_DELETE = "DELETE FROM TypesVo WHERE ID = :id";

	public TypesDao() {
	}

	@Override
	public long insert(TypesVo tv, Session session) {
		System.out.println("In TypesDao insert");
		Transaction txn = session.beginTransaction();
		long id = (long) session.save(tv);
		txn.commit();
		return id;
	}

	@Override
	public int update(TypesVo tv, Session session) {
		System.out.println("In TypesDao update");
		Transaction txn = session.beginTransaction();
		Query query = session.createQuery(HQL_UPDATE);
		query.setParameter("description", tv.getDescription());
		query.setParameter("isActive", tv.isActive());
		query.setParameter("id", tv.getId());
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}

	@Override
	public TypesVo get(long id, Session session) {
		System.out.println("In getBySequenceCode(), using " + id);
		Query<TypesVo> query = session.createQuery(HQL_GET_BY_ID);
		query.setParameter("id", id);
		List<TypesVo> lstTypes = query.list();

		return (((lstTypes == null) || lstTypes.isEmpty()) ? null : lstTypes.get(0));
	}

	@Override
	public TypesVo getBySequenceCode(String sequenceCode, Session session) {
		System.out.println("In getBySequenceCode(), using " + sequenceCode);
		Query<TypesVo> query = session.createQuery(HQL_GET_BY_SEQUENCE_CODE);
		query.setParameter("sequenceCode", sequenceCode);
		List<TypesVo> lstTypes = query.list();
		
		return (((lstTypes == null) || lstTypes.isEmpty()) ? null : lstTypes.get(0));
	}

	@Override
	public List<TypesVo> getByCategory(String category, Session session) {
		List<TypesVo> lstTypes = null;
		Query<TypesVo> query = session.createQuery(HQL_GET_BY_CATEGORY);
		query.setParameter("category", category);
		lstTypes = query.list();

		return lstTypes;
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