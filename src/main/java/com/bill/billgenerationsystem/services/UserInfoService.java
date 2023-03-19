package com.bill.billgenerationsystem.services;

import com.bill.billgenerationsystem.entities.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo addUser(UserInfo userInfo);
    String deleteUser(String id);
    String  updateUser(UserInfo userInfo,String id);

    List<UserInfo> getAllUsers();

    List<UserInfo> getAllCustomers();

    List<UserInfo> getAllPOS();

    UserInfo getUserByUserId(String id);


}
