package com.javaduckspizza.dao;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.javaduckspizza.dao.interfaces.IPizzaDao;
import com.javaduckspizza.vo.PizzaVo;

@RunWith(MockitoJUnitRunner.class)
class PizzaDaoTest {
	@Mock
	IPizzaDao pdMock;
	PizzaVo pvTest;

	@BeforeEach
	void setUp() throws Exception {
		pvTest = new PizzaVo();
		pvTest.setCrust(0L);
		pvTest.setId(1L);
		pvTest.setOrderId(1L);
		pvTest.setSauce(1L);
		pvTest.setSize(1L);
		pvTest.setStatus(1L);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	void testGet() {
		
	}
}