package com.javaduckspizza.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javaduckspizza.dao.interfaces.ICustomerDao;
import com.javaduckspizza.vo.CustomerVo;

public class CustomerDao implements ICustomerDao {
	private static final String HQL_UPDATE = "UPDATE CustomerVo " +
			"SET FIRST_NAME = :firstName, MIDDLE_NAME = :middleName, LAST_NAME = :lastName, PHONE = :phone, " +
			"STREET_ADDRESS_1 = :streetAddress1, STREET_ADDRESS_2 = :streetAddress2," +
			"CITY = :city, STATE = :state, ZIPCODE_5 = :zipcode5, ZIPCODE_4 = :zipcode4, EMAIL = :email " +
			"WHERE ID = :id";
	private static final String HQL_DELETE = "DELETE FROM CustomerVo WHERE ID = :id";
	private static final String HQL_GET_BY_ID = "FROM CustomerVo WHERE ID = :id";
	private static final String HQL_GET_BY_EMAIL = "FROM CustomerVo WHERE EMAIL = :email";

	@Override
	public long insert(CustomerVo cv, Session session) {
		Transaction txn = session.beginTransaction();
		long id = (long) session.save(cv);
		txn.commit();

		return id;
	}

	@Override
	public int delete(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query<CustomerDao> query = session.createQuery(HQL_DELETE);
		query.setParameter("id", id);
		int rows = query.executeUpdate();
		txn.commit();
		
		return rows;
	}

	@Override
	public int update(CustomerVo cv, Session session) {
		Transaction txn = session.beginTransaction();
		Query<CustomerDao> query = session.createQuery(HQL_UPDATE);
		query.setParameter("firstName", cv.getFirstName());
		query.setParameter("middleName", cv.getMiddleName());
		query.setParameter("lastName", cv.getLastName());
		query.setParameter("phone", cv.getPhone());
		query.setParameter("streetAddress1", cv.getStreetAddress1());
		query.setParameter("streetAddress2", cv.getStreetAddress2());
		query.setParameter("city", cv.getCity());
		query.setParameter("state", cv.getState());
		query.setParameter("zipcode5", cv.getZipcode5());
		query.setParameter("zipcode4", cv.getZipcode4());
		query.setParameter("email", cv.getEmail());
		query.setParameter("id", cv.getId());

		int rows = query.executeUpdate();
		txn.commit();
		return rows;
	}

	@Override
	public CustomerVo getById(long id, Session session) {
		Transaction txn = session.beginTransaction();
		Query<CustomerVo> query = session.createQuery(HQL_GET_BY_ID);
		query.setParameter("id", id);
		List<CustomerVo> lst = query.list();
		txn.commit();

		return (((lst == null) || lst.isEmpty()) ? null : lst.get(0));
	}

	@Override
	public CustomerVo getByEmail(String email, Session session) {
		Transaction txn = session.beginTransaction();
		Query<CustomerVo> query = session.createQuery(HQL_GET_BY_EMAIL);
		query.setParameter("email", email);
		List<CustomerVo> lstCv = query.list();
		txn.commit();

		return (((lstCv == null) || lstCv.isEmpty()) ? null : lstCv.get(0));
	}
}