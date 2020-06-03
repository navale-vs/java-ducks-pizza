package com.javaduckspizza.dao.interfaces;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.TypesModifiersAssociationVo;

public interface ITypesModifiersAssociationDao {
	public long insert(TypesModifiersAssociationVo ov, Session session);
	public TypesModifiersAssociationVo get(long id, Session session);
	public List<TypesModifiersAssociationVo> getByTypesId(long typeId, Session session);
	public List<TypesModifiersAssociationVo> getByModifiersId(long modifierId, Session session);
//	public int update(TypesModifiersAssociationVo tmav, Session session);
	public int delete(long id, Session session);
}
