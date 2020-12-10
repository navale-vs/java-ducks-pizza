package com.javaduckspizza.OnlineOrderingApplication.main;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.servlet.http.HttpServletResponse;
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
import com.javaduckspizza.service.OnlineOrderService;
import com.javaduckspizza.vo.CustomerVo;
import com.javaduckspizza.vo.ModifierVo;
import com.javaduckspizza.vo.OrdersVo;
import com.javaduckspizza.vo.PizzaToppingAssociationVo;
import com.javaduckspizza.vo.PizzaVo;

@Controller
@RequestMapping("/")
public class AppController {
	protected Map<PizzaVo, List<PizzaToppingAssociationVo>> mapShoppingCart =
			new HashMap<PizzaVo, List<PizzaToppingAssociationVo>>();
	protected Map<PizzaVo, List<PizzaToppingAssociationVo>> mapOrderHistory =
			new HashMap<PizzaVo, List<PizzaToppingAssociationVo>>();
	protected OnlineOrderService onlineOrderService = new OnlineOrderService();

	public AppController() {
		initCache();
	}

	protected void initCache() {
		TypesCache.getActiveTypesByCategory(onlineOrderService, "SIZE");
		TypesCache.getActiveTypesByCategory(onlineOrderService, "CRST");
		TypesCache.getActiveTypesByCategory(onlineOrderService, "SAUC");
		TypesCache.getActiveTypesByCategory(onlineOrderService, "CHES");
		TypesCache.getActiveTypesByCategory(onlineOrderService, "TOPP");
		TypesCache.getActiveTypesByCategory(onlineOrderService, "RTRV");
	}

	@GET
	@RequestMapping("/menu")
	public String getMenu(Model model, HttpServletResponse response) {
		System.err.println("In AppController.getMenu()");
		addAttributesForMenu(model);
		model.addAttribute("itemCount", mapShoppingCart.size());

		return "/menu.";  //I probably messed up something in the configuration.  If written without / and ., it tries to get WEB-INF/jspmenujsp
	}

	@GET
	@RequestMapping("/orderstatus")
	public String orderStatus(Model model) {
		return "/orderstatus.";
	}

	@GET
	@RequestMapping("/lookupOrder")
	public String lookupOrder(Model model, @ModelAttribute("byOrderNumber") String orderNumber, HttpServletResponse response) {
		System.err.println("Looking up order: " + orderNumber);
		OrdersVo ordersVo = new OrdersVo();
		mapOrderHistory.clear();

		if ((orderNumber != null) && !orderNumber.isEmpty()) {
			Long orderId = Long.valueOf(orderNumber);
			ordersVo = onlineOrderService.getOrderById(orderId);

			if(ordersVo == null) {
				model.addAttribute("noOrder", "No order found for " + orderNumber);
				return "/orderstatus.";
			}

			List<PizzaVo> lstPizzasForOrder = onlineOrderService.getPizzaByOrderId(orderId, response);
			System.err.println(lstPizzasForOrder.size() + " pizzas for order " + orderId);

			for (PizzaVo pizzaVo : lstPizzasForOrder) {
				System.err.println("Toppings for pizzaId: " + pizzaVo.getId());
				List<PizzaToppingAssociationVo> lstPtav = onlineOrderService.getToppingsByPizza(pizzaVo.getId());
				System.err.println("Toppings count for pizzaId: " + lstPtav.size());
				mapOrderHistory.put(pizzaVo, lstPtav);
			}
		}

		Map<PizzaVo, String> cartForDisplay = prepareCartForDisplay(mapOrderHistory);
		model.addAttribute("cartForDisplay", cartForDisplay);
		model.addAttribute("total", calculateTotal(cartForDisplay.keySet()));
		model.addAttribute("orderStatus", TypesCache.getById(onlineOrderService, ordersVo.getStatus()).getDescription());

		return "/orderstatus.";
	}

	@RequestMapping("/history")
	public String showHistory() {
		return "redirect:/stillCooking";
	}

	@RequestMapping("/sidebar")
	public String showSidebar(Model model) {
		model.addAttribute("itemCount", mapShoppingCart.size());
		System.err.println("showing sidebar when mapShoppingCart.size(): " + mapShoppingCart.size());
		return "/sidebar.";
	}
	

	@RequestMapping("/stillCooking")
	public String stillCooking() {
		return "/stillCooking.";
	}

	@RequestMapping("/cart")
	public String viewCart(Model model) {
		//navigate to cart and display contents of order
		Map<PizzaVo, String> cartForDisplay = prepareCartForDisplay(mapShoppingCart);
		model.addAttribute("cartForDisplay", cartForDisplay);
		model.addAttribute("total", calculateTotal(cartForDisplay.keySet()));
		model.addAttribute("retrievalMethod", TypesCache.getActiveTypesByCategory(onlineOrderService, "RTRV"));

		return "/cart.";
	}

	@POST
	@RequestMapping("/addItem")
	public String addItem(Model model, @ModelAttribute("crust") long crust, @ModelAttribute("sauce") long sauce,
			@ModelAttribute("size") long size, @ModelAttribute("cheese") long cheese,
			@ModelAttribute("toppingsSelected") String selectedToppings) {
		System.err.println("Adding item");
		System.err.println("selectedToppings: " + selectedToppings);

		PizzaVo pizzaVo = new PizzaVo();
		pizzaVo.setId(System.currentTimeMillis()); //allows for multiple pizzas with same settings.  This ID will not be propagated to DB on insert.
		pizzaVo.setCrust(crust);
		pizzaVo.setSauce(sauce);
		pizzaVo.setSize(size);
		pizzaVo.setCheese(cheese);

		try {
			ObjectMapper mapper = new ObjectMapper();
			List<PizzaToppingAssociationVo> lstPtav = mapper.readValue(selectedToppings,
					mapper.getTypeFactory().constructCollectionType(java.util.List.class, PizzaToppingAssociationVo.class));
			mapShoppingCart.put(pizzaVo, lstPtav);
			model.addAttribute("itemCount", mapShoppingCart.size());
			System.out.println("mapShoppingCart.size(): " + mapShoppingCart.size());
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		return "redirect:/menu"; 
	}

	@POST
	@RequestMapping("/removeItem")
	public String removeItem(Model model, @ModelAttribute("itemsToDelete") String itemsToDelete) {
		System.err.println("Removing item");
		System.err.println("itemsToDelete: " + itemsToDelete);
		removePizzasFromOrder(itemsToDelete);

		Map<PizzaVo, String> cartForDisplay = prepareCartForDisplay(mapShoppingCart);
		model.addAttribute("cartForDisplay", cartForDisplay);
		model.addAttribute("total", calculateTotal(cartForDisplay.keySet()));

		return "/cart.";
	}

	private void removePizzasFromOrder(String itemsToDelete) {
		if((itemsToDelete.length() > 0) && !itemsToDelete.matches("\\s")) {
			List<Long> lstPizzasToDelete = Arrays.stream(itemsToDelete.split(",")).
					mapToLong(n -> Long.valueOf(n)).boxed().collect(Collectors.toList());
			
			mapShoppingCart = mapShoppingCart.entrySet().stream().
					filter(entry -> !lstPizzasToDelete.contains(entry.getKey().getId())).
				collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
		}
	}

	@POST
	@RequestMapping("/addCustomer")
	//need to add parameter for toppings later
	public String addCustomer(Model model, @ModelAttribute("firstName_newCustomer") String firstName,
			@ModelAttribute("lastName_newCustomer") String lastName, @ModelAttribute("address1_newCustomer") String address1,
			@ModelAttribute("address2_newCustomer") String address2, @ModelAttribute("city_newCustomer") String city,
			/*@ModelAttribute("state_newCustomer") int state,*/ @ModelAttribute("zip_newCustomer") String zipcode,
			@ModelAttribute("phone_newCustomer") String phone, @ModelAttribute("email_newCustomer") String email,
			@ModelAttribute("password_newCustomer") String password, HttpServletResponse response) {
		System.err.println("Enter customer information");

//		CustomerServiceDao csd = new CustomerServiceDao();
		CustomerVo customerVo = onlineOrderService.getCustomerByEmail(email, response);

		if(customerVo == null) {
			model.addAttribute("email", email);

			customerVo = new CustomerVo();
			customerVo.setFirstName(firstName);
			customerVo.setLastName(lastName);
			customerVo.setStreetAddress1(address1);
			customerVo.setStreetAddress2(address2);
			customerVo.setCity(city);
			customerVo.setState(TypesCache.getBySequenceCode(onlineOrderService, "STAT_0015").getId());
			customerVo.setZipcode5(zipcode);
//			customerVo.setZipcode4(zipcode4);
			customerVo.setPhone(phone.replaceAll("\\D", ""));
			customerVo.setEmail(email);
	
//			customerVo.setId(csd.addCustomer(customerVo));
			customerVo.setId(onlineOrderService.addCustomer(firstName, "", lastName, address1, address2, city, zipcode, "0000",
					phone, TypesCache.getBySequenceCode(onlineOrderService, "STAT_0015").getId(), email, response));
		}

		/**
		 * LoginVo lv = new LoginVo();
		 * lv.setCustomerId(customerVo.getId());
		 * lv.setPassword(password);
		 */

		Map<PizzaVo, String> cartForDisplay = prepareCartForDisplay(mapShoppingCart);
		model.addAttribute("cartForDisplay", cartForDisplay);
		model.addAttribute("total", calculateTotal(cartForDisplay.keySet()));
		Long orderId = addOrder(customerVo.getId(), response);
		model.addAttribute("orderId", orderId);

		return ((orderId > 0L) ? "/checkout." : "/addCustomer.");
	}

	public Long addOrder(Long customerId, HttpServletResponse response) {
		//Remove pizzas that should be deleted
//		System.err.println("itemsToDelete: " + itemsToDelete);
//		removePizzasFromOrder(itemsToDelete);
		//Add order, will likely need Pizza, PizzaToppingAssociation  as well
		OrdersVo ordersVo = saveOrder(customerId, response);
		savePizza(ordersVo.getId(), response);
		System.err.println("order added, going to checkout");

		return ordersVo.getId();
	}

	private OrdersVo saveOrder(Long customerId, HttpServletResponse response) {
		OrdersVo ordersVo = new OrdersVo();
		ordersVo.setCustomerId(customerId);
		ordersVo.setDateTimePlaced(new Timestamp(System.currentTimeMillis()));
		ordersVo.setStatus(TypesCache.getBySequenceCode(onlineOrderService, "ORST_0001").getId());
		ordersVo.setDateTimeDue(addToDate(ordersVo.getDateTimePlaced(), 30));
		ordersVo.setRetrievalMethod(TypesCache.getBySequenceCode(onlineOrderService, "RTRV_0002").getId());
		ordersVo.setTotal(calculateTotal(mapShoppingCart.keySet()));

		System.err.println("adding orders record: " + ordersVo);
		//Add order
		long orderId = onlineOrderService.addOrder(customerId, TypesCache.getBySequenceCode(onlineOrderService, "RTRV_0001").getId(),
				ordersVo.getTotal(), ordersVo.getStatus(), ordersVo.getDateTimePlaced(), ordersVo.getDateTimeDue(),
				ordersVo.getDateTimeReady(), ordersVo.getDateTimeCompleted(), response);
		ordersVo.setId(orderId);

		return ordersVo;
	}

	private void savePizza(Long ordersId, HttpServletResponse httpResponse) {
		//Add Pizzas
		Long pizzaSubmittedStatus = TypesCache.getBySequenceCode(onlineOrderService, "PZST_0001").getId();

		for (PizzaVo pizzaVo : mapShoppingCart.keySet()) {
			pizzaVo.setStatus(pizzaSubmittedStatus);
			pizzaVo.setOrderId(ordersId);
			System.err.println("adding pizza record: " + pizzaVo);

			pizzaVo.setId(onlineOrderService.addPizza(pizzaVo.getCheese(), pizzaVo.getCrust(), ordersId, pizzaVo.getPrice(), pizzaVo.getSauce(),
					pizzaVo.getSize(), pizzaVo.getStatus(), mapShoppingCart.get(pizzaVo), httpResponse));
		}
	}

	@RequestMapping("/cancel")
	public String cancelOrder(Model model) {
		System.err.println("Canceling order");
		mapShoppingCart = new HashMap<PizzaVo, List<PizzaToppingAssociationVo>>();
		model.addAttribute("mapShoppingCart", mapShoppingCart);

		return "redirect:/";
	}

	public void addAttributesForMenu(Model model) {
		model.addAttribute("sizes", TypesCache.getActiveTypesByCategory(onlineOrderService, "SIZE"));
		model.addAttribute("crusts", TypesCache.getActiveTypesByCategory(onlineOrderService, "CRST"));
		model.addAttribute("cheeses", TypesCache.getActiveTypesByCategory(onlineOrderService, "CHES"));
		model.addAttribute("sauces", TypesCache.getActiveTypesByCategory(onlineOrderService, "SAUC"));
		model.addAttribute("toppings", TypesCache.getActiveTypesByCategory(onlineOrderService, "TOPP"));
	}

	protected Map<PizzaVo, String> prepareCartForDisplay(Map<PizzaVo, List<PizzaToppingAssociationVo>> mapOrder) {
		Map<PizzaVo, String> mapDescriptionToPizza = new HashMap<PizzaVo, String>();

		for (PizzaVo pizzaVo : mapOrder.keySet()) {
			StringBuffer sbPizzaDescription = generateDisplayStringForPizza(pizzaVo, mapOrder.get(pizzaVo));

			if((pizzaVo.getPrice() == null) || BigDecimal.ZERO.equals(pizzaVo.getPrice())) {
				pizzaVo.setPrice(calculatePrice(pizzaVo));
			}

			mapDescriptionToPizza.put(pizzaVo, sbPizzaDescription.toString());
		}

		return mapDescriptionToPizza;
	}

	private StringBuffer generateDisplayStringForPizza(PizzaVo pizzaVo, List<PizzaToppingAssociationVo> lstToppings) {
		StringBuffer sbPizzaDescription = new StringBuffer();
		sbPizzaDescription.append(TypesCache.getById(onlineOrderService, pizzaVo.getSize()).getDescription());
		sbPizzaDescription.append(" ");
		sbPizzaDescription.append(TypesCache.getById(onlineOrderService, pizzaVo.getCrust()).getDescription());
		sbPizzaDescription.append(" crust with ");
		sbPizzaDescription.append(TypesCache.getById(onlineOrderService, pizzaVo.getSauce()).getDescription());
		sbPizzaDescription.append(" sauce, ");
		sbPizzaDescription.append(TypesCache.getById(onlineOrderService, pizzaVo.getCheese()).getDescription());
		sbPizzaDescription.append(" cheese");

		if(!lstToppings.isEmpty()) {
			sbPizzaDescription.append(", and");

			for (int i = 0; i < lstToppings.size(); i++) {
				if((i % 5) == 0) {
					sbPizzaDescription.append("<br />");
				}

				PizzaToppingAssociationVo ptav = lstToppings.get(i);
				sbPizzaDescription.append(TypesCache.getById(onlineOrderService, ptav.getToppingsId()).getDescription());

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

	@RequestMapping("/lookupCustomer")
	public String lookupCustomer(Model model, @ModelAttribute("email_returningCustomer") String email,
			@ModelAttribute("password_returningCustomer") String password, HttpServletResponse response) {
//		 CustomerServiceDao csd = new CustomerServiceDao();
		 CustomerVo cv = onlineOrderService.getCustomerByEmail(email, response);

		 if(cv != null) {
			 model.addAttribute("customerId", cv.getId());
			 model.addAttribute("firstName", cv.getFirstName());
		 } else {
			 model.addAttribute("No customer found for " + email);
			 return "";
		 }

		/**
		 * if(lsv != null) {
		 * 		LoginServiceDao lsd = new LoginServiceDao();
		 * 		LoginServiceVo lsv = lsd.getLogin(, password);
		 * } else {
		 * 		model.addAttribute("notFound", "Could not find user with that email/password. Please try again.");
		 * 		return "/customer.";
		 * }
		 */
		return "/checkout.";
	}

	@RequestMapping("/loginCustomer")
	public String loginCustomer(Model model, @ModelAttribute("itemsToDelete") String itemsToDelete) {
		removePizzasFromOrder(itemsToDelete);

		if(mapShoppingCart.size() == 0) {
			return "redirect:/cart";
		}

		model.addAttribute("IOWA", TypesCache.getBySequenceCode(onlineOrderService, "STAT_0015"));
		return "/customer.";
	}

	public BigDecimal calculatePrice(PizzaVo pizzaVo) {
		//need size and toppings
		//get appropriate records from Modifiers
		//do mafs
		ModifierVo mvBasePrice =
				onlineOrderService.getCurrentModifierByType(TypesCache.getBySequenceCode(onlineOrderService, "PRCE_0001").getId());
		ModifierVo mvTopping =
				onlineOrderService.getCurrentModifierByType(TypesCache.getBySequenceCode(onlineOrderService, "PRCE_0002").getId());
		ModifierVo mvHalfTopping =
				onlineOrderService.getCurrentModifierByType(TypesCache.getBySequenceCode(onlineOrderService, "PRCE_0007").getId());
		ModifierVo mvSize =
				onlineOrderService.getCurrentModifierByType(pizzaVo.getSize());

		BigDecimal bdPrice = mvBasePrice.getValue().multiply(mvSize.getValue());
		BigDecimal bdToppingsTotal = BigDecimal.ZERO; //mvTopping.getValue().multiply(BigDecimal.valueOf(mapShoppingCart.get(pizzaVo).size()));
		List<PizzaToppingAssociationVo> lstToppings = mapShoppingCart.get(pizzaVo);

		if(lstToppings != null) {
			for (PizzaToppingAssociationVo ptav : lstToppings) {
				if(ptav.getLeft() != ptav.getRight()) { //topping only on one side
					bdToppingsTotal = bdToppingsTotal.add(mvTopping.getValue().multiply(mvHalfTopping.getValue()));
				} else {
					bdToppingsTotal = bdToppingsTotal.add(mvTopping.getValue());
				}
			}
		}

		return bdPrice.add(bdToppingsTotal).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal calculateTotal(Set<PizzaVo> pizzas) {
		BigDecimal bdTotal = BigDecimal.ZERO;

		//I want to believe that this can be replaced with stream.reduce(), but haven't figured it out yet
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