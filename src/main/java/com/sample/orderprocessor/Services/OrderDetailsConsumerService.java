package com.sample.orderprocessor.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sample.orderprocessor.models.Order;
import com.sample.orderprocessor.utils.Response;

@Service
public class OrderDetailsConsumerService {
	
	private static final Logger logger = LogManager.getLogger(OrderDetailsConsumerService.class);
	
	@Autowired
	private OrderDetailsService OrderDetailsService;
	
	@KafkaListener(topics = "${spring.kafka.template.default-topic}", 
			groupId = "${spring.kafka.consumer.group-id}"
			,containerFactory = "orderKafkaListenerContainerFactory")
    public Response consume(Order order){
		
		logger.info("Saving order details with Order Request ID: "+
    			order.getOrderStatus().getOrderRequestId()+" to Database");
    	
    	return OrderDetailsService.saveOrder(order);	
        
    }
	
}
