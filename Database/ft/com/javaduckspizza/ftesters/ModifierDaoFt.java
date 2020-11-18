package com.javaduckspizza.ftesters;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.dao.ModifierDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.ModifierVo;

public class ModifierDaoFt extends BasicDaoFt {
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
			modifierVo = generateModifiersVo();
			testInsert();
			testGetByPrimaryKey();
			testUpdate();
			testGetByTypeId();
			testGetByDateExpired();
			testGetCurrentByType();
			testDelete();
		} catch(Throwable t) {
			t.printStackTrace();
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
			session = SessionUtil.getInstance().openSession();
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
			session = SessionUtil.getInstance().openSession();
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
			session = SessionUtil.getInstance().openSession();
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
			session = SessionUtil.getInstance().openSession();
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
			session = SessionUtil.getInstance().openSession();
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
			session = SessionUtil.getInstance().openSession();
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

	void testGetCurrentByType() {
		System.out.println("testGetCurrentByType()...");
		Session session = null;
		try {
			session = SessionUtil.getInstance().openSession();
			ModifierVo mv = modifierDao.getCurrentByType(modifierVo.getTypeId(), session);
//			List<ModifierVo> lstModifiersVos = modifierDao.getCurrentByType(modifierVo.getTypeId(), session);
			System.err.println("lstModifiersVos is : " + ((mv == null) ? "null." : mv.toString()));
//			for (ModifierVo typesVo : lstModifiersVos) {
//				System.out.println(typesVo.toString());
//			}
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

//	private void testGetByDateExpired_NULL() {
//		Session session = null;
//		try {
//			session = SessionUtil.getInstance().openSession();
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