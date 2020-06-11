package com.javaduckspizza.service.dao;

import org.hibernate.Session;

import com.javaduckspizza.dao.ModifierDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.ModifierVo;

public class ModifierServiceDao {
	private ModifierDao modifierDao = new ModifierDao();

	public ModifierVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		ModifierVo mv = modifierDao.get(id, session);
		session.close();
		return mv;
	}

	public ModifierVo getCurrentByType(long type) {
		Session session = SessionUtil.getInstance().openSession();
		ModifierVo mv = modifierDao.get(type, session);
		session.close();
		return mv;
	}
}
