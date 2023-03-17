package com.bill.billgenerationsystem.services;

import com.bill.billgenerationsystem.entities.Orders;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrdersService {

    List<Orders> getAllOrders();

    Orders addOrder(Orders orders);
    String getCustomerId(String email);
    String getPOSIdByName(String email);
    List<Orders> getAllOrdersByCustomerId(String customerId);

    List<Orders> getAllOrdersByDate(String date);

}
