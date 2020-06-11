package com.javaduckspizza.service.dao;

import org.hibernate.Session;

import com.javaduckspizza.dao.CustomerDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.CustomerVo;

public class CustomerServiceDao {
	private CustomerDao customerDao = new CustomerDao();

	public CustomerVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		CustomerVo cv = customerDao.getById(id, session);
		session.close();

		return cv;
	}

	public CustomerVo getByEmail(String email) {
		Session session = SessionUtil.getInstance().openSession();
		CustomerVo cv = customerDao.getByEmail(email, session);
		session.close();

		return cv;
	}
}
