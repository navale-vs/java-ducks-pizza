package com.javaduckspizza.service.dao;

import org.hibernate.Session;

import com.javaduckspizza.dao.CustomerDao;
import com.javaduckspizza.dao.interfaces.ICustomerDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.CustomerVo;

public class CustomerServiceDao {
	private ICustomerDao iCustomerDao = new CustomerDao();

	public long addCustomer(CustomerVo customerVo) {
		Session session = SessionUtil.getInstance().openSession();
		long id = iCustomerDao.insert(customerVo, session);
		session.close();
		
		return id;
	}

	public CustomerVo getById(long id) {
		Session session = SessionUtil.getInstance().openSession();
		CustomerVo cv = iCustomerDao.getById(id, session);
		session.close();

		return cv;
	}

	public CustomerVo getByEmail(String email) {
		Session session = SessionUtil.getInstance().openSession();
		CustomerVo cv = iCustomerDao.getByEmail(email, session);
		session.close();

		return cv;
	}

	public int updateCustomer(CustomerVo customerVo) {
		Session session = SessionUtil.getInstance().openSession();
		int rowsUpdated = iCustomerDao.update(customerVo, session);
		session.close();
		return rowsUpdated;
	}
}