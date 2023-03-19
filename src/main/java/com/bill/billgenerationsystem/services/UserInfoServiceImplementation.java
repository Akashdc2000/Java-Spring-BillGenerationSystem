package com.bill.billgenerationsystem.services;

import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.repository.UserInfoRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserInfoServiceImplementation implements UserInfoService{

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;


    public UserInfoServiceImplementation(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfo addUser(UserInfo userInfo) {

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }

    @Override
    public String deleteUser(String id) {
        userInfoRepository.deleteById(id);
        return "Deleted Successfully...";
    }

    @Override
    public String  updateUser(UserInfo userInfo, String id) {

        Optional<UserInfo> existingUserInfo =userInfoRepository.findById(id);
        if(existingUserInfo.isPresent())
        {
            existingUserInfo.get().setUserName(userInfo.getUserName());
            existingUserInfo.get().setEmail(userInfo.getEmail());
            existingUserInfo.get().setRoles(userInfo.getRoles());
            existingUserInfo.get().setPassword(passwordEncoder.encode(userInfo.getPassword()));
            userInfoRepository.save(existingUserInfo.get());
            return "Updated Successfully...";
        }
        return "Something Bad Happen...";

    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    @Override
    public List<UserInfo> getAllCustomers() {

        Optional<List<UserInfo>> userInfo=userInfoRepository.findAllByRoles("ROLE_CUSTOMER");
        return  userInfo.orElse(null);
    }

    @Override
    public List<UserInfo> getAllPOS() {
        Optional<List<UserInfo>> userInfo=userInfoRepository.findAllByRoles("ROLE_POS");
        return  userInfo.orElse(null);
    }

    @Override
    public UserInfo getUserByUserId(String id) {
        return userInfoRepository.findById(id).get();
    }
}
