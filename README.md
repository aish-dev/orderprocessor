# orderprocessing API to process the order details and to get order status.

Sample JSON for order details, line items and order summary and status.


```
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
      "billingAddress" : "No. 8 Bazaar Lane , Mylapore,Chennai - 600 004"
    },
    "shippingAddress" : "No. 8 Bazaar Lane , Mylapore,Chennai - 600 004"
  },
  
  "lineItems" : 
  [
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
      "itemPrice" : "500"
      
    }
    ],
   
  "orderSummary" : {
    "itemsTotalPrice" : "900.00",
    "packingCost" : "0.00",
    "totalBeforeTax" : "900.00",
    "tax" : "75",
    "total" : "975"
  },
  
  "orderStatus" : {
      "orderRequestId" : "134",
      "orderRequestStatus" : "submitted"
  }
  
}
```
