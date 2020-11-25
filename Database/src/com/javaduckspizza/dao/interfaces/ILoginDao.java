package com.javaduckspizza.dao.interfaces;

import org.hibernate.Session;

import com.javaduckspizza.vo.LoginVo;

public interface ILoginDao {
	public Long insert(LoginVo loginVo, Session session);
	public LoginVo getByCustomerAndPassword(LoginVo loginVo, Session session);
	public int delete(LoginVo loginVo, Session session);
}
