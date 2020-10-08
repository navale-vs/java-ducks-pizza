package com.javaduckspizza.OnlineOrderingApplication.main;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;
import com.javaduckspizza.vo.PizzaVo;

@Controller
@RequestMapping("/")
public class AppController {
	//needs to include sizes, toppings, crusts, cheeses, and sauces
	private ArrayList<PizzaVo> shoppingCart = new ArrayList<PizzaVo>();

	@GET
	@RequestMapping("/menu")
	public String getMenu(Model model) {
		System.err.println("In AppController.getMenu()");
		model.addAttribute("sizes", TypesCache.getSizes());
		model.addAttribute("crusts", TypesCache.getCrusts());
		model.addAttribute("cheeses", TypesCache.getCheeses());
		model.addAttribute("sauces", TypesCache.getSauces());
		model.addAttribute("toppings", TypesCache.getToppings());

		return "/menu.";  //I probably messed up something in the configuration.  If written without / and ., it tries to get WEB-INF/jspmenujsp
	}

	@RequestMapping("/cart")
	public String viewCart(Model model) {
		//navigate to cart and display contents of order
		model.addAttribute("shoppingCart", shoppingCart);

		return "/cart.";
	}

	@POST
	@RequestMapping("/addItem")
	//need to add parameter for toppings later
	public String addItem(Model model, @ModelAttribute("crust") long crust, @ModelAttribute("sauce") long sauce,
			@ModelAttribute("size") long size, @ModelAttribute("cheese") long cheese) {

		return "/menu.";
	}

	@POST
	@RequestMapping("/addorder")
	public String submitOrder(ModelMap modelMap) {
		//use OrdersServiceDao to add order, will likely need Pizza, PizzaToppingAssociation  as well
		return "/checkout.";
	}

	@RequestMapping("/cancel")
	public String cancelOrder(Model model) {
		shoppingCart = new ArrayList<PizzaVo>();
		
		return "/";
	}
}