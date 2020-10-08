package com.javaduckspizza.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.javaduckspizza.service.dao.CustomerServiceDao;
import com.javaduckspizza.service.dao.ModifierServiceDao;
import com.javaduckspizza.service.dao.OrderServiceDao;
import com.javaduckspizza.service.dao.PizzaServiceDao;
import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.vo.CustomerVo;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesVo;

@RunWith(MockitoJUnitRunner.class)
class OnlineOrderServiceTest {
	@InjectMocks
	OnlineOrderService oos = new OnlineOrderService();
	@Mock
	CustomerServiceDao customerServiceDao;
	@Mock
	ModifierServiceDao modifierServiceDao;
	@Mock
	OrderServiceDao orderServiceDao;
	@Mock
	PizzaServiceDao pizzaServiceDao;
	@Mock
	TypesServiceDao typesServiceDao;


	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/*
	 * Customers 
	 */
	@Test
	void testAddCustomer() {
		String firstName = "FIRST";
		String middleName = "TEST";
		String lastName = "NAME";
		String streetAddress1 = "123 STREET ADDRESS";
		String streetAddress2 = "ADDRESS 2";
		String city = "THE CITY";
		String zipcode5 = "88888";
		String zipcode4 = "2222";
		String phone = "1231231234";
		long state = 13L;
		String email = "account@domain.extension";

		String message = oos.addCustomer(firstName, middleName, lastName, streetAddress1, streetAddress2, city, zipcode5, zipcode4, phone,
				state, email, null);
		verify(customerServiceDao, times(1)).addCustomer(any(CustomerVo.class));
	}

	@Test
	void testGetCustomerById() {
		Long id = Long.valueOf(9L);
		CustomerVo cv = oos.getCustomerById(id);
		verify(customerServiceDao, times(1)).getById(id);
	}

	@Test
	void testGetCustomerByEmail() {
		String emailAddress = "emailAddress";
		CustomerVo cv = oos.getCustomerByEmail(emailAddress);
		verify(customerServiceDao, times(1)).getByEmail(emailAddress);
	}

	@Test
	void updateCustomer() {
		String firstName = "FIRST";
		String middleName = "TEST";
		String lastName = "NAME";
		String streetAddress1 = "123 STREET ADDRESS";
		String streetAddress2 = "ADDRESS 2";
		String city = "THE CITY";
		String zipcode5 = "88888";
		String zipcode4 = "2222";
		String phone = "1231231234";
		long state = 13L;
		String email = "account@domain.extension";

		oos.updateCustomer(firstName, middleName, lastName, streetAddress1, streetAddress2, city, zipcode5, zipcode4, phone,
				state, email, null);
		verify(customerServiceDao, times(1)).updateCustomer(any(CustomerVo.class));
	}

	/*
	 * Modifier
	 */
	@Test
	void testGetCurrentModifierByType() {
		long typeId = 8L;
		ModifierVo modifierVo = oos.getCurrentModifierByType(typeId);
		verify(modifierServiceDao, times(1)).getCurrentByType(typeId);
	}

	/*
	 * Order
	 */
	@Test
	void testAddOrder() {
		long customerId = 90L;
		long retrievalMethod = 101L;
		BigDecimal bdTotal = BigDecimal.valueOf(19.67);
		long status = 51L;
		Timestamp dateTimePlaced = new Timestamp(Calendar.getInstance().getTimeInMillis());
		Timestamp dateTimeDue = new Timestamp(dateTimePlaced.getTime() + 1800000L); //add 30 minutes
		Timestamp dateTimeReady = new Timestamp(dateTimePlaced.getTime() + 1200000L); //add 20 minutes
		Timestamp dateTimeCompleted = new Timestamp(dateTimePlaced.getTime() + 1850000); //customer was late to pickup

		long orderId = oos.addOrder(customerId, retrievalMethod, bdTotal, status, dateTimePlaced, dateTimeDue, dateTimeReady,
				dateTimeCompleted, null);
		verify(orderServiceDao, times(1)).addOrder(any(OrdersVo.class));
	}

	@Test
	void testGetOrderById() {
		long orderId = 34L;
		OrdersVo orderVo = oos.getOrderById(orderId);
		verify(orderServiceDao, times(1)).getById(orderId);
	}

	@Test
	void testGetOrdersByCustomerId() {
		long customerId = 34L;
		List<OrdersVo> lstOrderVo = oos.getOrdersByCustomerId(customerId);
		verify(orderServiceDao, times(1)).getByCustomerId(customerId);
	}

	@Test
	void testGetOrderByStatus() {
		long statusId = 34L;
		List<OrdersVo> lstOrderVo = oos.getOrdersByStatus(statusId);
		verify(orderServiceDao, times(1)).getByStatusId(statusId);
	}

	/*
	 * Pizza
	 */
	@Test
	void testAddPizza() {
		long crust = 34L;
		long orderId = 4L;
		BigDecimal price = BigDecimal.valueOf(10.50);
		long size = 8L;
		long sauce = 77L;
		long status = 98L;

		long id = oos.addPizza(crust, orderId, price, sauce, size, status, null);
		verify(pizzaServiceDao, times(1)).addPizza(any(PizzaVo.class));
	}

	@Test
	void testGetPizzaById() {
		long id = 334L;
		PizzaVo pizzaVo = oos.getPizzaById(id);
		verify(pizzaServiceDao, times(1)).getById(id);
	}

	@Test
	void testGetPizzaByOrderId() {
		long orderId = 334L;
		List<PizzaVo> lstPizzaVo = oos.getPizzaByOrderId(orderId);
		verify(pizzaServiceDao, times(1)).getByOrderId(orderId);
	}

	@Test
	void testGetPizzaByOrderIdAndStatus() {
		long id = 334L;
		long status = 23L;
		List<PizzaVo> lstPizzaVo = oos.getPizzaByOrderIdAndStatus(id, status);
		verify(pizzaServiceDao, times(1)).getByOrderIdAndStatus(id, status);
	}

	/*
	 * Types
	 */
	@Test
	void testGetTypesById() {
		Long id = Long.valueOf(1L);
		TypesVo tv = oos.getTypeById(id);
		verify(typesServiceDao, times(1)).getById(id);
	}

	@Test
	void testGetTypesById_NoObjectForId() {
		long id = -1L;
		TypesVo tv = oos.getTypeById(id);
		assertNull(tv);
		verify(typesServiceDao, times(1)).getById(id);
	}

	@Test
	void testGetTypesBySequenceCode() {
		String sequenceCode = "TEST_0001";
		TypesVo tv = oos.getTypesBySequenceCode(sequenceCode);
		verify(typesServiceDao, times(1)).getBySequenceCode(sequenceCode);
	}

	@Test
	void testGetTypesByCategory() {
		String category = "TEST";
		List<TypesVo> lstTypeVos = oos.getTypesByCategory(category);
		System.out.println("lstTypeVos.size(): " + lstTypeVos.size());
		verify(typesServiceDao, times(1)).getByCategory(category);

		for (TypesVo typesVo : lstTypeVos) {
			System.out.println("typesVo: " + typesVo);
			assertEquals(category, typesVo.getCategory());
		}
	}

	@Test
	void testGetTypesByCategoryAndStatus() {
		String category = "TEST";
		boolean active = true;

		List<TypesVo> lstTypeVos = oos.getTypesByCategoryAndStatus(category, active);
		System.out.println("lstTypeVos.size(): " + lstTypeVos.size());
		verify(typesServiceDao, times(1)).getByCategoryAndStatus(category, active);

		for (TypesVo typesVo : lstTypeVos) {
			System.out.println("typesVo: " + typesVo);
			assertEquals(category, typesVo.getCategory());
		}
	}
}