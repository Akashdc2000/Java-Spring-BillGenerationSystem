package com.bill.billgenerationsystem.controller;

import com.bill.billgenerationsystem.entities.Orders;
import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.model.BillRequest;
import com.bill.billgenerationsystem.model.DailyPosRecords;
import com.bill.billgenerationsystem.services.PosService;
import com.bill.billgenerationsystem.services.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.bill.billgenerationsystem.entities.POS;

import java.util.List;

@RestController
@RequestMapping("/pos")
public class PosController {

    private final PosService posService;
    private final UserInfoService userInfoService;

    public PosController(PosService posService, UserInfoService userInfoService) {
        this.posService = posService;
        this.userInfoService = userInfoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<UserInfo> getAllPos(){
        return userInfoService.getAllPOS();
    }

    @PostMapping("/generate-bill")
    @PreAuthorize("hasAnyRole('ROLE_POS')")
    public POS generateBill(@RequestBody BillRequest billRequest){
        return posService.generateBill(billRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_POS')")
    public List<Orders> getAllOrdersByPosId(@PathVariable("id") String posId){
        return posService.getAllOrdersByPosId(posId);
    }

    @GetMapping("/id/{id}/date/{date}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_POS')")
    public DailyPosRecords getAllOrdersByIdAndDate(@PathVariable("id")String id, @PathVariable("date") String date){
        return posService.getAllOrdersByPosIdAndDate(id,date);
    }


    //Get Customer Info By Its ID

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasRole('ROLE_POS')")
    public UserInfo getCustomerById(@PathVariable String id){
        return posService.getCustomerById(id);
    }
}
