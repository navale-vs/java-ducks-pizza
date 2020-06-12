package com.javaduckspizza.service.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;

import com.javaduckspizza.dao.TypesDao;
import com.javaduckspizza.dao.interfaces.ITypesDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.TypesVo;

public class TypesServiceDao {
	private final ITypesDao typesDao = new TypesDao();

	public TypesVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		TypesVo typesVo = typesDao.get(id, session);
		session.close();
		return typesVo;
	}

	public TypesVo getBySequenceCode(String sequenceCode) {
		Session session = SessionUtil.getInstance().openSession();
		TypesVo typesVo = typesDao.getBySequenceCode(sequenceCode, session);
		session.close();
		return typesVo;
	}

	public List<TypesVo> getByCategory(String category) throws PersistenceException {
		Session session = SessionUtil.getInstance().openSession();
		List<TypesVo> typesVo = typesDao.getByCategory(category, session);
		session.close();

		return typesVo;
	}
}
