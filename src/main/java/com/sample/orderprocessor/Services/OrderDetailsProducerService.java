package com.sample.orderprocessor.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.sample.orderprocessor.models.Order;
import com.sample.orderprocessor.utils.Response;

@Service
public class OrderDetailsProducerService {
	
	@Autowired
	Response response;
	
	private final static Logger logger = LogManager.getLogger(OrderDetailsProducerService.class); 
	
	@Value("${spring.kafka.template.default-topic}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, Order> sendDetailskafkaTemplate;

	public Response produceOrder(Order order) {
		
		ListenableFuture<SendResult<String, Order>> future = sendDetailskafkaTemplate.send(topic,order);
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {

		    @Override
		    public void onSuccess(SendResult<String, Order> result) {
		    	logger.info("Successfully sent order details with order ID: " +order.getOrderStatus().getOrderRequestId()+" to kafka");
		    	response.setStatus("success");
		    	response.setMessage("Successfully submitted order details");

		    }

		    @Override
		    public void onFailure(Throwable ex) {
		    	logger.error("Error while sending order details with order ID: " +order.getOrderStatus().getOrderRequestId()+" to kafka");
		    	response.setMessage("Failed to submit order details");
		    	response.setStatus("failure");
		    	
		    }

		});
		
		return response;
	
	}
	
}
