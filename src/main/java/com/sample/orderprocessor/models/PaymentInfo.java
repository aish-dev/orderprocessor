package com.sample.orderprocessor.models;

import java.io.Serializable;

public class PaymentInfo implements Serializable{
	
	private String paymentMethod;
	private String billingAddress;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

}
