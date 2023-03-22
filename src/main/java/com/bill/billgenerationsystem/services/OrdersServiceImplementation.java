package com.bill.billgenerationsystem.services;

import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.model.Earning;
import com.bill.billgenerationsystem.model.Items;
import com.bill.billgenerationsystem.repository.OrdersRepository;
import com.bill.billgenerationsystem.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<Orders> getAllOrdersByDate(String dateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date startDate = (Date) formatter.parse(dateString);

        Date endDate = (Date) formatter.parse(dateString);
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);

        return ordersRepository.findAllByCreatedDateBetween(startDate,endDate);
    }

    @Override
    public Earning getTotalEarning(String date) throws ParseException {

        double total=0.0D;
        List<Orders> ordersList=getAllOrdersByDate(date);
        for(Orders order: ordersList){
            for (Items item:order.getItems()){
                total+=item.getPrice();
            }
        }
        Earning earning=new Earning();
        earning.setDate(date);
        earning.setTotalEarning(total);
        return earning;
    }
}
