package com.javaduckspizza.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import com.javaduckspizza.vo.CustomerVo;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesVo;

public class OnlineOrderServiceFt {
	private Client client;
	private OnlineOrderService oos;
	private final String SERVICE_URL = "http://localhost:8095/PizzaService/rest/JavaDucksOnlineOrderingSystem/";
	public static final String SUCCESS_MESSAGE = "</result>SUCCESS</result>";
	public static final String FAILURE_MESSAGE = "</result>FAILURE</result>";
	private static final String PASS = "PASS";
	private static final String FAIL = "FAIL";

	private void init() {
		client = ClientBuilder.newClient();
		oos = new OnlineOrderService();
	}

	public static void main(String[] args) {
		OnlineOrderServiceFt onlineOrderServiceFt = new OnlineOrderServiceFt();
		onlineOrderServiceFt.init();

		try {
			//Customer
			onlineOrderServiceFt.getCustomerById();
			onlineOrderServiceFt.getCustomerByEmail();
			onlineOrderServiceFt.addCustomer();
			//Modifiers
			onlineOrderServiceFt.getCurrentModifierByType();
			//Order
			onlineOrderServiceFt.getOrderById();
			onlineOrderServiceFt.getOrdersByCustomerId();
			onlineOrderServiceFt.getOrdersByStatusId();
			//Pizza
			onlineOrderServiceFt.getPizzaById();
			onlineOrderServiceFt.getPizzaByOrderId();
			onlineOrderServiceFt.addPizza();
			//Types
			onlineOrderServiceFt.getTypesById();
			onlineOrderServiceFt.getTypesBySequenceCode();
			onlineOrderServiceFt.getTypesByCategory();
			onlineOrderServiceFt.getTypesByCategoryAndStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Customer
	 */
	private void getCustomerById() {
		final long id = 1L;
		CustomerVo customerVo = oos.getCustomerById(id);
		System.out.println("getCustomerById: " + ( ((customerVo != null) && (customerVo.getId() == id)) ? PASS : FAIL));
	}

	private void getCustomerByEmail() {
		final String email = "email@domain.com";
		CustomerVo customerVo = oos.getCustomerByEmail(email);
		System.out.println("getCustomerByEmail: " +
				( ((customerVo != null) && email.equals(customerVo.getEmail())) ? PASS : FAIL));
	}

	private void addCustomer() {
//		long id = oos.addCustomer(firstName, middleName, lastName, streetAddress1, streetAddress2, city, zipcode5,
//				zipcode4, phone, state, email, response);

		Form form = new Form();
		form.param("firstName", "First");
		form.param("middleName", "Middle");
		form.param("lastName", "Last");
		form.param("streetAddress1", "5467 Street Pl.");
		form.param("streetAddress2", "");
		form.param("city", "This Town");
		form.param("zipcode5", "54321");
		form.param("zipcode4", "2345");
		form.param("phone", "9898989009");
		form.param("state", "8");
		form.param("email", "test@mail.com");

		String result = client.target(SERVICE_URL + "customer/").request(MediaType.APPLICATION_FORM_URLENCODED).
				post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
		System.out.println("result: " + result);
	}

	/*
	 * Modifiers
	 */
	private void getCurrentModifierByType() {
		final long type_id = 1L;
		ModifierVo modifierVo = oos.getCurrentModifierByType(type_id);
		System.out.println("getCurrentModifierByType(): " +
				(((modifierVo != null) && (modifierVo.getId() == type_id)) ? PASS : FAIL));
	}

	/*
	 * Order
	 */
	private void getOrderById() {
		final long id = 1L;
		OrdersVo ordersVo = oos.getOrderById(id);
		System.out.println("getOrderById: " + ( ((ordersVo != null) && (ordersVo.getId() == id)) ? PASS : FAIL) );
	}

	private void getOrdersByCustomerId() {
		final long id = 1L;
		List<OrdersVo> lstOrdersVo = oos.getOrdersByCustomerId(id);
		System.out.println("getOrdersByCustomerId: " + ( ((lstOrdersVo != null) && !lstOrdersVo.isEmpty()) ? PASS : FAIL) );
		System.out.println("lstOrdersVo.size(): " + lstOrdersVo.size());
	}

	private void getOrdersByStatusId() {
		final long id = 1L;
		List<OrdersVo> lstOrdersVo = oos.getOrdersByStatus(id);
		System.out.println("getOrdersByStatusId: " + ( ((lstOrdersVo != null) && !lstOrdersVo.isEmpty()) ? PASS : FAIL) );
		System.out.println("lstOrdersVo.size(): " + lstOrdersVo.size());
	}

	/*
	 * Pizza
	 */
	private void getPizzaById() {
		final long id = 1L;

		PizzaVo pizzaVo = oos.getPizzaById(id);
		System.out.println("getPizzaById: " + ( ((pizzaVo != null) && (pizzaVo.getId() == id)) ? PASS : FAIL));
	}

	private void getPizzaByOrderId() {
		final long orderId = 1L;
		List<PizzaVo> lstPizza = oos.getPizzaByOrderId(orderId);
		System.out.println("getPizzaByOrderId: " + ( ((lstPizza != null) && !lstPizza.isEmpty()) ? PASS : FAIL));
	}
	
	private void addPizza() {
		System.out.println("In addPizza()...");
//		PizzaVo pizzaVo = new PizzaVo();
//		pizzaVo.setCrust(5L);
//		pizzaVo.setOrderId(4L);
//		pizzaVo.setPrice(BigDecimal.valueOf(12.99));
//		pizzaVo.setSauce(2L);
//		pizzaVo.setSize(3L);
//		pizzaVo.setStatus(9L);
//		oos.addPizza(5L, 4L, BigDecimal.valueOf(12.99), 2L, 3L, 9L);

		Form form = new Form();
		form.param("crust", "5L");
		form.param("orderId", "4L");
		form.param("price", BigDecimal.valueOf(12.99).toString());
		form.param("sauce", "2L");
		form.param("size", "3L");
		form.param("status", "9L");

		try {
			String result = client.target(SERVICE_URL + "pizza/").request(MediaType.APPLICATION_FORM_URLENCODED).
					post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
//		oos.addPizza(crust, orderId, price, sauce, size, status, response)
			System.out.println("result: " + result);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Types
	 */
	private void getTypesById() throws Exception {
		final long id = 1L;
		
		TypesVo tvGet = oos.getTypeById(id);
		System.out.println("getTypesById: " + ( ((tvGet != null) && (tvGet.getId() == id)) ? PASS : FAIL));
	}

	private void getTypesBySequenceCode() throws Exception {
		final String sequence_code = "CHES_0001";
		
		TypesVo tvGet = oos.getTypesBySequenceCode(sequence_code);
		System.out.println("getTypesBySequenceCode: " +
				((tvGet != null) && sequence_code.equals(tvGet.getSequenceCode()) ? PASS : FAIL));
	}

	private void getTypesByCategory() throws Exception {
		final String category_code = "CHES";
		
		List<TypesVo> lst = oos.getTypesByCategory(category_code);
		System.out.println("getTypesByCategory: " + ( ((lst != null) && !lst.isEmpty()) ? PASS : FAIL));
		System.out.println("lst.size(): " + lst.size());
	}

	private void getTypesByCategoryAndStatus() throws Exception {
		final String category_code = "CHES";
		final boolean active = true;
		
		List<TypesVo> lst = oos.getTypesByCategoryAndStatus(category_code, active);
		System.out.println("getTypesByCategory: " + ( ((lst != null) && !lst.isEmpty()) ? PASS : FAIL));
		System.out.println("lst.size(): " + lst.size());
	}
}
