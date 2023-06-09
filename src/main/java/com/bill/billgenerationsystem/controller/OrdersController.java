package com.bill.billgenerationsystem.controller;

import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.model.DailyPosRecords;
import com.bill.billgenerationsystem.model.Earning;
import com.bill.billgenerationsystem.model.Items;
import com.bill.billgenerationsystem.services.OrdersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Orders> getAllOrders(){
        return ordersService.getAllOrders();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Orders addOrder(@RequestBody Orders orders){
        return ordersService.addOrder(orders);
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_POS')")
    public List<Orders> getAllOrdersByDate(@PathVariable("date") String date) throws ParseException {
        return ordersService.getAllOrdersByDate(date);
    }

    @GetMapping("/earning/{date}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Earning getTotalEarning(@PathVariable("date") String date) throws ParseException {
        return ordersService.getTotalEarning(date);
    }


}
