package com.javaduckspizza.service.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;

import com.javaduckspizza.dao.TypesDao;
import com.javaduckspizza.vo.TypesVo;

public class TypesServiceDao {
	private final TypesDao typesDao = new TypesDao();

	public TypesVo getById(long id, Session session) {
		System.out.println("In get with id: " + id);
		return typesDao.get(id, session);
	}

	public TypesVo getBySequenceCode(String sequenceCode, Session session) {
		return typesDao.getBySequenceCode(sequenceCode, session);
	}

	public List<TypesVo> getByCategory(String category, Session session) throws PersistenceException {
		return typesDao.getByCategory(category, session);
	}
}
