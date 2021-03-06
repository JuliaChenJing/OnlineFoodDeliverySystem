package com.grp11.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.grp11.Consumer.IConsumerService;

import com.grp11.Domain.UserDomain;

import com.grp11.products.IProductService;
import com.grp11.products.ProductDomain;
@Controller
@RequestMapping("/orders")
public class OrderCtrl {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IConsumerService consumerService;
	@Autowired
	private IProductService productService;
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getAllOrdersFromAllUser(Model model) {
		model.addAttribute("allOrders", orderService.getAllOrder());
		return "home2";
	}
	
	@RequestMapping(value = {"/undelivered"}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getAllUndeliveredOrdersFromAllUser(Model model) {
		model.addAttribute("allOrders", orderService.getAllUndeliveredOrder());
		return "undeliveredOrders";
	}
	
	@RequestMapping(value = {"/{UserId}/{productId}/new", "/{UserId}/{productId}/new/"}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String startOrdering(Model model, @PathVariable("productId") long productId, @PathVariable("UserId") long UserId) {
		model.addAttribute("userId", UserId);
		model.addAttribute("product", productService.getProduct(productId));
		return "addOrder";
	}
	
	@RequestMapping(value = "/{UserId}/{ProductId}/new", method = RequestMethod.POST)
	public String addConsumer(OrderDomain order, @PathVariable("UserId") long UserId, @PathVariable("ProductId") long ProductId, int quantity) {
		UserDomain c = consumerService.getUser(UserId);
		ProductDomain p = productService.getProduct(ProductId);
		int finalprice = quantity * p.getUnitPrice();
		order.setPrice(finalprice);
		order.setProduct(p);
		order.setConsumer(c);
		orderService.createOrder(order);
		return "redirect:/products/";
	}
	
	@RequestMapping(value = "/{UserId}/{ProductId}/{OrderId}", method = RequestMethod.POST)
	public String updateOrder(OrderDomain order, @PathVariable("UserId") long UserId, @PathVariable("OrderId") long OrderId, int quantity, boolean isDelivered) {
		UserDomain c = consumerService.getUser(UserId);
		OrderDomain p = orderService.getOrder(OrderId);
		int finalprice = quantity * p.getProduct().getUnitPrice();
		p.setPrice(finalprice);
		p.setConsumer(c);
		p.setId(OrderId);
		p.setDelivered(isDelivered);
		orderService.updateOrder(p);
		return "redirect:/products/";
	}
	
	@RequestMapping(value = "/{UserId}/{ProductId}/{OrderId}", method = RequestMethod.GET)
	public String getOrder(@PathVariable("UserId") long UserId, @PathVariable("OrderId") long OrderId, Model model) {
		model.addAttribute("order", orderService.getOrder(OrderId));
		return "updateOrder";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public String deleteOrder(@RequestBody OrderDomain order) {
		orderService.deleteOrder(order.getId());
		return "redirect:/home2";
	}
	
	@RequestMapping(value = "/{UserId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getAllOrdersForSpecificUser(@PathVariable("UserId") long UserId, Model model) {
		List<OrderDomain> listOrder = orderService.getAllOrderForUser(UserId);
		model.addAttribute("allOrders", listOrder);
		int total = 0;
		for(OrderDomain o : listOrder) {
			total += o.getPrice();
		}
		model.addAttribute("total", total);
		return "listAllOrdersForUser";
	}

}