package com.javaduckspizza.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
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

		PizzaVo pizzaVo = new PizzaVo();
		pizzaVo.setCrust(crust);
		pizzaVo.setOrderId(orderId);
		pizzaVo.setPrice(price);
		pizzaVo.setSauce(sauce);
		pizzaVo.setSize(size);
		pizzaVo.setStatus(status);
		

		oos.addPizza(crust, orderId, price, sauce, size, status, null);
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

//	private void populateTypesVoList() {
//		mapTypeVos = new HashMap<Long, TypesVo>();
//
//		TypesVo tvTest01 = new TypesVo();
//		tvTest01.setActive(true);
//		tvTest01.setCategory("TEST");
//		tvTest01.setDescription("Test TEST 01");
//		tvTest01.setId(1L);
//		tvTest01.setSequenceCode("TEST_0001");
//
//		TypesVo tvTest02 = new TypesVo();
//		tvTest02.setActive(true);
//		tvTest02.setCategory("TEST");
//		tvTest02.setDescription("Test TEST 02");
//		tvTest02.setId(1L);
//		tvTest02.setSequenceCode("TEST_0002");
//
//		TypesVo tvTest03 = new TypesVo();
//		tvTest03.setActive(true);
//		tvTest03.setCategory("TEST");
//		tvTest03.setDescription("Test TEST 03");
//		tvTest03.setId(1L);
//		tvTest03.setSequenceCode("TEST_0003");
//
//		mapTypeVos.put(tvTest01.getId(), tvTest01);
//		mapTypeVos.put(tvTest02.getId(), tvTest02);
//		mapTypeVos.put(tvTest03.getId(), tvTest03);
//	}
}