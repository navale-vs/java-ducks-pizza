package com.javaduckspizza.OnlineOrderingApplication.common;

import java.util.ArrayList;
import java.util.List;

import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.vo.TypesVo;

public class TypesCache {
	private static List<TypesVo> lstAllCachedTypes = new ArrayList<TypesVo>();
//	private static List<TypesVo> sizes;
//	private static List<TypesVo> crusts;
//	private static List<TypesVo> sauces;
//	private static List<TypesVo> cheeses;
//	private static List<TypesVo> toppings;

//	public static void initCache() {
//		sizes = loadActiveTypesByCategory("SIZE");
//		crusts = loadActiveTypesByCategory("CRST");
//		sauces = loadActiveTypesByCategory("SAUC");
//		cheeses = loadActiveTypesByCategory("CHES");
//		toppings = loadActiveTypesByCategory("TOPP");
//	}

	public static List<TypesVo> getActiveTypesByCategory(String category) {
		List<TypesVo> list = new ArrayList<TypesVo>();

		for (TypesVo typeVo : lstAllCachedTypes) {
			if(typeVo.getCategory().equals(category)) {
				list.add(typeVo);
			}
		}

		if(list.size() == 0) {
			list = new TypesServiceDao().getByCategoryAndStatus(category, true);
			lstAllCachedTypes.addAll(list);
			System.err.println("for " + category + " list.size(): " + ((list == null) ? "null" : list.size()));
		}

		return list;
	}

	public static TypesVo getBySequenceCode(String sequenceCode) {
		for (TypesVo typeVo : lstAllCachedTypes) {
			if(typeVo.getSequenceCode().equals(sequenceCode)) {
				return typeVo;
			}
		}

		TypesVo typeVo = new TypesServiceDao().getBySequenceCode(sequenceCode);

		if(typeVo != null) {
			lstAllCachedTypes.add(typeVo);
		}

		System.err.println("for " + sequenceCode + ": " + ((typeVo == null) ? "null" : "not null"));
		return typeVo;
	}

	public static TypesVo getById(long id) {
		for (TypesVo typeVo : lstAllCachedTypes) {
			if(typeVo.getId() == id) {
				return typeVo;
			}
		}

		TypesVo typeVo = new TypesServiceDao().getById(id);

		if(typeVo != null) {
			lstAllCachedTypes.add(typeVo);
		}

		System.err.println("for " + id + ": " + ((typeVo == null) ? "null" : "not null"));
		return typeVo;
	}

//	public static List<TypesVo> getSizes() {
//		return sizes;
//	}
//
//	public static List<TypesVo> getCrusts() {
//		return crusts;
//	}
//
//	public static List<TypesVo> getSauces() {
//		return sauces;
//	}
//
//	public static List<TypesVo> getCheeses() {
//		return cheeses;
//	}
//
//	public static List<TypesVo> getToppings() {
//		return toppings;
//	}
}