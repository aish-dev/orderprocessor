package com.sample.orderprocessor.repository;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.sample.orderprocessor.models.Order;

@Repository
public class OrderDetailsRepo {
	
	  @Autowired 
	  private RedisTemplate<String, Order> redisTemplate;
	  
	  @Value("${db.key}")
	  private String dbKey;
	   
	  public void save(Order order) { 
			  redisTemplate.opsForHash().put(dbKey, order.getOrderStatus().getOrderRequestId(),order);
	  }

	  public Order findById(String orderRequestId) {
		  Order order = (Order) redisTemplate.opsForHash().get(dbKey, orderRequestId); 
		  return order;
	  }
	  
	  public void update(Order order) {
		  save(order);
	  }
}
