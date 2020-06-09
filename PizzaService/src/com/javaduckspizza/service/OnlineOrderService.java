package com.javaduckspizza.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import com.javaduckspizza.service.dao.PizzaServiceDao;
import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.util.SessionUtil;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesVo;  

@Path("/JavaDucksOnlineOrderingSystem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class OnlineOrderService {
	public static final String SUCCESS_MESSAGE = "</result>SUCCESS</result>";
	public static final String FAILURE_MESSAGE = "</result>FAILURE</result>";

	@Inject
	protected PizzaServiceDao pizzaServiceDao = new PizzaServiceDao();
	@Inject
	protected TypesServiceDao typesServiceDao = new TypesServiceDao();

	public OnlineOrderService() {
//		pizzaServiceDao = new PizzaServiceDao();
//		typesServiceDao = new TypesServiceDao();
	}

	/*
	 * Pizza
	 */
	@GET
	@Path("/pizza/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PizzaVo getPizzaById(@PathParam(value = "id") long id) {
		Session session = SessionUtil.getInstance().openSession();
		PizzaVo pv = pizzaServiceDao.getById(id, session);
		session.close();
		return pv;
	}

	@GET
	@Path("/pizza/orderid/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PizzaVo getPizzaByOrderId(@PathParam(value = "orderId") long orderId) {
		Session session = SessionUtil.getInstance().openSession();
		PizzaVo pv = pizzaServiceDao.getById(orderId, session);
		session.close();
		return pv;
	}

	@GET
	@Path("/pizza/orderidandstatus/{orderId}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PizzaVo> getPizzaByOrderIdAndStatus(@PathParam(value = "orderId") long orderId,
			@PathParam(value = "status") long status) {
		Session session = SessionUtil.getInstance().openSession();
		List<PizzaVo> lstPv = pizzaServiceDao.getByOrderIdAndStatus(orderId, status, session);
		session.close();
		return lstPv;
	}

	/*
	 * Types
	 */
	@GET
	@Path("/types/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TypesVo getTypeById(@PathParam(value = "id") long id) {
		Session session = SessionUtil.getInstance().openSession();
		TypesVo tv = typesServiceDao.getById(id, session);
		session.close();

		return tv;
	}

	@GET
	@Path("/types/sequenceCode/{sequenceCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public TypesVo getTypesBySequenceCode(@PathParam(value = "sequenceCode") String sequenceCode) {
		Session session = SessionUtil.getInstance().openSession();
		TypesVo tv = typesServiceDao.getBySequenceCode(sequenceCode, session);
		session.close();

		return tv;
	}

	@GET
	@Path("/types/category/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypesVo> getTypesByCategory(@PathParam(value = "category") String category) {
		Session session = SessionUtil.getInstance().openSession();
		List<TypesVo> lst = typesServiceDao.getByCategory(category, session);
		session.close();

		return lst;
	}

}