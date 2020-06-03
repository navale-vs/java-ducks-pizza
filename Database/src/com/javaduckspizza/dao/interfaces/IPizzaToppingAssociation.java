package com.javaduckspizza.dao.interfaces;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.PizzaToppingAssociationVo;

public interface IPizzaToppingAssociation {
	public long insert(PizzaToppingAssociationVo ptav, Session session);
	public PizzaToppingAssociationVo get(long id, Session session);
	public List<PizzaToppingAssociationVo> getByPizzaId(long pizzaId, Session session);
	public int update(PizzaToppingAssociationVo ptav, Session session);
	public int delete(long id, Session session);
}
