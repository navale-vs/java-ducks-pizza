package com.javaduckspizza.OnlineOrderingApplication.main;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesVo;

@Controller
@RequestMapping("/")
public class AppController {
	//needs to include sizes, toppings, crusts, cheeses, and sauces
	private ArrayList<PizzaVo> shoppingCart = new ArrayList<PizzaVo>();

	@GET
	@RequestMapping("/menu")
	public String getMenu(Model model) {
		System.err.println("In AppController.getMenu()");
		addAttributesForMenu(model);

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
		System.err.println("Adding item");
		PizzaVo pizzaVo = new PizzaVo();
		pizzaVo.setCrust(crust);
		pizzaVo.setSauce(sauce);
		pizzaVo.setSize(size);
		pizzaVo.setCheese(cheese);
		shoppingCart.add(pizzaVo);
		model.addAttribute("shoppingCart", shoppingCart);
		addAttributesForMenu(model);

		return "/menu.";
	}

	@POST
	@RequestMapping("/removeItem")
	//need to add parameter for toppings later
	public String removeItem(Model model, @ModelAttribute("crust") long crust, @ModelAttribute("sauce") long sauce,
			@ModelAttribute("size") long size, @ModelAttribute("cheese") long cheese) {
		System.err.println("Removing item");
		
		for (PizzaVo pizzaVo : shoppingCart) {
			if((pizzaVo.getSize() == size) && (pizzaVo.getCrust() == crust) && (pizzaVo.getSauce() == sauce)) {
				shoppingCart.remove(pizzaVo);
			}
		}

		model.addAttribute("shoppingCart", shoppingCart);
		addAttributesForMenu(model);

		return "/cart.";
	}

	@POST
	@RequestMapping("/addorder")
	public String submitOrder(ModelMap modelMap) {
		//use OrdersServiceDao to add order, will likely need Pizza, PizzaToppingAssociation  as well
		return "/checkout.";
	}

	@RequestMapping("/cancel")
	public String cancelOrder(Model model) {
		System.err.println("Canceling order");
		shoppingCart = new ArrayList<PizzaVo>();
		model.addAttribute("shoppingCart", shoppingCart);

		return "redirect:/";
	}

	public void addAttributesForMenu(Model model) {
		model.addAttribute("sizes", TypesCache.getSizes());
		model.addAttribute("crusts", TypesCache.getCrusts());
		model.addAttribute("cheeses", TypesCache.getCheeses());
		model.addAttribute("sauces", TypesCache.getSauces());
		model.addAttribute("toppings", TypesCache.getToppings());
	}

	protected void buildPizzaList() {
		//size.id + " pizza " + " on " + crust.id + " crust with " + sauce.id + " sauce, " + cheese.id + " cheese.";
		for (PizzaVo pizzaVo : shoppingCart) {
//			String description = 
		}
	}

	protected String getTypeById(List<TypesVo> list, long id) {
		for (TypesVo typesVo : list) {
			if(typesVo.getId() == id) {
				return typesVo.getDescription();
			}
		}

		return "";
	}
}