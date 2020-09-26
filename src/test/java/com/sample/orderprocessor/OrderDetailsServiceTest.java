package com.sample.orderprocessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.sample.orderprocessor.Services.OrderDetailsService;
import com.sample.orderprocessor.config.KafkaTopicConfig;
import com.sample.orderprocessor.models.Order;
import com.sample.orderprocessor.models.OrderDetails;
import com.sample.orderprocessor.models.OrderStatus;
import com.sample.orderprocessor.repository.OrderDetailsRepo;
import com.sample.orderprocessor.utils.Response;

@SpringBootTest
public class OrderDetailsServiceTest {

	@MockBean
	KafkaTopicConfig kafkaTopicCfg;
	
	@InjectMocks
	private OrderDetailsService orderDetailsService;
	
	@Mock
	OrderDetailsRepo orderDetailsRepo;
	
	@Mock
	Response response;
	
	@Test
	public void saveOrderSuccess() {
		
		//Arrange
		OrderStatus mockOrderStatus = new OrderStatus("134","delivered");
		OrderDetails mockOrderDetails = new OrderDetails();
		mockOrderDetails.setOrderDate("20-Sep-2020");
		mockOrderDetails.setOrderId(1279);
		mockOrderDetails.setOrderTotal("1375");
		Order mockOrder = new Order();
		mockOrder.setOrderStatus(mockOrderStatus);
		mockOrder.setOrderDetails(mockOrderDetails);
		Mockito.when(response.getStatus()).thenReturn("success");
		Mockito.when(response.getMessage()).thenReturn("Saved Order details Successfully");
		Mockito.doNothing().when(orderDetailsRepo).save(mockOrder);
		
		//Act
		Response responseExpected = orderDetailsService.saveOrder(mockOrder);
		
		//Assert
		assertEquals(responseExpected.getStatus(),"success");
		assertEquals(responseExpected.getMessage(),"Saved Order details Successfully");
		
	}
	
	@Test
	public void saveOrderFailure() {
		
		//Arrange
		OrderStatus mockOrderStatus = new OrderStatus("134","delivered");
		OrderDetails mockOrderDetails = new OrderDetails();
		mockOrderDetails.setOrderDate("20-Sep-2020");
		mockOrderDetails.setOrderId(1279);
		mockOrderDetails.setOrderTotal("1375");
		Order mockOrder = new Order();
		mockOrder.setOrderStatus(mockOrderStatus);
		mockOrder.setOrderDetails(mockOrderDetails);
		Mockito.when(response.getStatus()).thenReturn("failure");
		Mockito.when(response.getMessage()).thenReturn("Failed to save order details");
		Mockito.doThrow(IllegalStateException.class).when(orderDetailsRepo).save(mockOrder);
		
		//Act
		Response responseExpected = orderDetailsService.saveOrder(mockOrder);
		
		//Assert
		assertEquals(responseExpected.getStatus(),"failure");
		assertEquals(responseExpected.getMessage(),"Failed to save order details");
		
	}
	
	@Test
	public void getOrderDetailsSuccess() {
		
		//Arrange
		String orderRequestId = "134";
		OrderStatus mockOrderStatus = new OrderStatus("134","delivered");
		OrderDetails mockOrderDetails = new OrderDetails();
		mockOrderDetails.setOrderDate("20-Sep-2020");
		mockOrderDetails.setOrderId(1279);
		mockOrderDetails.setOrderTotal("1375");
		Order mockOrder = new Order();
		mockOrder.setOrderStatus(mockOrderStatus);
		mockOrder.setOrderDetails(mockOrderDetails);
		Mockito.when(orderDetailsRepo.findById(Mockito.anyString())).thenReturn(mockOrder);
		
		
		//Act
		Order expected = orderDetailsService.getOrderDetails(orderRequestId);
		
		//Assert
		assertThat(expected).isNotNull();
		assertEquals(expected.getOrderStatus().getOrderRequestId(), "134");
		assertEquals(expected.getOrderStatus().getOrderRequestStatus(), "delivered");
		assertEquals(expected.getOrderDetails().getOrderDate(),"20-Sep-2020");
		assertEquals(expected.getOrderDetails().getOrderId(),1279);
		assertEquals(expected.getOrderDetails().getOrderTotal(),"1375");
		
	}
	
	@Test
	public void getOrderDetailsFailed() {
		
		//Arrange
		String orderRequestId = "135";
		Mockito.when(orderDetailsRepo.findById(Mockito.anyString())).thenReturn(null);
		
		
		//Act
		Order expected = orderDetailsService.getOrderDetails(orderRequestId);
		
		//Assert
		assertThat(expected).isNull();
		
	}
	
	@Test
	public void getOrderStatusFailed() {
		
		//Arrange
		String orderRequestId = "135";
		Mockito.when(orderDetailsRepo.findById(Mockito.anyString())).thenReturn(null);
		
		
		//Act
		OrderStatus expected = orderDetailsService.getOrderStatus(orderRequestId);
		
		//Assert
		assertThat(expected).isNull();
		
	}
	
	@Test
	public void getOrderStatusSuccess() {
		
		//Arrange
		String orderRequestId = "134";
		OrderStatus mockOrderStatus = new OrderStatus("134","delivered");
		Order mockOrder = new Order();
		mockOrder.setOrderStatus(mockOrderStatus);
		Mockito.when(orderDetailsRepo.findById(Mockito.anyString())).thenReturn(mockOrder);
		
		
		//Act
		OrderStatus expected = orderDetailsService.getOrderStatus(orderRequestId);
		
		//Assert
		assertThat(expected).isNotNull();
		assertEquals(expected.getOrderRequestId(), "134");
		assertEquals(expected.getOrderRequestStatus(), "delivered");
		
	}
	
}
