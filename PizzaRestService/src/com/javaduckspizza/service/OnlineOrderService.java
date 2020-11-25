package com.javaduckspizza.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.javaduckspizza.service.dao.CustomerServiceDao;
import com.javaduckspizza.service.dao.LoginServiceDao;
import com.javaduckspizza.service.dao.ModifierServiceDao;
import com.javaduckspizza.service.dao.OrderServiceDao;
import com.javaduckspizza.service.dao.PizzaServiceDao;
import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.vo.CustomerVo;
import com.javaduckspizza.vo.LoginVo;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesVo;  

@Path("/JavaDucksOnlineOrderingSystem")
public class OnlineOrderService {
	public static final String SUCCESS_MESSAGE = "</result>SUCCESS</result>";
	public static final String FAILURE_MESSAGE = "</result>FAILURE</result>";

	private CustomerServiceDao customerServiceDao = new CustomerServiceDao();
	private ModifierServiceDao modifierServiceDao = new ModifierServiceDao();
	private OrderServiceDao orderServiceDao = new OrderServiceDao();
	private PizzaServiceDao pizzaServiceDao = new PizzaServiceDao();
	private TypesServiceDao typesServiceDao = new TypesServiceDao();

	public OnlineOrderService() {
	}

	/**
	 * Customers
	 */
	@POST
	@Path("/customer/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Long addCustomer(@FormParam("firstName") String firstName, @FormParam("middleName") String middleName,
			@FormParam("lastName") String lastName, @FormParam("streetAddress1") String streetAddress1,
			@FormParam("streetAddress2") String streetAddress2, @FormParam("city") String city, @FormParam("zipcode5") String zipcode5,
			@FormParam("zipcode4") String zipcode4, @FormParam("phone") String phone, @FormParam("state") long state,
			@FormParam("email") String email, HttpServletResponse response) {
		System.out.println("In addCustomer()...");
		CustomerVo customerVo = new CustomerVo();
		customerVo.setFirstName(firstName);
		customerVo.setMiddleName(middleName);
		customerVo.setLastName(lastName);
		customerVo.setStreetAddress1(streetAddress1);
		customerVo.setStreetAddress2(streetAddress2);
		customerVo.setCity(city);
		customerVo.setZipcode5(zipcode5);
		customerVo.setZipcode4(zipcode4);
		customerVo.setPhone(phone);
		customerVo.setEmail(email);
		customerVo.setState(state);
		long id = customerServiceDao.addCustomer(customerVo);
		System.out.println("id: " + id);

//		return ((id > 0) ? SUCCESS_MESSAGE : FAILURE_MESSAGE);
		return id;
	}

	@PUT
	@Path("/customer/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public int updateCustomer(@FormParam("firstName") String firstName, @FormParam("middleName") String middleName,
			@FormParam("lastName") String lastName, @FormParam("streetAddress1") String streetAddress1,
			@FormParam("streetAddress2") String streetAddress2, @FormParam("city") String city, @FormParam("zipcode5") String zipcode5,
			@FormParam("zipcode4") String zipcode4, @FormParam("phone") String phone, @FormParam("state") long state,
			@FormParam("email") String email, HttpServletResponse response) {
		CustomerVo customerVo = new CustomerVo();
		customerVo.setFirstName(firstName);
		customerVo.setMiddleName(middleName);
		customerVo.setLastName(lastName);
		customerVo.setStreetAddress1(streetAddress1);
		customerVo.setStreetAddress2(streetAddress2);
		customerVo.setCity(city);
		customerVo.setZipcode5(zipcode5);
		customerVo.setZipcode4(zipcode4);
		customerVo.setPhone(phone);
		customerVo.setEmail(email);
		customerVo.setState(state);
		int rowsUpdated = customerServiceDao.updateCustomer(customerVo);

		return rowsUpdated;
	}

	@GET
	@Path("/customer/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerVo getCustomerById(@PathParam(value = "id") long id) {
		return customerServiceDao.getById(id);
	}

	@GET
	@Path("/customer/email/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerVo getCustomerByEmail(@PathParam(value = "email") String email, HttpServletResponse response) {
		return customerServiceDao.getByEmail(email);
	}

	/*
	 * Modifiers
	 */
	@GET
	@Path("/modifiers/currentbytype/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ModifierVo getCurrentModifierByType(@PathParam(value = "type") long type) {
		return modifierServiceDao.getCurrentByType(type);
	}

	/*
	 * Orders
	 */
	@POST
	@Path("/orders")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public long addOrder(@FormParam("customerId") long customerId, @FormParam("retrievalMethod") long retrievalMethod,
			@FormParam("total") BigDecimal total, @FormParam("status") long status, @FormParam("dateTimePlaced") Timestamp dateTimePlaced,
			@FormParam("dateTimeDue") Timestamp dateTimeDue, @FormParam("dateTimeReady") Timestamp dateTimeReady,
			@FormParam("dateTimeCompleted") Timestamp dateTimeCompleted, @Context HttpServletResponse response) {
		OrdersVo ordersVo = new OrdersVo();
		ordersVo.setCustomerId(customerId);
		ordersVo.setRetrievalMethod(retrievalMethod);
		ordersVo.setTotal(total);
		ordersVo.setStatus(status);
		ordersVo.setDateTimePlaced(dateTimePlaced);
		ordersVo.setDateTimeDue(dateTimeDue);
		ordersVo.setDateTimeReady(dateTimeReady);
		ordersVo.setDateTimeCompleted(dateTimeCompleted);
		long orderId = orderServiceDao.addOrder(ordersVo);

		return orderId;
	}

	@GET
	@Path("/orders/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrdersVo getOrderById(@PathParam(value = "id") long id) {
		return orderServiceDao.getById(id);
	}

	@GET
	@Path("/orders/customerid/{customerid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrdersVo> getOrdersByCustomerId(@PathParam(value = "id") long id) {
		return orderServiceDao.getByCustomerId(id);
	}

	@GET
	@Path("/orders/statusid/{statusid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrdersVo> getOrdersByStatus(@PathParam(value = "id") long id) {
		return orderServiceDao.getByStatusId(id);
	}

	/*
	 * Pizza
	 */
	@GET
	@Path("/pizza/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PizzaVo getPizzaById(@PathParam(value = "id") long id) {
		PizzaVo pv = pizzaServiceDao.getById(id);
		return pv;
	}

	@GET
	@Path("/pizza/orderid/{orderid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PizzaVo> getPizzaByOrderId(@PathParam(value = "orderId") long orderId, HttpServletResponse response) {
		List<PizzaVo> lstPizzaVo = pizzaServiceDao.getByOrderId(orderId);
		return lstPizzaVo;
	}

	@GET
	@Path("/pizza/orderidandstatus/{orderid}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PizzaVo> getPizzaByOrderIdAndStatus(@PathParam(value = "orderId") long orderId,
			@PathParam(value = "status") long status) {
//		Session session = SessionUtil.getInstance().openSession();
		List<PizzaVo> lstPv = pizzaServiceDao.getByOrderIdAndStatus(orderId, status);
//		session.close();
		return lstPv;
	}

	@GET
	@Path("/pizza/orderidandstatus/{orderid}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PizzaToppingAssociationVo> getToppingsByPizza(@PathParam(value = "orderId") long pizzaId) {
		List<PizzaToppingAssociationVo> lstPtav = pizzaServiceDao.getToppingsByPizza(pizzaId);
		return lstPtav;
	}	

	@POST
	@Path("/pizza/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public long addPizza(@FormParam("cheese") long cheese, @FormParam("crust") long crust, @FormParam("orderId") long orderId, @FormParam("price") BigDecimal price,
			@FormParam("sauce") long sauce, @FormParam("size") long size, @FormParam("status") long status,
			List<PizzaToppingAssociationVo> lstToppings, @Context HttpServletResponse response) {
		PizzaVo pizzaVo = new PizzaVo();
		pizzaVo.setCheese(cheese);
		pizzaVo.setCrust(crust);
		pizzaVo.setOrderId(orderId);
		pizzaVo.setPrice(price);
		pizzaVo.setSauce(sauce);
		pizzaVo.setSize(size);
		pizzaVo.setStatus(status);

		long id = pizzaServiceDao.addPizza(pizzaVo);

		for (PizzaToppingAssociationVo ptav : lstToppings) {
			ptav.setPizzaId(pizzaVo.getId());
			pizzaServiceDao.addPizzaToppingAssociation(ptav);
		}

		return id;
	}
	/*
	 * Types
	 */
	@GET
	@Path("/types/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TypesVo getTypeById(@PathParam(value = "id") long id) {
//		Session session = SessionUtil.getInstance().openSession();
		TypesVo tv = typesServiceDao.getById(id);
//		session.close();

		return tv;
	}

	@GET
	@Path("/types/sequenceCode/{sequenceCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public TypesVo getTypeBySequenceCode(@PathParam(value = "sequenceCode") String sequenceCode) {
//		Session session = SessionUtil.getInstance().openSession();
		TypesVo tv = typesServiceDao.getBySequenceCode(sequenceCode);
//		session.close();

		return tv;
	}

	@GET
	@Path("/types/category/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypesVo> getTypesByCategory(@PathParam(value = "category") String category) {
		List<TypesVo> lst = typesServiceDao.getByCategory(category);

		return lst;
	}

	@GET
	@Path("/types/category/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypesVo> getTypesByCategoryAndStatus(@PathParam(value = "category") String category,
			@PathParam(value = "category") boolean active) {
		List<TypesVo> lst = typesServiceDao.getByCategoryAndStatus(category, active);

		return lst;
	}

	@POST
	@Path("/login/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public long addLogin(@FormParam("email") String email, @FormParam("password") String password,
			@Context HttpServletResponse response) {
		CustomerVo cv = getCustomerByEmail(email, response);
		LoginVo loginVo= new LoginVo();
		loginVo.setCustomerId(cv.getId());
		loginVo.setPassword(password);

		LoginServiceDao lsd = new LoginServiceDao();
		lsd.addLogin(loginVo);

		return cv.getId(); //maybe change this to return something else?
	}


	@GET
	@Path("/login/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public long getLogin(@FormParam("email") String email, @FormParam("password") String password,
			@Context HttpServletResponse response) {
		CustomerVo cv = getCustomerByEmail(email, response);
		LoginVo loginVo= new LoginVo();
		loginVo.setCustomerId(cv.getId());
		loginVo.setPassword(password);

		return cv.getId(); //maybe change this to return something else?
	}
}