package com.sample.orderprocessor.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
	
	private CustomerDetails customerDetails;
	private OrderDetails orderDetails;
	private ArrayList<LineItemDetails> lineItems;
	private OrderSummary orderSummary;
	private OrderStatus orderStatus;
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	public ArrayList<LineItemDetails> getLineItems() {
		return lineItems;
	}
	public void setLineItems(ArrayList<LineItemDetails> lineItems) {
		this.lineItems = lineItems;
	}
	public OrderSummary getOrderSummary() {
		return orderSummary;
	}
	public void setOrderSummary(OrderSummary orderSummary) {
		this.orderSummary = orderSummary;
	}
	@Override
	public String toString() {
		return "Order [customerDetails=" + customerDetails + ", orderDetails=" + orderDetails + ", lineItems="
				+ lineItems + ", orderSummary=" + orderSummary + ", orderStatus=" + orderStatus + "]";
	}
	
	
}
