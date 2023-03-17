package com.bill.billgenerationsystem.controller;

import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.services.OrdersService;
import com.bill.billgenerationsystem.services.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final UserInfoService userInfoService;
    private final OrdersService ordersService;

    public CustomerController(UserInfoService userInfoService, OrdersService ordersService) {
        this.userInfoService = userInfoService;
        this.ordersService = ordersService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfo> getAllCustomers(){
        return userInfoService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public List<Orders> getAllOrdersByCustomerId(@PathVariable("id") String customerId){
        return ordersService.getAllOrdersByCustomerId(customerId);
    }
}
