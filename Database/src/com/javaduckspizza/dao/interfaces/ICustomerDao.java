package com.javaduckspizza.dao.interfaces;

import org.hibernate.Session;

import com.javaduckspizza.vo.CustomersVo;

public interface ICustomerDao {
	public long insert(CustomersVo cv, Session session);
	public int delete(long id, Session session);
	public int update(CustomersVo cv, Session session);
	public CustomersVo getById(long id, Session session);
	public CustomersVo getByEmail(String email, Session session);
}
