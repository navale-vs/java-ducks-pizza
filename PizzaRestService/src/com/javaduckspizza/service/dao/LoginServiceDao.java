package com.javaduckspizza.service.dao;

import org.hibernate.Session;

import com.javaduckspizza.dao.LoginDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.LoginVo;

public class LoginServiceDao {
	private LoginDao loginDao;

	public long addLogin(LoginVo loginVo) {
		Session session = SessionUtil.getInstance().openSession();
		long id = loginDao.insert(loginVo, session);
		session.close();

		return id;
	}

	public LoginVo getLogin(LoginVo loginVo) {
		Session session = SessionUtil.getInstance().openSession();
		LoginVo loginVoToReturn = loginDao.getByCustomerAndPassword(loginVo, session);
		session.close();

		return loginVoToReturn;
	}
}
