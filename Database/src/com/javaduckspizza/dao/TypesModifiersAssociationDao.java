package com.javaduckspizza.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javaduckspizza.dao.interfaces.ITypesModifiersAssociationDao;
import com.javaduckspizza.vo.TypesModifierAssociationVo;

public class TypesModifiersAssociationDao implements ITypesModifiersAssociationDao {
	@Override
	public long insert(TypesModifierAssociationVo vo, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (long) session.save(vo);
		txn.commit();

		return id;
	}

	@Override
	public TypesModifierAssociationVo get(long id, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TypesModifierAssociationVo> cq = cb.createQuery(TypesModifierAssociationVo.class);
		Root<TypesModifierAssociationVo> root = cq.from(TypesModifierAssociationVo.class);
		Query<TypesModifierAssociationVo> query = session.createQuery(cq);

		cq.where(cb.equal(root.get("id"), id));
		List<TypesModifierAssociationVo> lst = query.getResultList();
		txn.commit();

		return (((lst == null) || lst.isEmpty()) ? null : lst.get(0));
	}

	@Override
	public List<TypesModifierAssociationVo> getByTypesId(long typeId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TypesModifierAssociationVo> cq = cb.createQuery(TypesModifierAssociationVo.class);
		Root<TypesModifierAssociationVo> root = cq.from(TypesModifierAssociationVo.class);
		Query<TypesModifierAssociationVo> query = session.createQuery(cq);

		cq.where(cb.equal(root.get("typesId"), typeId));
		List<TypesModifierAssociationVo> lst = query.getResultList();
		txn.commit();

		return lst;
	}

	@Override
	public List<TypesModifierAssociationVo> getByModifiersId(long modifierId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TypesModifierAssociationVo> cq = cb.createQuery(TypesModifierAssociationVo.class);
		Root<TypesModifierAssociationVo> root = cq.from(TypesModifierAssociationVo.class);
		Query<TypesModifierAssociationVo> query = session.createQuery(cq);

		cq.where(cb.equal(root.get("modifiersId"), modifierId));
		List<TypesModifierAssociationVo> lst = query.getResultList();
		txn.commit();

		return lst;
	}

//	@Override
//	public int update(TypesModifiersAssociationVo tmav, Session session) {
//		Transaction txn = session.beginTransaction();
//		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//		CriteriaUpdate<TypesModifiersAssociationVo> criteriaUpdate =
//				criteriaBuilder.createCriteriaUpdate(TypesModifiersAssociationVo.class);
//		Root<TypesModifiersAssociationVo> root = criteriaUpdate.from(TypesModifiersAssociationVo.class);
//		criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), tmav.getId()));
//
//		criteriaUpdate.set("typesId", tmav.getBaseTypeId());
//		criteriaUpdate.set("modifiersId", tmav.getModifierTypeId());
//
//		Query<TypesModifiersAssociationVo> query = session.createQuery(criteriaUpdate);
//		int rows = query.executeUpdate();
//		txn.commit();
//		
//		return rows;
//	}

	@Override
	public int delete(long id, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<TypesModifierAssociationVo> criteriaDelete =
				criteriaBuilder.createCriteriaDelete(TypesModifierAssociationVo.class);
		Root<TypesModifierAssociationVo> root = criteriaDelete.from(TypesModifierAssociationVo.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

		Query<TypesModifierAssociationVo> query = session.createQuery(criteriaDelete);
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}
}