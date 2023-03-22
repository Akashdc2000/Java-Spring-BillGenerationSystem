package com.bill.billgenerationsystem.services;

import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.entities.POS;
import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.model.BillRequest;
import com.bill.billgenerationsystem.model.DailyPosRecords;
import com.bill.billgenerationsystem.model.Items;
import com.bill.billgenerationsystem.repository.OrdersRepository;
import com.bill.billgenerationsystem.repository.PosRepository;
import com.bill.billgenerationsystem.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PosServiceImplementation implements PosService{

    private final PosRepository posRepository;
    private final OrdersRepository ordersRepository;
    private final UserInfoRepository userInfoRepository;
    private final OrdersService ordersService;

    private final UserInfoService userInfoService;
    public PosServiceImplementation(PosRepository posRepository, OrdersRepository ordersRepository, UserInfoRepository userInfoRepository, OrdersService ordersService, UserInfoService userInfoService) {
        this.posRepository = posRepository;
        this.ordersRepository = ordersRepository;
        this.userInfoRepository = userInfoRepository;
        this.ordersService = ordersService;
        this.userInfoService = userInfoService;
    }

    @Override
    public POS generateBill(BillRequest billRequest) {
        Orders orders=new Orders();

        orders.setItems(billRequest.getItems());
        orders.setTotalCost(calculateTotalCost(orders.getItems()));

        String customerId=ordersService.getCustomerId(billRequest.getCustomerEmail());
        if(customerId.equals("No User Found")){

            UserInfo newUser=addCustomerEntryToDatabase(billRequest);
            orders.setCustomerId(newUser.getUserId());
        }
        else
            orders.setCustomerId(customerId);
        if(ordersService.getPOSIdByName(billRequest.getPosEmail()).equals("No POS Found")){
            System.out.println("Please Select Proper POS");
            throw new UnsupportedOperationException("POS Not Properly Given...");
        }else
            orders.setPosId(ordersService.getPOSIdByName(billRequest.getPosEmail()));

        orders.setCreatedDate(new Date());
        ordersRepository.save(orders);


        POS pos=new POS();

        pos.setTotalCost(orders.getTotalCost());
        pos.setOrderedId(orders.getId());
        pos.setPosId(orders.getPosId());
        pos.setCustomerId(orders.getCustomerId());
        pos.setCreatedDate(new Date());
        posRepository.save(pos);
        return pos;
    }


    @Override
    public List<Orders> getAllOrdersByPosId(String posId) {

        Optional<List<Orders>> orders=ordersRepository.findAllByPosId(posId);
        return orders.orElse(null);
    }

    @Override
    public DailyPosRecords getAllOrdersByPosIdAndDate(String id, String dateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = (Date) formatter.parse(dateString);

        Date endDate = (Date) formatter.parse(dateString);
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);

        Optional<List<Orders>> orders=ordersRepository.findAllByPosIdAndCreatedDateBetween(id,startDate,endDate);

        DailyPosRecords records=new DailyPosRecords();
        records.setPosId(id);
        records.setOrdersList(orders.get());

        double total=0.0D;
        for (Orders order:orders.get()) {
            total+=order.getTotalCost();
        }
        records.setTotalAmount(total);

        return records;
    }

    @Override
    public UserInfo getCustomerById(String id) {
        return userInfoRepository.findByUserIdAndRoles(id,"ROLE_CUSTOMER");
    }

    ///Helper Functions....
//    public String currentDate(){
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        return LocalDate.now().format(formatter);
//    }


    public float calculateTotalCost(List<Items> items){
        float total=0.0F;
        for (Items item: items) {
            total+=item.getPrice();
        }
        return total;
    }

    public UserInfo addCustomerEntryToDatabase(BillRequest billRequest)
    {
        UserInfo userInfo=new UserInfo();
        String email=billRequest.getCustomerEmail();
        userInfo.setUserName(email);
        userInfo.setPassword(email);
        userInfo.setEmail(email);
        userInfo.setRoles("ROLE_CUSTOMER");

        return userInfoService.addUser(userInfo);
    }

}
