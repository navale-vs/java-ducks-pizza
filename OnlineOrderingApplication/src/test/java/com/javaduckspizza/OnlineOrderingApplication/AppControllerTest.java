package com.javaduckspizza.OnlineOrderingApplication;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.ui.Model;

import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;
import com.javaduckspizza.OnlineOrderingApplication.main.AppController;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest(TypesCache.class)
class AppControllerTest {
	@InjectMocks
	private AppController appController;
	@Mock
	private Model model; //mocking here because any() returns null instance

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
//		PowerMockito.mockStatic(TypesCache.class);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetMenu() {
		String result = appController.getMenu(model);
		assertEquals(result, "/menu."); //changed how this method works, so not much to test here.
		verify(appController, times(1)).addAttributesForMenu(model);
	}

	@Test
	void testGetTypeById() {
		
	}
}