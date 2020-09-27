# orderprocessing API 
    
Functionalities:
- Pushes the initial order details to message queue and creates a record in redis dB.
- Order Status corresponding to an order is updated and pushed to the queue.
- A consumer processes the order message (order status update) in the queue and simultaneously updates the status to redis DB.
- GET APis helps to fetch the order status corresponding to an order request ID (or the tracking ID).

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

|   HTTP Verb   |      URL                                                       |   Description                                                                 |
| ------------- | ---------------------------------------------------------------|-------------------------------------------------------------------------------|
|     `GET`     |`http://localhost:8082/orders/get/order/status/{orderRequestId}`| Obtains order status corresponding to the provided order request ID.          |
|     `GET`     |   `http://localhost:8082/orders/fetchorder/{orderRequestId}`   | Obtains all the order details corresponding to the provided order request ID. |
|    `POST`     |        `http://localhost:8082/orders/publish/order`            | Publishes the orderdetails to kafka and stores the details in Redis DB.       |
|   `PATCH`     |     ` http://localhost:8082/orders/order/update/status`        | Update the order status in Kafka                                              | 

