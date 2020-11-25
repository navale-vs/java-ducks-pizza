package com.javaduckspizza.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.IModifiersDao;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.TypesVo;

public class ModifiersDao implements IModifiersDao {
	private static final String HQL_UPDATE = "UPDATE ModifierVo SET DATE_EXPIRED = :dateExpired WHERE ID = :id";
	private static final String HQL_GET_BY_ID = "FROM ModifierVo WHERE ID = :id";
	private static final String HQL_GET_BY_TYPE_ID = "FROM ModifierVo WHERE TYPE_ID = :typeId";
	private static final String HQL_GET_BY_DATE_ACTIVE = "FROM ModifierVo WHERE DATE_ACTIVE = :dateActive";
	private static final String HQL_GET_BY_DATE_EXPIRED = "FROM ModifierVo WHERE DATE_EXPIRED = :dateExpired";
	private static final String HQL_GET_WHERE_NOT_EXPIRED = "FROM ModifierVo WHERE DATE_EXPIRED IS NULL OR"
			+ " (DATE_ACTIVE <= :dateActive ";
	private static final String HQL_GET_BY_TYPE_WHERE_NOT_EXPIRED = "FROM ModifierVo WHERE TYPE_ID = :typeId AND"
			+ " DATE_ACTIVE <= CURRENT_DATE AND "
			+ " (DATE_EXPIRED IS NULL OR DATE_EXPIRED > CURRENT_DATE)";
	private static final String HQL_DELETE = "DELETE FROM ModifierVo WHERE ID = :id";

	public ModifiersDao() {}

	@Override
	public long insert(ModifierVo mv, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (Long) session.save(mv);
		return id;
	}

	@Override
	public int delete(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_DELETE);
		query.setParameter("id", id);
		int rows = query.executeUpdate();
		txn.commit();
		return rows;
	}

	@Override
	public int update(ModifierVo mv, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_UPDATE);
		query.setParameter("dateExpired", mv.getDateExpired());
		query.setParameter("id", mv.getId());
		int rows = query.executeUpdate();
		txn.commit();
		return 0;
	}

	@Override
	public ModifierVo get(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_GET_BY_ID);
		query.setParameter("id", id);
		List<ModifierVo> lstModifiers = query.list();
		txn.commit();
		
		return (((lstModifiers == null) || lstModifiers.isEmpty()) ? null : lstModifiers.get(0));
	}

	@Override
	public List<ModifierVo> getByTypeId(long typeId, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_GET_BY_TYPE_ID);
		query.setParameter("typeId", typeId);
		List<ModifierVo> lstModifiers = query.list();
		txn.commit();
		return lstModifiers;
	}

	@Override
	public List<ModifierVo> getByDateActive(Date expiration, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_GET_BY_DATE_ACTIVE);
		query.setParameter("dateActive", expiration);
		List<ModifierVo> lstModifiers = query.list();
		txn.commit();
		return lstModifiers;
	}

	@Override
	public List<ModifierVo> getByDateExpired(Date expiration, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_GET_BY_DATE_EXPIRED);
		query.setParameter("dateExpired", expiration);
		List<ModifierVo> lstModifiers = query.list();
		txn.commit();
		return lstModifiers;
	}

//	@Override
	public ModifierVo getCurrentByType(long typeId, Session session) {
		Transaction txn = session.beginTransaction();
		Query<ModifierVo> query = session.createQuery(HQL_GET_BY_TYPE_WHERE_NOT_EXPIRED);
		query.setParameter("typeId", typeId);
		List<ModifierVo> lstModifiers = query.list();
		txn.commit();
		
		return (((lstModifiers == null) || lstModifiers.isEmpty()) ? null : lstModifiers.get(0));
	}
}