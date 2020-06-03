package com.javaduckspizza.ftesters;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.TypesDao;
import com.javaduckspizza.vo.TypesVo;

public class TypesDaoFt extends BasicDaoFt {
	private SessionFactory sf;
//	private final Logger logger = new ;
	private TypesVo typesVo;

	public static void main(String[] args) {
		TypesDaoFt typesDaoFt = new TypesDaoFt();
		typesDaoFt.runTests();
	}

	@Override
	protected void runTests() {
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
			sf = md.getSessionFactoryBuilder().build();

			System.out.println("sf is " + ((sf == null) ? "null" : "not null"));

			typesVo = generateTypesVo();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetBySequenceCodeId();
			testGetByCategory();
			testDelete();
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			if(sf != null) {
				sf.close();
			} else {
				System.err.println("Failed to build SessionFactory.");
			}
		}
	}

	protected TypesVo generateTypesVo() {
		TypesVo typesVo = new TypesVo();
		typesVo.setActive(true);
		typesVo.setCategory("TEST");
		typesVo.setDescription("FT TEST DESCRIPTON");
		typesVo.setSequenceCode("TEST_0001");
		return typesVo;
	}

	@Override
	protected void testInsert() {
		Session session = null;
		try {
			session = sf.openSession();
			TypesDao typesDao = new TypesDao();
			long id = typesDao.insert(typesVo, session);
			System.out.println("New TypesVo record has ID: " + id);
			typesVo.setId(id);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testUpdate() {
		System.out.println("Calling update");
		Session session = null;
		try {
			session = sf.openSession();
			TypesDao typesDao = new TypesDao();
			TypesVo typesVoClone = typesVo.clone();
			typesVoClone.setActive(false);
			int updatedRows = typesDao.update(typesVoClone, session);

			TypesVo typesVoAfterUpdate = typesDao.get(typesVo.getId(), session);
			System.out.println("typesVoClone: " + typesVoClone);
			System.out.println("Was record updated? " + (typesVoClone.equals(typesVoAfterUpdate)));
			System.out.println("After update: " + typesVoAfterUpdate.toString());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testGetByPrimaryKey() {
		System.out.println("\nIn getByPrimaryKey()...");
		Session session = null;
		try {
			TypesDao td = new TypesDao();
			session = sf.openSession();
			TypesVo tv = td.get(typesVo.getId(), session);
			System.out.println("For id " + typesVo.getId() + ", tv is: " + ((tv == null) ? "null." : tv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetBySequenceCodeId() {
		Session session = null;
		try {
			TypesDao td = new TypesDao();
			session = sf.openSession();
			TypesVo tv = td.getBySequenceCode(typesVo.getSequenceCode(), session);
			System.out.println("tv is : " + ((tv == null) ? "null." : tv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByCategory() {
		Session session = null;
		try {
			TypesDao td = new TypesDao();
			session = sf.openSession();
			List<TypesVo> lstTypesVos = td.getByCategory(typesVo.getCategory(), session);
			System.err.println("lstTypesVos is : " + ((lstTypesVos == null) ? "null." : " not null."));
			for (TypesVo typesVo : lstTypesVos) {
				System.out.println(typesVo.toString());
			}
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testDelete() {
		Session session = null;
		try {
			session = sf.openSession();
			TypesDao typesDao = new TypesDao();
			int rows = typesDao.delete(typesVo.getId(), session);
			System.out.println("Rows deleted: " + rows + " for ID " + typesVo.getId());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}
