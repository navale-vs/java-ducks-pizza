package com.javaduckspizza.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.vo.TypesVo;

@RunWith(MockitoJUnitRunner.class)
class OnlineOrderServiceTest {
	@InjectMocks
	OnlineOrderService oos = new OnlineOrderService();
	@Mock
	TypesServiceDao typesServiceDao;
//	@Mock
	Session session;
//	private Map<Long, TypesVo> mapTypeVos = new HashMap<Long, TypesVo>();


	@Before
	public void setUp() throws Exception {
		System.err.println("WORDZ START HEER");
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		System.err.println("WORDZ LEEV HEER");
	}

	@Test
	void testGetTypesById() {
		Long id = Long.valueOf(1L);
		TypesVo typesVo = new TypesVo();
		typesVo.setId(id);

//		when(typesServiceDao.getById(id, session)).thenReturn(typesVo);
		TypesVo tv = oos.getTypeById(id);
		assertEquals(id, tv.getId());
//		verify(typesServiceDao, times(1)).getById(id, session);
	}

	@Test
	void testGetTypesById_NoObjectForId() {
		long id = -1L;
		TypesVo tv = oos.getTypeById(id);
		assertNull(tv);
//		verify(typesServiceDao, times(1)).getById(id, session);
	}

	@Test
	void testGetTypesBySequenceCode() {
		String sequenceCode = "TEST_0001";
		TypesVo tv = oos.getTypesBySequenceCode(sequenceCode);
		assertEquals(sequenceCode, tv.getSequenceCode());
	}

	@Test
	void testGetTypesByCategory() {
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