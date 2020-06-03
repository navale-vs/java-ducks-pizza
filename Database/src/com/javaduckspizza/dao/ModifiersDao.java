package com.javaduckspizza.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.IModifiersDao;
import com.javaduckspizza.vo.ModifiersVo;
import com.javaduckspizza.vo.TypesVo;

public class ModifiersDao implements IModifiersDao {
	private static final String HQL_UPDATE = "UPDATE ModifiersVo SET DATE_EXPIRED = :dateExpired WHERE ID = :id";
	private static final String HQL_GET_BY_ID = "FROM ModifiersVo WHERE ID = :id";
	private static final String HQL_GET_BY_TYPE_ID = "FROM ModifiersVo WHERE TYPE_ID = :typeId";
	private static final String HQL_GET_BY_DATE_ACTIVE = "FROM ModifiersVo WHERE DATE_ACTIVE = :dateActive";
	private static final String HQL_GET_BY_DATE_EXPIRED = "FROM ModifiersVo WHERE DATE_EXPIRED = :dateExpired";
//	private static final String HQL_GET_WHERE_NOT_EXPIRED = "FROM ModifiersVo WHERE DATE_EXPIRED > :date OR DATE_EXPIRED IS NULL";
	private static final String HQL_DELETE = "DELETE FROM ModifiersVo WHERE ID = :id";

	public ModifiersDao() {}

	@Override
	public long insert(ModifiersVo mv, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (Long) session.save(mv);
		return id;
	}

	@Override
	public int delete(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifiersVo> query = session.createQuery(HQL_DELETE);
		query.setParameter("id", id);
		int rows = query.executeUpdate();
		txn.commit();
		return rows;
	}

	@Override
	public int update(ModifiersVo mv, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifiersVo> query = session.createQuery(HQL_UPDATE);
		query.setParameter("dateExpired", mv.getDateExpired());
		query.setParameter("id", mv.getId());
		int rows = query.executeUpdate();
		txn.commit();
		return 0;
	}

	@Override
	public ModifiersVo get(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifiersVo> query = session.createQuery(HQL_GET_BY_ID);
		query.setParameter("id", id);
		List<ModifiersVo> lstModifiers = query.list();
		txn.commit();
		
		return (((lstModifiers == null) || lstModifiers.isEmpty()) ? null : lstModifiers.get(0));
	}

	@Override
	public List<ModifiersVo> getByTypeId(long typeId, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifiersVo> query = session.createQuery(HQL_GET_BY_TYPE_ID);
		query.setParameter("typeId", typeId);
		List<ModifiersVo> lstModifiers = query.list();
		txn.commit();
		return lstModifiers;
	}

	@Override
	public List<ModifiersVo> getByDateActive(Date expiration, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifiersVo> query = session.createQuery(HQL_GET_BY_DATE_ACTIVE);
		query.setParameter("dateActive", expiration);
		List<ModifiersVo> lstModifiers = query.list();
		txn.commit();
		return lstModifiers;
	}

	@Override
	public List<ModifiersVo> getByDateExpired(Date expiration, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifiersVo> query = session.createQuery(HQL_GET_BY_DATE_EXPIRED);
		query.setParameter("dateExpired", expiration);
		List<ModifiersVo> lstModifiers = query.list();
		txn.commit();
		return lstModifiers;
	}
}
