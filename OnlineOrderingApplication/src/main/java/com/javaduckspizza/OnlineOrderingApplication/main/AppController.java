package com.javaduckspizza.OnlineOrderingApplication.main;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;
import com.javaduckspizza.service.dao.ModifierServiceDao;
import com.javaduckspizza.service.dao.OrderServiceDao;
import com.javaduckspizza.service.dao.PizzaServiceDao;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.PizzaVo;
import com.javaduckspizza.vo.TypesVo;

@Controller
@RequestMapping("/")
public class AppController {
	protected Map<PizzaVo, List<PizzaToppingAssociationVo>> mapShoppingCart =
			new HashMap<PizzaVo, List<PizzaToppingAssociationVo>>();

	@GET
	@RequestMapping("/menu")
	public String getMenu(Model model) {
		System.err.println("In AppController.getMenu()");
		addAttributesForMenu(model);
		model.addAttribute("itemCount", mapShoppingCart.size());

		return "/menu.";  //I probably messed up something in the configuration.  If written without / and ., it tries to get WEB-INF/jspmenujsp
	}

	@RequestMapping("/cart")
	public String viewCart(Model model) {
		//navigate to cart and display contents of order
		Map<PizzaVo, String> cartForDisplay = prepareCartForDisplay();
		model.addAttribute("cartForDisplay", cartForDisplay);
		model.addAttribute("total", calculateTotal(cartForDisplay.keySet()));

		return "/cart.";
	}

	//@POST
	@RequestMapping("/additem")
	public String addItem(Model model, @ModelAttribute("crust") long crust, @ModelAttribute("sauce") long sauce,
			@ModelAttribute("size") long size, @ModelAttribute("cheese") long cheese,
			@ModelAttribute("toppingsSelected") String selectedToppings) {
		System.err.println("Adding item");
		System.err.println("selectedToppings: " + selectedToppings);
		ObjectMapper mapper = new ObjectMapper();

		PizzaVo pizzaVo = new PizzaVo();
		pizzaVo.setId(System.currentTimeMillis()); //allows for multiple pizzas with same settings.  ID will not be propagated to DB on insert.
		pizzaVo.setCrust(crust);
		pizzaVo.setSauce(sauce);
		pizzaVo.setSize(size);
		pizzaVo.setCheese(cheese);
//		pizzaVo.setStatus(TypesCache.getBySequenceCode("PZST_0001").getId());

		try {
			List<PizzaToppingAssociationVo> lstPtav = mapper.readValue(selectedToppings,
					mapper.getTypeFactory().constructCollectionType(java.util.List.class, PizzaToppingAssociationVo.class));
			mapShoppingCart.put(pizzaVo, lstPtav);
			model.addAttribute("mapShoppingCart", mapShoppingCart);
			model.addAttribute("itemCount", mapShoppingCart.size());
			System.out.println("mapShoppingCart.size(): " + mapShoppingCart.size());
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		return getMenu(model); //I don't know if this is good design, but it works. 
	}

	@POST
	@RequestMapping("/removeItem")
	//need to add parameter for toppings later
	public String removeItem(Model model, @ModelAttribute("itemsToDelete") String itemsToDelete) {
		System.err.println("Removing item");
		System.err.println("itemsToDelete: " + itemsToDelete);
		removePizzas(itemsToDelete);

		Map<PizzaVo, String> cartForDisplay = prepareCartForDisplay();
		model.addAttribute("cartForDisplay", cartForDisplay);
		model.addAttribute("total", calculateTotal(cartForDisplay.keySet()));

		return "/cart.";
	}

	private void removePizzas(String itemsToDelete) {
		String [] array = itemsToDelete.split(",");
		for (int i = 0; i < array.length; i++) {
			for (PizzaVo pizzaVo : mapShoppingCart.keySet()) {
				if(Long.valueOf(array[i]).equals(pizzaVo.getId())) {
					mapShoppingCart.remove(pizzaVo);
				}
			}
		}
	}

	@POST
	@RequestMapping("/addorder")
	public String addOrder(Model model, @ModelAttribute("itemsToDelete") String itemsToDelete) {
		//Remove pizzas that should be deleted
		System.out.println("itemsToDelete: " + itemsToDelete);
		removePizzas(itemsToDelete);

		//use OrdersServiceDao to add order, will likely need Pizza, PizzaToppingAssociation  as well
		OrdersVo ordersVo = new OrdersVo();
		ordersVo.setDateTimePlaced(new Timestamp(System.currentTimeMillis()));
		ordersVo.setStatus(TypesCache.getBySequenceCode("ORST_0001").getId());
		ordersVo.setDateTimeDue(addToDate(ordersVo.getDateTimePlaced(), 30));
		ordersVo.setRetrievalMethod(TypesCache.getBySequenceCode("RTRV_0001").getId());
		ordersVo.setTotal(calculateTotal(mapShoppingCart.keySet()));

		//Add order
		OrderServiceDao osd = new OrderServiceDao();
		osd.addOrder(ordersVo);

		//Add Pizzas
		PizzaServiceDao psd = new PizzaServiceDao();
		Long pizzSubmittedStatus = TypesCache.getBySequenceCode("PZST_0001").getId();

		for (PizzaVo pizzaVo : mapShoppingCart.keySet()) {
			pizzaVo.setStatus(pizzSubmittedStatus);
			psd.addPizza(pizzaVo);

			for (PizzaToppingAssociationVo ptav : mapShoppingCart.get(pizzaVo)) {
				ptav.setPizzaId(pizzaVo.getId());
			}
		}
		
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

	protected Map<PizzaVo, String> prepareCartForDisplay() {
		Map<PizzaVo, String> mapDescriptionToPizza = new HashMap<PizzaVo, String>();

		for (PizzaVo pizzaVo : mapShoppingCart.keySet()) {
			StringBuffer sbPizzaDescription = generateDisplayStringForPizza(pizzaVo);
			pizzaVo.setPrice(calculatePrice(pizzaVo));
			mapDescriptionToPizza.put(pizzaVo, sbPizzaDescription.toString());
		}

		return mapDescriptionToPizza;
	}

	private StringBuffer generateDisplayStringForPizza(PizzaVo pizzaVo) {
		StringBuffer sbPizzaDescription = new StringBuffer();
		sbPizzaDescription.append(TypesCache.getById(pizzaVo.getSize()).getDescription());
		sbPizzaDescription.append(" ");
		sbPizzaDescription.append(TypesCache.getById(pizzaVo.getCrust()).getDescription());
		sbPizzaDescription.append(" crust with ");
		sbPizzaDescription.append(TypesCache.getById(pizzaVo.getSauce()).getDescription());
		sbPizzaDescription.append(" sauce, ");
		sbPizzaDescription.append(TypesCache.getById(pizzaVo.getCheese()).getDescription());
		sbPizzaDescription.append(" cheese");

		List<PizzaToppingAssociationVo> lstToppings = mapShoppingCart.get(pizzaVo);

		if(!lstToppings.isEmpty()) {
			sbPizzaDescription.append(", and");

			for (int i = 0; i < lstToppings.size(); i++) {
				if((i % 5) == 0) {
					sbPizzaDescription.append("<br />");
				}

				PizzaToppingAssociationVo ptav = lstToppings.get(i);
				sbPizzaDescription.append(TypesCache.getById(ptav.getToppingsId()).getDescription());

				if(ptav.getLeft() != ptav.getRight()) {
					sbPizzaDescription.append(((ptav.getLeft()) ? " (Left Side Only)" : " (Right Side Only)" ));
				}

				if(i < (lstToppings.size() - 1)) {
					sbPizzaDescription.append(", ");
				}
			}
		}

		return sbPizzaDescription;
	}

	public BigDecimal calculatePrice(PizzaVo pizzaVo) {
		//need size and toppings
		//get appropriate records from Modifiers
		//do mafs
		ModifierVo mvBasePrice = ModifierServiceDao.getCurrentByType(TypesCache.getBySequenceCode("PRCE_0001").getId());
		ModifierVo mvTopping = ModifierServiceDao.getCurrentByType(TypesCache.getBySequenceCode("PRCE_0002").getId());
		ModifierVo mvHalfTopping = ModifierServiceDao.getCurrentByType(TypesCache.getBySequenceCode("PRCE_0007").getId());
		ModifierVo mvSize = ModifierServiceDao.getCurrentByType(pizzaVo.getSize());

		BigDecimal bdPrice = mvBasePrice.getValue().multiply(mvSize.getValue());
		BigDecimal bdToppingsTotal = BigDecimal.ZERO; //mvTopping.getValue().multiply(BigDecimal.valueOf(mapShoppingCart.get(pizzaVo).size()));

		for (PizzaToppingAssociationVo ptav : mapShoppingCart.get(pizzaVo)) {
			if(ptav.getLeft() != ptav.getRight()) { //topping only on one side
				bdToppingsTotal = bdToppingsTotal.add(mvTopping.getValue().multiply(mvHalfTopping.getValue()));
			} else {
				bdToppingsTotal = bdToppingsTotal.add(mvTopping.getValue());
			}
		}

		return bdPrice.add(bdToppingsTotal).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal calculateTotal(Set<PizzaVo> pizzas) {
		BigDecimal bdTotal = BigDecimal.ZERO;

		for (PizzaVo pizzaVo : pizzas) {
			bdTotal = bdTotal.add(pizzaVo.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		return bdTotal;
	}

	private Timestamp addToDate(Date dateOrdered, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOrdered);
		cal.add(Calendar.MINUTE, minutes);
		return new Timestamp(cal.getTime().getTime());
	}
}