package com.javaduckspizza.service.dao;

import org.hibernate.Session;

import com.javaduckspizza.dao.ModifiersDao;
import com.javaduckspizza.dao.interfaces.IModifiersDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.ModifierVo;

public class ModifierServiceDao {
	private IModifiersDao iModifierDao = new ModifiersDao();

	public ModifierVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		ModifierVo mv = iModifierDao.get(id, session);
		session.close();
		return mv;
	}

	public ModifierVo getCurrentByType(long type) {
		Session session = SessionUtil.getInstance().openSession();
		ModifierVo mv = iModifierDao.getCurrentByType(type, session);
		session.close();
		return mv;
	}
}
