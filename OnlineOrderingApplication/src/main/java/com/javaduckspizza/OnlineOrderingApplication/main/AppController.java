package com.javaduckspizza.OnlineOrderingApplication.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.PizzaVo;

@Controller
@RequestMapping("/")
public class AppController {
	private Map<PizzaVo, List<PizzaToppingAssociationVo>> mapShoppingCart =
			new HashMap<PizzaVo, List<PizzaToppingAssociationVo>>();

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
		model.addAttribute("cartForDisplay", prepareCartForDisplay());

		return "/cart.";
	}

	//@POST
	@RequestMapping("/addItem")
	public String addItem(Model model, @ModelAttribute("crust") long crust, @ModelAttribute("sauce") long sauce,
			@ModelAttribute("size") long size, @ModelAttribute("cheese") long cheese,
			@ModelAttribute("toppingsSelected") String selectedToppings) {
		System.err.println("Adding item");
		System.err.println("selectedToppings: " + selectedToppings);
		ObjectMapper mapper = new ObjectMapper();

		PizzaVo pizzaVo = new PizzaVo();
		pizzaVo.setCrust(crust);
		pizzaVo.setSauce(sauce);
		pizzaVo.setSize(size);
		pizzaVo.setCheese(cheese);

		try {
			List<PizzaToppingAssociationVo> lstPtav = mapper.readValue(selectedToppings,
					mapper.getTypeFactory().constructCollectionType(java.util.List.class, PizzaToppingAssociationVo.class));
			mapShoppingCart.put(pizzaVo, lstPtav);
			model.addAttribute("mapShoppingCart", mapShoppingCart);
			model.addAttribute("itemCount", mapShoppingCart.size());
			System.out.println("mapShoppingCart.size(): " + mapShoppingCart.size());
//			for (PizzaToppingAssociationVo ptav : lstPtav) {
//				System.err.println(ptav.toString());
//			}
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

//		addToCart();

		return getMenu(model); //I don't know if this is good design, but it works. 
	}

	@POST
	@RequestMapping("/removeItem")
	//need to add parameter for toppings later
	public String removeItem(Model model, @ModelAttribute("crust") long crust, @ModelAttribute("sauce") long sauce,
			@ModelAttribute("size") long size, @ModelAttribute("cheese") long cheese) {
		System.err.println("Removing item");
		
		for (PizzaVo pizzaVo : mapShoppingCart.keySet()) {
			if((pizzaVo.getSize() == size) && (pizzaVo.getCrust() == crust) && (pizzaVo.getSauce() == sauce)) {
				mapShoppingCart.remove(pizzaVo);
			}
		}

		model.addAttribute("cartForDisplay", prepareCartForDisplay());
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
		mapShoppingCart = new HashMap<PizzaVo, List<PizzaToppingAssociationVo>>();
		model.addAttribute("mapShoppingCart", mapShoppingCart);

		return "redirect:/";
	}

	public void addAttributesForMenu(Model model) {
		model.addAttribute("sizes", TypesCache.getActiveTypesByCategory("SIZE"));
		model.addAttribute("crusts", TypesCache.getActiveTypesByCategory("CRST"));
		model.addAttribute("cheeses", TypesCache.getActiveTypesByCategory("CHES"));
		model.addAttribute("sauces", TypesCache.getActiveTypesByCategory("SAUC"));
		model.addAttribute("toppings", TypesCache.getActiveTypesByCategory("TOPP"));
	}

	protected List<String> prepareCartForDisplay() {
		List<String> lstDescriptions = new ArrayList<String>();

		for (PizzaVo pizzaVo : mapShoppingCart.keySet()) {
			StringBuffer sbPizzaDescription = new StringBuffer();
			sbPizzaDescription.append(TypesCache.getById(pizzaVo.getSize()).getDescription());
			sbPizzaDescription.append(" ");
			sbPizzaDescription.append(TypesCache.getById(pizzaVo.getCrust()).getDescription());
			sbPizzaDescription.append(" crust, with ");
			sbPizzaDescription.append(TypesCache.getById(pizzaVo.getSauce()).getDescription());
			sbPizzaDescription.append(" sauce, ");
			sbPizzaDescription.append(TypesCache.getById(pizzaVo.getCheese()).getDescription());
			sbPizzaDescription.append(" cheese");

			List<PizzaToppingAssociationVo> lstToppings = mapShoppingCart.get(pizzaVo);

			if(!lstToppings.isEmpty()) {
				sbPizzaDescription.append(", and\n");

				for (PizzaToppingAssociationVo ptav : lstToppings) {
					sbPizzaDescription.append(TypesCache.getById(ptav.getToppingsId()).getDescription());
					sbPizzaDescription.append(", ");
				}
			}

			lstDescriptions.add(sbPizzaDescription.toString());
		}

		return lstDescriptions;
	}
}