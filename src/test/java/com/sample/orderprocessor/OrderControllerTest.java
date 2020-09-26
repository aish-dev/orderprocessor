package com.sample.orderprocessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sample.orderprocessor.Services.OrderDetailsProducerService;
import com.sample.orderprocessor.Services.OrderDetailsService;
import com.sample.orderprocessor.config.KafkaTopicConfig;
import com.sample.orderprocessor.models.Order;
import com.sample.orderprocessor.models.OrderDetails;
import com.sample.orderprocessor.models.OrderStatus;
import com.sample.orderprocessor.utils.Response;

import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OrderDetailsProducerService orderDetailsProducer;
	
	@MockBean
	KafkaTopicConfig kafkaTopicCfg;
	
	@Mock
	private Response response;
	
	@MockBean
	private OrderDetailsService orderDetailsDervice;

	@Test
	public void getOrderStatusForValidRequestId() throws Exception {
		
		//Arrange
		OrderStatus mockOrderStatus = new OrderStatus("124","submitted");
		
		Mockito.when(orderDetailsDervice.getOrderStatus(Mockito.anyString())).thenReturn(mockOrderStatus);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/get/order/status/124")
				.accept(MediaType.APPLICATION_JSON);
		
		//Act and Assert
		mockMvc.perform(requestBuilder).andDo(print())
			.andExpect(jsonPath("$.orderRequestId", is("124")))
			.andExpect(jsonPath("$.orderRequestStatus", is("submitted")));
	}
	
	@Test
	public void getOrderStatusForInValidRequestId() throws Exception {
		
		//Arrange
		OrderStatus mockOrderStatus = null;
		
		Mockito.when(orderDetailsDervice.getOrderStatus(Mockito.anyString())).thenReturn(mockOrderStatus);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/get/order/status/xx")
				.accept(MediaType.APPLICATION_JSON);
		
		//Act 
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//Assert
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
	}
	
	@Test
	public void getOrderDetailsForValidRequestId() throws Exception {
		
		//Arrange
		OrderStatus mockOrderStatus = new OrderStatus("124","delivered");
		OrderDetails mockOrderDetails = new OrderDetails();
		mockOrderDetails.setOrderDate("20-Sep-2020");
		mockOrderDetails.setOrderId(1279);
		mockOrderDetails.setOrderTotal("1375");
		Order mockOrder = new Order();
		mockOrder.setOrderStatus(mockOrderStatus);
		mockOrder.setOrderDetails(mockOrderDetails);
		Mockito.when(orderDetailsDervice.getOrderDetails(Mockito.anyString())).thenReturn(mockOrder);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/fetchorder/124")
				.accept(MediaType.APPLICATION_JSON);
		
		//Act and Assert
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.orderDetails.orderDate", is("20-Sep-2020")))
		.andExpect(jsonPath("$.orderDetails.orderId", is(1279)))
		.andExpect(jsonPath("$.orderDetails.orderTotal", is("1375")))
		.andExpect(jsonPath("$.orderStatus.orderRequestId", is("124")))
		.andExpect(jsonPath("$.orderStatus.orderRequestStatus", is("delivered")));
	}
	
	@Test
	public void getOrderDetailsForinValidRequestId() throws Exception {
		
		//Arrange
		Order mockOrder = null;
		Mockito.when(orderDetailsDervice.getOrderDetails(Mockito.anyString())).thenReturn(mockOrder);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/fetchorder/xx")
				.accept(MediaType.APPLICATION_JSON);
		
		//Act and Assert
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
	
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
	}
	
	@Test
	public void postOrderDetailsWithSuccess() throws Exception {
		
		//Arrange 
		response.setMessage("successfully submitted");
		response.setStatus("success");
		OrderStatus mockOrderStatus = new OrderStatus("134","delivered");
		OrderDetails mockOrderDetails = new OrderDetails();
		mockOrderDetails.setOrderDate("20-Sep-2020");
		mockOrderDetails.setOrderId(1279);
		mockOrderDetails.setOrderTotal("1375");
		Order mockOrder = new Order();
		mockOrder.setOrderStatus(mockOrderStatus);
		mockOrder.setOrderDetails(mockOrderDetails);
		Mockito.when(orderDetailsProducer.produceOrder(mockOrder)).thenReturn(response);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/publish/order")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"customerDetails\":{\"customerId\":1396,\"customerName\":\"Jack\",\"customerContact\":\"5673433692\"},\"orderDetails\":{\"orderId\":1246396,\"orderDate\":\"21-Sep-2020\",\"orderTotal\":\"1453\",\"paymentInfo\":{\"paymentMethod\":\"NetBanking\",\"billingAddress\":\"93-C, tyuuu Street, NeyYork\"},\"shippingAddress\":\"93-C, tyuuu Street, NeyYork\"},\"lineItems\":[{\"itemNo\":1,\"itemId\":\"156\",\"itemName\":\"FGD Handwash\",\"itemPrice\":\"400\"},{\"itemNo\":2,\"itemId\":\"137\",\"itemName\":\"XXX Shampoo\",\"itemPrice\":\"400\"}],\"orderSummary\":{\"itemsTotalPrice\":\"800.00\",\"packingCost\":\"0.00\",\"totalBeforeTax\":\"800.00\",\"tax\":\"75\",\"total\":\"875\"},\"orderStatus\":{\"orderRequestId\":\"109\",\"orderRequestStatus\":\"submitted\"}}");
		
		//Act and Assert
		mockMvc.perform(requestBuilder)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
}
