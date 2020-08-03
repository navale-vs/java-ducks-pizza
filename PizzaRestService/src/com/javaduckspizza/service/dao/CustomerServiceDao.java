package com.javaduckspizza.service.dao;

import org.hibernate.Session;

import com.javaduckspizza.dao.CustomerDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.CustomerVo;

public class CustomerServiceDao {
	private CustomerDao customerDao = new CustomerDao();

	public long addCustomer(CustomerVo customerVo) {
		Session session = SessionUtil.getInstance().openSession();
		long id = customerDao.insert(customerVo, session);
		session.close();
		
		return id;
	}

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

	public int updateCustomer(CustomerVo customerVo) {
		Session session = SessionUtil.getInstance().openSession();
		int rowsUpdated = customerDao.update(customerVo, session);
		session.close();
		return rowsUpdated;
	}
}