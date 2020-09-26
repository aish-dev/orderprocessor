package com.sample.orderprocessor.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.orderprocessor.Services.OrderDetailsProducerService;
import com.sample.orderprocessor.Services.OrderDetailsService;
import com.sample.orderprocessor.models.Order;
import com.sample.orderprocessor.models.OrderStatus;
import com.sample.orderprocessor.utils.Response;

@RestController
@RequestMapping("orders")
public class OrderController {
	
	private final static Logger logger = LogManager.getLogger(OrderDetailsProducerService.class);
	
	@Autowired
	private OrderDetailsProducerService orderDetailsProducer;
	
	@Autowired
	private OrderDetailsService orderDetailsService;
	

	@PostMapping("publish/order")
	public Response submitOrder(@RequestBody Order order) {
		logger.info("Submitting order details with Order Request ID: " + order.getOrderStatus().getOrderRequestId());
		return orderDetailsProducer.produceOrder(order);
	}
	
	@GetMapping("get/order/status/{orderRequestId}")
	public OrderStatus getOrderStatus(@PathVariable int orderRequestId) {
		logger.info("Obtaining status for the order with Order Request ID: "+ orderRequestId);
		OrderStatus orderStatus = orderDetailsService.getOrderStatus(Integer.toString(orderRequestId));
		return orderStatus;
			
	}
	
	@GetMapping("fetchorder/{orderRequestId}")
	public Order getOrderDetails(@PathVariable int orderRequestId) {
		logger.info("Obtaining order details for the order with Order Request ID: "+ orderRequestId);
		Order order = orderDetailsService.getOrderDetails(Integer.toString(orderRequestId));
		return order;
	}
	
	@PatchMapping("order/update/status")
	public void updateOrderStatus(@RequestBody OrderStatus orderStatus) {
		logger.info("Updating the order status for the Order Request ID: "+orderStatus.getOrderRequestId());
		Order order = orderDetailsService.getOrderDetails(orderStatus.getOrderRequestId());
		if(ObjectUtils.isEmpty(order) != true) {
			order.setOrderStatus(orderStatus);
			orderDetailsProducer.produceOrder(order);
		}
	}
	 
}
