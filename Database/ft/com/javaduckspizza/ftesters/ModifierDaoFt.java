package com.javaduckspizza.ftesters;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.javaduckspizza.dao.ModifierDao;
import com.javaduckspizza.vo.ModifierVo;

public class ModifierDaoFt extends BasicDaoFt {
	SessionFactory sf;
//	private final Logger logger = new ;
	ModifierVo modifierVo;
	ModifierDao modifierDao;

	public ModifierDaoFt() {
		modifierDao = new ModifierDao();
	}

	public static void main(String[] args) {
		ModifierDaoFt mdf = new ModifierDaoFt();
		mdf.runTests();
	}

	@Override
	protected void runTests() {
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(new File("./hibernate.cfg.xml")).build();
			Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
			sf = md.getSessionFactoryBuilder().build();

			System.out.println("sf is " + ((sf == null) ? "null" : "not null"));
			
			modifierVo = generateModifiersVo();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetByTypeId();
			testGetByDateExpired();
//			testGetByDateExpired_NULL();
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

	private ModifierVo generateModifiersVo() {
		ModifierVo mv = new ModifierVo();
		mv.setDateActive(new Date(Calendar.getInstance().getTimeInMillis()));
		mv.setDateExpired(null);
		mv.setTypeId(1L);
		mv.setValue(BigDecimal.valueOf(1.35));
		return mv;
	}

	@Override
	protected void testInsert() {
		Session session = null;
		try {
			session = sf.openSession();
			long id = modifierDao.insert(modifierVo, session);
			System.out.println("New ModifiersVo record has ID: " + id);
			modifierVo.setId(id);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testUpdate() {
		Session session = null;
		try {
			session = sf.openSession();
			ModifierVo mvClone = modifierVo.clone();
			mvClone.setDateExpired(new Date( Calendar.getInstance().getTimeInMillis() ));
			modifierDao.update(mvClone, session);

			ModifierVo mvAfterUpdate = modifierDao.get(modifierVo.getId(), session);
			System.out.println("modifiersVo" + modifierVo.toString());
			System.out.println("mvClone" + mvClone.toString());
			System.out.println("mvAfterUpdate" + mvAfterUpdate.toString());
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
			int rows = modifierDao.delete(modifierVo.getId(), session);
			System.out.println("Rows deleted: " + rows);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testGetByPrimaryKey() {
		Session session = null;
		try {
			session = sf.openSession();
			List<ModifierVo> tv = modifierDao.getByTypeId(1, session);
			System.out.println("tv is : " + ((tv == null) ? "null." : tv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	protected void testGetByTypeId() {
		Session session = null;
		try {
			session = sf.openSession();
			List<ModifierVo> tv = modifierDao.getByTypeId(1, session);
			System.out.println("tv is : " + ((tv == null) ? "null." : tv.toString()));
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	void testGetByDateExpired() {
		Session session = null;
		try {
			session = sf.openSession();
			List<ModifierVo> lstModifiersVos = modifierDao.getByDateExpired(new Date(Calendar.getInstance().getTimeInMillis()), session);
			System.err.println("lstModifiersVos is : " + ((lstModifiersVos == null) ? "null." : " not null."));
			for (ModifierVo typesVo : lstModifiersVos) {
				System.out.println(typesVo.toString());
			}
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

//	private void testGetByDateExpired_NULL() {
//		Session session = null;
//		try {
//			session = sf.openSession();
//			List<ModifiersVo> lstModifiersVos = modifiersDao.getByDateExpired(null, session);
//			System.err.println("lstModifiersVos is : " + ((lstModifiersVos == null) ? "null." : " not null."));
//			for (ModifiersVo typesVo : lstModifiersVos) {
//				System.out.println(typesVo.toString());
//			}
//		} finally {
//			if(session != null) {
//				session.close();
//			}
//		}
//	}
}