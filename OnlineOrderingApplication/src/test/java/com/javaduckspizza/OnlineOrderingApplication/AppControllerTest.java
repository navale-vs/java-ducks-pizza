package com.javaduckspizza.OnlineOrderingApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.javaduckspizza.OnlineOrderingApplication.main.AppController;

@RunWith(MockitoJUnitRunner.class)
class AppControllerTest {
	@InjectMocks
	private AppController appController;
	@Mock
	private Model model; //mocking here because any() returns null instance

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetMenu() {
		String result = appController.getMenu(model);
		assertEquals(result, "/menu."); //changed how this method works, so not much to test here.
	}

}