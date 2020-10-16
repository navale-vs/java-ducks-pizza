package com.javaduckspizza.OnlineOrderingApplication.main;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;

@RunWith(MockitoJUnitRunner.class)
class OnlineOrderingApplicationTest {
	@InjectMocks
	private OnlineOrderingApplication onlineOrderingApplication;
	@Mock
	private TypesCache typesCache;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMain() {
//		verify(typesCache, times(4)).getActiveTypesByCategory(Mockito.anyString());
	}

}
