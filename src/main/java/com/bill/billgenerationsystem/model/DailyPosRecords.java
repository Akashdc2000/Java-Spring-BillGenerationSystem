package com.bill.billgenerationsystem.model;

import com.bill.billgenerationsystem.entities.Orders;
import lombok.Data;

import java.util.List;

@Data
public class DailyPosRecords {
    private String posId;
    private Double totalAmount;
    private List<Orders> ordersList;
}
