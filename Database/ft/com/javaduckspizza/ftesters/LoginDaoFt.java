package com.javaduckspizza.ftesters;

import org.hibernate.Session;

import com.javaduckspizza.dao.LoginDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.LoginVo;

public class LoginDaoFt extends BasicDaoFt {
	private LoginVo loginVo;

	public static void main(String[] args) {
		LoginDaoFt loginDaoFt = new LoginDaoFt();
		loginDaoFt.runTests();
	}

	@Override
	protected void runTests() {
		loginVo = generateVo();
		testInsert();
		testGetByPrimaryKey();
		testDelete();
	}

	@Override
	protected void testInsert() {
		Session session = null;

		try {
			LoginDao loginDao = new LoginDao();
			session = SessionUtil.getInstance().openSession();
			long id = loginDao.insert(loginVo, session);
			System.out.println("New LoginVo record for: " + loginVo);
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
			LoginDao loginDao = new LoginDao();
			loginDao.getByCustomerAndPassword(loginVo, session);
			System.out.println("Retrieved record: " + loginVo);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	protected void testUpdate() {
		//Not currently implemented in LoginDao
	}

	@Override
	protected void testDelete() {
		Session session = null;

		try {
			session = SessionUtil.getInstance().openSession();
			LoginDao loginDao = new LoginDao();
			int rows = loginDao.delete(loginVo, session);
			System.out.println("Number of rows deleted: " + rows);
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	private LoginVo generateVo() {
		loginVo = new LoginVo();
		loginVo.setCustomerId(Long.valueOf(999));
		loginVo.setPassword("TEST_PASSWORD");

		return loginVo;
	}
}
