package com.javaduckspizza.dao.interfaces;

import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.TypesModifierAssociationVo;

public interface ITypesModifiersAssociationDao {
	public long insert(TypesModifierAssociationVo ov, Session session);
	public TypesModifierAssociationVo get(long id, Session session);
	public List<TypesModifierAssociationVo> getByTypesId(long typeId, Session session);
	public List<TypesModifierAssociationVo> getByModifiersId(long modifierId, Session session);
//	public int update(TypesModifiersAssociationVo tmav, Session session);
	public int delete(long id, Session session);
}
