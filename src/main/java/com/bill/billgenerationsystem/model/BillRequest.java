package com.bill.billgenerationsystem.model;

import lombok.Data;

import java.util.List;

@Data
public class BillRequest {
    private String customerEmail;
    private String posEmail;
    private List<Items> items;
}
