package com.sample.orderprocessor.models;

import java.io.Serializable;

public class OrderSummary implements Serializable {
	
	private String itemsTotalPrice;
	private String packingCost;
	private String totalBeforeTax;
	private String tax;
	private String total;
	
	public String getItemsTotalPrice() {
		return itemsTotalPrice;
	}
	public void setItemsTotalPrice(String itemsTotalPrice) {
		this.itemsTotalPrice = itemsTotalPrice;
	}
	public String getPackingCost() {
		return packingCost;
	}
	public void setPackingCost(String packingCost) {
		this.packingCost = packingCost;
	}
	public String getTotalBeforeTax() {
		return totalBeforeTax;
	}
	public void setTotalBeforeTax(String totalBeforeTax) {
		this.totalBeforeTax = totalBeforeTax;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}


}
