package com.javaduckspizza.OnlineOrderingApplication.common;

import java.util.ArrayList;
import java.util.List;

import com.javaduckspizza.service.OnlineOrderService;
import com.javaduckspizza.service.dao.TypesServiceDao;
import com.javaduckspizza.vo.TypesVo;

public class TypesCache {
	private static List<TypesVo> lstAllCachedTypesVos = new ArrayList<TypesVo>();

	public static List<TypesVo> getActiveTypesByCategory(OnlineOrderService onlineOrderService, String category) {
		List<TypesVo> list = new ArrayList<TypesVo>();

		for (TypesVo typesVo : lstAllCachedTypesVos) {
			if(typesVo.getCategory().equals(category)) {
				list.add(typesVo);
			}
		}

		if(list.size() == 0) {
			list = onlineOrderService.getTypesByCategoryAndStatus(category, true);
			lstAllCachedTypesVos.addAll(list);
			System.err.println("for " + category + " list.size(): " + ((list == null) ? "null" : list.size()));
		}

		return list;
	}

	public static TypesVo getBySequenceCode(OnlineOrderService onlineOrderService, String sequenceCode) {
		for (TypesVo typeVo : lstAllCachedTypesVos) {
			if(typeVo.getSequenceCode().equals(sequenceCode)) {
				return typeVo;
			}
		}

		TypesVo typeVo = onlineOrderService.getTypeBySequenceCode(sequenceCode);

		if(typeVo != null) {
			lstAllCachedTypesVos.add(typeVo);
		}

		System.err.println("for " + sequenceCode + ": " + ((typeVo == null) ? "null" : "not null"));
		return typeVo;
	}

	public static TypesVo getById(OnlineOrderService onlineOrderService, long id) {
		for (TypesVo typeVo : lstAllCachedTypesVos) {
			if(typeVo.getId() == id) {
				return typeVo;
			}
		}

		TypesVo typeVo = new TypesServiceDao().getById(id);

		if(typeVo != null) {
			lstAllCachedTypesVos.add(typeVo);
		}

		System.err.println("for " + id + ": " + ((typeVo == null) ? "null" : "not null"));
		return typeVo;
	}
}