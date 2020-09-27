package com.sample.orderprocessor.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.orderprocessor.models.Order;
import com.sample.orderprocessor.models.OrderStatus;
import com.sample.orderprocessor.repository.OrderDetailsRepo;
import com.sample.orderprocessor.utils.Response;

@Service
public class OrderDetailsService {
	
	private static final Logger logger = LogManager.getLogger(OrderDetailsService.class);
	
	@Autowired
	private Response response;
	
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	
	public Response saveOrder(Order order) {
		try {
			orderDetailsRepo.save(order);
			logger.info("Saving order details with Order Request ID "+ 
					order.getOrderStatus().getOrderRequestId()+ " success");
			response.setMessage("Saved Order details Successfully");
			response.setStatus("success");
			return response;
		}
		catch (Exception e) {
			logger.error("Saving order details with Order Request ID "+ 
					order.getOrderStatus().getOrderRequestId()+ " failed");
			response.setMessage("Failed to save order details");
			response.setStatus("failure");
			return response;
		}
		
	}

	public Order getOrderDetails(String orderRequestId) {
		try {
			return orderDetailsRepo.findById(orderRequestId);
			
		}
		catch (Exception e) {
			logger.error("Fetching order details with Order Request ID "+ 
					orderRequestId+ "failed - error: "+
					e.getMessage());
			return null;
		}
	}
	
	public OrderStatus getOrderStatus(String orderRequestId) {
		try {
			return orderDetailsRepo.findById(orderRequestId).getOrderStatus();
		}
		catch (Exception e) {
			logger.error("Fetching order status with Order Request ID "+ 
					orderRequestId+ "failed - error: "+
					e.getMessage());
			return null;
		}
	}
}
