package com.javaduckspizza.dao.interfaces;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.TypesVo;

public interface ITypesDao {
	public long insert(TypesVo tv, Session session);
	public int update(TypesVo tv, Session session);
	public TypesVo get(long id, Session session);
	public TypesVo getBySequenceCode(String sequenceCode, Session session);
	public List<TypesVo> getByCategory(String category, Session session);
	public List<TypesVo> getByCategoryStatus(String category, boolean isActive, Session session);
	public int delete(long id, Session session);
}
