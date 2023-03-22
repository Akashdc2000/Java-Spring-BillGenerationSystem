package com.bill.billgenerationsystem.services;
import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.entities.POS;
import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.model.BillRequest;
import com.bill.billgenerationsystem.model.DailyPosRecords;

import java.text.ParseException;
import java.util.List;

public interface PosService {

    POS generateBill(BillRequest billRequest);
    List<Orders> getAllOrdersByPosId(String posId);

    DailyPosRecords getAllOrdersByPosIdAndDate(String id, String date) throws ParseException;

    UserInfo getCustomerById(String id);

}
