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
import com.javaduckspizza.vo.TypesModifiersAssociationVo;

public class TypesModifiersAssociationDao implements ITypesModifiersAssociationDao {
	@Override
	public long insert(TypesModifiersAssociationVo vo, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (long) session.save(vo);
		txn.commit();

		return id;
	}

	@Override
	public TypesModifiersAssociationVo get(long id, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TypesModifiersAssociationVo> cq = cb.createQuery(TypesModifiersAssociationVo.class);
		Root<TypesModifiersAssociationVo> root = cq.from(TypesModifiersAssociationVo.class);
		Query<TypesModifiersAssociationVo> query = session.createQuery(cq);

		cq.where(cb.equal(root.get("id"), id));
		List<TypesModifiersAssociationVo> lst = query.getResultList();
		txn.commit();

		return (((lst == null) || lst.isEmpty()) ? null : lst.get(0));
	}

	@Override
	public List<TypesModifiersAssociationVo> getByTypesId(long typeId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TypesModifiersAssociationVo> cq = cb.createQuery(TypesModifiersAssociationVo.class);
		Root<TypesModifiersAssociationVo> root = cq.from(TypesModifiersAssociationVo.class);
		Query<TypesModifiersAssociationVo> query = session.createQuery(cq);

		cq.where(cb.equal(root.get("typesId"), typeId));
		List<TypesModifiersAssociationVo> lst = query.getResultList();
		txn.commit();

		return lst;
	}

	@Override
	public List<TypesModifiersAssociationVo> getByModifiersId(long modifierId, Session session) {
		Transaction txn = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TypesModifiersAssociationVo> cq = cb.createQuery(TypesModifiersAssociationVo.class);
		Root<TypesModifiersAssociationVo> root = cq.from(TypesModifiersAssociationVo.class);
		Query<TypesModifiersAssociationVo> query = session.createQuery(cq);

		cq.where(cb.equal(root.get("modifiersId"), modifierId));
		List<TypesModifiersAssociationVo> lst = query.getResultList();
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
		CriteriaDelete<TypesModifiersAssociationVo> criteriaDelete =
				criteriaBuilder.createCriteriaDelete(TypesModifiersAssociationVo.class);
		Root<TypesModifiersAssociationVo> root = criteriaDelete.from(TypesModifiersAssociationVo.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

		Query<TypesModifiersAssociationVo> query = session.createQuery(criteriaDelete);
		int rows = query.executeUpdate();
		txn.commit();

		return rows;
	}
}