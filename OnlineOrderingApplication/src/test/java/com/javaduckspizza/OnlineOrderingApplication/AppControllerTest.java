package com.javaduckspizza.OnlineOrderingApplication;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javaduckspizza.OnlineOrderingApplication.main.AppController;

class AppControllerTest {
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new AppController()).setControllerAdvice().build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetMenu() {
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/menu"));
//			ra.andExpect()
			Map<String, Object> modelMap = ra.andReturn().getModelAndView().getModel();
			assertEquals(modelMap.size(), 6);
			assert(modelMap.containsKey("sizes"));
			assert(modelMap.containsKey("crusts"));
			assert(modelMap.containsKey("sauces"));
			assert(modelMap.containsKey("cheeses"));
			assert(modelMap.containsKey("toppings"));
			assert(modelMap.containsKey("itemCount"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testViewCart() {
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/cart"));
			Map<String, Object> modelMap = ra.andReturn().getModelAndView().getModel();
			assertEquals(modelMap.size(), 2);
			assert(modelMap.containsKey("cartForDisplay"));
			assert(modelMap.containsKey("total"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	void testAddItem() {
//		appController.addItem(model, Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
//				Mockito.anyLong(), Mockito.anyString());
////		assertEquals(appController.mapShoppingCart.size(), 1);
//		verify(model, times(1)).addAttribute(Mockito.anyString(), Mockito.anyMap());
//		verify(model, times(1)).addAttribute(Mockito.anyString(), Mockito.anyInt());
//		verify(appController, times(1)).getMenu(model); //probably won't work, since appController isn't mock
//	}
//
//	@Test
//	void testRemoveItem() {
//		verify(model, times(1)).addAttribute(Mockito.anyString(), Mockito.anyMap());
//	}
//
//
//	@Test
//	void testAddOrder() {
////		String result = appController.addOrder(model);
////		assertEquals(result, "/checkout.");
//	}
//
//
//	@Test
//	void testCancelOrder() {
//		String result = appController.cancelOrder(model);
//		assertEquals(result, "redirect:/");
//	}
//
//
//	@Test
//	void testGetTypeById() {
//		
//	}
//
//	@Test
//	void testAddAttributesForMenu() {
//		
//	}
//
//	@Test
//	void testPrepareCartForDisplay() {
//		
//	}
//
//	void testGenerateDisplayStringForPizza() {
//		
//	}
//
//	@Test
//	void testCalculatePrice() {
//		MockedStatic<ModifierServiceDao> mockedMsd = Mockito.mockStatic(ModifierServiceDao.class);
//		mockedMsd.verify(Mockito.times(3), () -> ModifierServiceDao.getCurrentByType(Mockito.anyLong()));
//	}
//
//	@Test
//	void testCalculateTotal() {
//		Collection<BigDecimal> col = new ArrayList<BigDecimal>();
//		col.add(BigDecimal.TEN);
//		col.add(BigDecimal.ONE);
////		Mockito.when(appController.calculateTotal(col)).thenReturn(BigDecimal.valueOf(11));
//	}
}