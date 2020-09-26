package com.sample.orderprocessor.models;

import java.io.Serializable;

public class OrderStatus implements Serializable {
	
	private String orderRequestId;
	private String orderRequestStatus;
	
	public OrderStatus() {
		
	}
	
	public OrderStatus(String orderRequestId, String orderRequestStatus) {
		super();
		this.orderRequestId = orderRequestId;
		this.orderRequestStatus = orderRequestStatus;
	}
	public String getOrderRequestId() {
		return orderRequestId;
	}
	public void setOrderRequestId(String orderRequestId) {
		this.orderRequestId = orderRequestId;
	}
	public String getOrderRequestStatus() {
		return orderRequestStatus;
	}
	public void setOrderRequestStatus(String orderRequestStatus) {
		this.orderRequestStatus = orderRequestStatus;
	}

	@Override
	public String toString() {
		return "OrderStatus [orderRequestId=" + orderRequestId + ", orderRequestStatus=" + orderRequestStatus + "]";
	}
	
	

}
