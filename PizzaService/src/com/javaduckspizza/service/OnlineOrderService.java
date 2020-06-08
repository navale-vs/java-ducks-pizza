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
import com.javaduckspizza.vo.TypesVo;  

@Path("/JavaDucksOnlineOrderingSystem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class OnlineOrderService {
	public static final String SUCCESS_MESSAGE = "</result>SUCCESS</result>";
	public static final String FAILURE_MESSAGE = "</result>FAILURE</result>";

	@Inject
	private PizzaServiceDao pizzaServiceDao;
	@Inject
	private TypesServiceDao typesServiceDao;

	public OnlineOrderService() {
		pizzaServiceDao = new PizzaServiceDao();
		typesServiceDao = new TypesServiceDao();
	}

	@GET
	@Path("/types/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TypesVo getTypesById(@PathParam(value = "id") long id) {
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