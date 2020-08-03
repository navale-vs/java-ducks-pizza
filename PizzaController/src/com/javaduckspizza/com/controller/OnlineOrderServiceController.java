package com.javaduckspizza.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaduckspizza.vo.TypesVo;

@Controller
@RequestMapping("/JavaDucksOnlineOrderingSystem")
public class OnlineOrderServiceController {
	@RequestMapping(method = RequestMethod.GET, path="/modifiers/")
	public TypesVo getTypesByCategory() {
		return new TypesVo();
	}
}