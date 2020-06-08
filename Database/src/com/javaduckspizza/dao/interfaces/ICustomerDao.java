package com.javaduckspizza.dao.interfaces;

import org.hibernate.Session;

import com.javaduckspizza.vo.CustomerVo;

public interface ICustomerDao {
	public long insert(CustomerVo cv, Session session);
	public int delete(long id, Session session);
	public int update(CustomerVo cv, Session session);
	public CustomerVo getById(long id, Session session);
	public CustomerVo getByEmail(String email, Session session);
}
