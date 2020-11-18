package com.javaduckspizza.OnlineOrderingApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaduckspizza.OnlineOrderingApplication.main.AppController;
import com.javaduckspizza.service.OnlineOrderService;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;

class AppControllerTest {
	private MockMvc mockMvc;
//	@MockBean
//	private OnlineOrderService oosMock;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new AppController()).setControllerAdvice().build();
//		MockedStatic<TypesCache> mockedTypesCache = Mockito.mockStatic(TypesCache.class, "TypesCache");
//		mockedTypesCache.verify(Mockito.times(5), () -> TypesCache.getActiveTypesByCategory(Mockito.anyString()));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetMenu() {
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/menu"));
			ra.andExpect(MockMvcResultMatchers.status().isOk());
//			ra.andExpect(MockMvcResultMatchers.);
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

	@Test
	void testAddItem_Basics() {
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/addItem"));
			ra.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
			ra.andExpect(MockMvcResultMatchers.redirectedUrl("/menu"));

			Map<String, Object> modelMap = ra.andReturn().getModelAndView().getModel();
			assertEquals(modelMap.size(), 1);
			assert(modelMap.containsKey("itemCount"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	void testAddItem_Attributes() {
//		Long crust = Long.valueOf(0L);
//		Long sauce = Long.valueOf(1L);
//		Long size = Long.valueOf(2L);
//		Long cheese = Long.valueOf(3L);
//
//		PizzaToppingAssociationVo ptavBothSides = new PizzaToppingAssociationVo();
//		PizzaToppingAssociationVo ptavLeftOnly = new PizzaToppingAssociationVo();
//		PizzaToppingAssociationVo ptavRightOnly = new PizzaToppingAssociationVo();
//
//		ptavBothSides.setLeft(true);
//		ptavBothSides.setRight(true);
//		ptavBothSides.setToppingsId(5L);
//
//		ptavLeftOnly.setLeft(true);
//		ptavLeftOnly.setRight(false);
//		ptavLeftOnly.setToppingsId(6L);
//
//		ptavRightOnly.setLeft(false);
//		ptavRightOnly.setRight(true);
//		ptavRightOnly.setToppingsId(8L);
//
//		PizzaToppingAssociationVo [] toppingsArray = {ptavBothSides, ptavLeftOnly, ptavRightOnly};
//
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			String jsonToppings = objectMapper.writeValueAsString(toppingsArray);
//			
//
//			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/addItem", crust, sauce, size, cheese, jsonToppings));
//			ra.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//			ra.andExpect(MockMvcResultMatchers.redirectedUrl("/menu"));
//			ra.andExpect(model().attribute("crust", crust));
////			ra.andExpect(MockMvcResultMatchers.jsonPath("crust").value(size));
////			ra.andExpect(MockMvcResultMatchers.jsonPath("crust").value(sauce));
////			ra.andExpect(MockMvcResultMatchers.jsonPath("crust").value(cheese));
//
//			Map<String, Object> modelMap = ra.andReturn().getModelAndView().getModel();
//			assertEquals(modelMap.size(), 1);
//			assert(modelMap.containsKey("itemCount"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	void testRemoveItem() {
//		try {
//			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/removeItem"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	void testAddOrder() {
//		try {
//			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/addorder"));
//			ra.andExpect(MockMvcResultMatchers.status().isOk());
////			ra.andExpect(MockMvcResultMatchers.
//			Map<String, Object> modelMap = ra.andReturn().getModelAndView().getModel();
//			assertEquals(modelMap.size(), 2);
//			assert(modelMap.containsKey("itemsToDelete"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	@Test
	void testCancelOrder() {
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/cancel"));
			ra.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
			ra.andExpect(MockMvcResultMatchers.redirectedUrl("/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddCustomer() {
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post("/addCustomer"));
			ra.andExpect(MockMvcResultMatchers.status().isOk());
			Map<String, Object> modelMap = ra.andReturn().getModelAndView().getModel();
			assertEquals(modelMap.size(), 1);
//			assert(modelMap.containsKey("cartForDisplay"));
//			assert(modelMap.containsKey("total"));
//			assert(modelMap.containsKey("orderNumber"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}