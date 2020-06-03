package com.javaduckspizza.ftesters;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.TypesModifiersAssociationDao;
import com.javaduckspizza.vo.TypesModifiersAssociationVo;

public class TypesModifiersAssociationDaoFt extends BasicDaoFt {
	private SessionFactory sf;
//	private final Logger logger = new ;
	private TypesModifiersAssociationDao tmad;
	private TypesModifiersAssociationVo tmav;

	public TypesModifiersAssociationDaoFt() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		TypesModifiersAssociationDaoFt ft = new TypesModifiersAssociationDaoFt();
		ft.runTests();
	}

	@Override
	protected void runTests() {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
		Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
		sf = md.getSessionFactoryBuilder().build();
		tmad = new TypesModifiersAssociationDao();
		tmav = generateTypesModifiersAssociationVo();

		testInsert();
		testGetByPrimaryKey();
//		testUpdate();
		testGetByModifierId();
		testGetByTypeId();
		testDelete();
	}

	private TypesModifiersAssociationVo generateTypesModifiersAssociationVo() {
		TypesModifiersAssociationVo vo =
				new TypesModifiersAssociationVo();
		vo.setTypeId(7L);
		vo.setModifiersId(1L);
		return vo;
	}

	@Override
	protected void testInsert() {
		Session session = null;

		try {
			session = sf.openSession();
			long id = tmad.insert(tmav, session);
			tmav.setId(id);
			System.out.println("tmav: " + tmav);
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\ntestGetByPrimaryKey()...");
		Session session = null;

		try {
			session = sf.openSession();
			TypesModifiersAssociationVo tmavGet = tmad.get(tmav.getId(), session);
			System.out.println("tmavGet: " + tmavGet);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	protected void testGetByTypeId() {
		System.out.println("\ntestGetByTypeId()...");
		Session session = null;

		try {
			session = sf.openSession();
			List<TypesModifiersAssociationVo> lstTmavGet = tmad.getByTypesId(tmav.getTypeId(), session);

			for (TypesModifiersAssociationVo vo : lstTmavGet) {
				System.out.println("vo: " + vo);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	protected void testGetByModifierId() {
		System.out.println("\ntestGetByModifierId()...");
		Session session = null;

		try {
			session = sf.openSession();
			List<TypesModifiersAssociationVo> lstTmavGet = tmad.getByModifiersId(tmav.getModifiersId(), session);

			for (TypesModifiersAssociationVo vo : lstTmavGet) {
				System.out.println("vo: " + vo);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testUpdate() {
//		System.out.println("In testUpdate()...");
//		Session session = null;
//
//		try {
//			session = sf.openSession();
//			TypesModifiersAssociationVo tmavClone = tmav.clone();
//			tmav.
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
		//NOT ALLOWING UPDATE IN THIS TABLE
	}

	@Override
	protected void testDelete() {
		System.out.println("\ntestGetByModifierId()...");
		Session session = null;

		try {
			session = sf.openSession();
			int rows = tmad.delete(tmav.getId(), session);
			TypesModifiersAssociationVo tmavDeleted = tmad.get(tmav.getId(), session);
			System.out.println("tmavDeleted: " + tmavDeleted);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
