package com.javaduckspizza.OnlineOrderingApplication.common;

import java.util.List;

import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.vo.TypesVo;

public class TypesCache {
	private static List<TypesVo> sizes;
	private static List<TypesVo> crusts;
	private static List<TypesVo> sauces;
	private static List<TypesVo> cheeses;
	private static List<TypesVo> toppings;

	public static void initCache() {
		sizes = loadActiveTypesByCategory("SIZE");
		crusts = loadActiveTypesByCategory("CRST");
		sauces = loadActiveTypesByCategory("SAUC");
		cheeses = loadActiveTypesByCategory("CHES");
		toppings = loadActiveTypesByCategory("TOPP");
	}

	protected static List<TypesVo> loadActiveTypesByCategory(String category) {
		List<TypesVo> list = new TypesServiceDao().getByCategoryAndStatus(category, true);
		System.err.println("for " + category + " list.size(): " + ((list == null) ? "null" : list.size()));
		return list;
	}

	public static List<TypesVo> getSizes() {
		return sizes;
	}

	public static List<TypesVo> getCrusts() {
		return crusts;
	}

	public static List<TypesVo> getSauces() {
		return sauces;
	}

	public static List<TypesVo> getCheeses() {
		return cheeses;
	}

	public static List<TypesVo> getToppings() {
		return toppings;
	}
}