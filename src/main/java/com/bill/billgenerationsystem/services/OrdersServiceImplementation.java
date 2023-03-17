package com.bill.billgenerationsystem.services;

import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.repository.OrdersRepository;
import com.bill.billgenerationsystem.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImplementation implements OrdersService{

    private final OrdersRepository ordersRepository;
    private final UserInfoRepository userInfoRepository;

    public OrdersServiceImplementation(OrdersRepository ordersRepository, UserInfoRepository userInfoRepository) {
        this.ordersRepository = ordersRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders addOrder(Orders orders) {
        return ordersRepository.save(orders);
    }

    public String getCustomerId(String email){
        Optional<UserInfo> userInfo=userInfoRepository.findByEmail(email);
        if(userInfo.isPresent())
            return userInfo.get().getUserId();
        return "No User Found";
    }

    @Override
    public String getPOSIdByName(String email) {
        Optional<UserInfo> userInfo=userInfoRepository.findByEmail(email);
        if(userInfo.isPresent())
            return userInfo.get().getUserId();
        return "No POS Found";
    }

    @Override
    public List<Orders> getAllOrdersByCustomerId(String customerId) {

        Optional<List<Orders>> orders=ordersRepository.findAllByCustomerId(customerId);
        return orders.orElse(null);
    }

    @Override
    public List<Orders> getAllOrdersByDate(String date) {
        return ordersRepository.findAllByCreatedDate(date);
    }
}
