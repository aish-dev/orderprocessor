# orderprocessing API to process the order details and to get order status.

Sample JSON for order details, line items and order summary and status.

{
    "customerDetails" : {
    "customerId" : 1396,
    "customerName" : "Jack",
    "customerContact" : "5673433692"
  },
  
  "orderDetails" : {
    "orderId" : 1246396,
    "orderDate" : "21-Sep-2020",
    "orderTotal" : "1453",
    "paymentInfo" : {
      "paymentMethod" : "NetBanking",
      "billingAddress" : "59 West 46th Street, New York City, NY 10036"
    },
    "shippingAddress" : "59 West 46th Street, New York City, NY 10036"
  },
  
  "lineItems" : [
    { 
      "itemNo" : 1,
      "itemId" : "156",
      "itemName" : "FGD Handwash",
      "itemPrice" : "400"
      
    },
    { 
      "itemNo" : 2,
      "itemId" : "137",
      "itemName" : "SRT Shampoo",
      "itemPrice" : "400"
      
    }
    ],
   
  "orderSummary" : {
    "itemsTotalPrice" : "800.00",
    "packingCost" : "0.00",
    "totalBeforeTax" : "800.00",
    "tax" : "75",
    "total" : "875"
  },
  
  "orderStatus" : {
      "orderRequestId" : "111",
      "orderRequestStatus" : "submitted"
  }
  
}
