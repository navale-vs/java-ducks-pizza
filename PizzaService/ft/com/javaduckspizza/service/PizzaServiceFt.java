package com.javaduckspizza.service;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.javaduckspizza.vo.TypesVo;

public class PizzaServiceFt {
	private Client client;
	private final String SERVICE_URL = "http://localhost:8095/PizzaService/rest/JavaDucksOnlineOrderingSystem/types";
	public static final String SUCCESS_MESSAGE = "</result>SUCCESS</result>";
	public static final String FAILURE_MESSAGE = "</result>FAILURE</result>";
	private static final String PASS = "pass";
	private static final String FAIL = "fail";

	private void init() {
		client = ClientBuilder.newClient();
	}

	public static void main(String[] args) {
		PizzaServiceFt pizzaServiceFt = new PizzaServiceFt();
		pizzaServiceFt.init();

		try {
			pizzaServiceFt.getTypesById();
			pizzaServiceFt.getTypesBySequenceCode();
			pizzaServiceFt.getTypesByCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getTypesById() throws Exception {
		final long id = 1L;
		
		TypesVo tvGet = client.target(SERVICE_URL + "/id/").path("/{id}").resolveTemplate("id", id).
				request(MediaType.APPLICATION_JSON).get(TypesVo.class);
		System.out.println(( ((tvGet != null) && (tvGet.getId() == id)) ? PASS : FAIL));
	}

	private void getTypesBySequenceCode() throws Exception {
		final String sequence_code = "CHES_0001";
		
		TypesVo tvGet = client.target(SERVICE_URL + "/sequenceCode/").path("/{sequenceCode}").
				resolveTemplate("sequenceCode", sequence_code).request(MediaType.APPLICATION_JSON).get(TypesVo.class);
		System.out.println( ((tvGet != null) && sequence_code.equals(tvGet.getSequenceCode())) ? PASS : FAIL);
	}

	private void getTypesByCategory() throws Exception {
//		TypesVo tv = new TypesVo();
//		tv.setCategory("CHES");
		final String category_code = "CHES";
		
		List<TypesVo> lst = client.target(SERVICE_URL + "/category/").path("/{category}").
				resolveTemplate("category", category_code).request(MediaType.APPLICATION_JSON).get(List.class);
		System.out.println( ((lst != null) && lst.isEmpty()) ? PASS : FAIL);
		System.out.println("lst.size(): " + lst.size());
//		System.out.println(((lst.size() == 2) ? PASS : FAIL));
	}
}
