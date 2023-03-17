package com.bill.billgenerationsystem.controller;

import com.bill.billgenerationsystem.entities.UserInfo;
import com.bill.billgenerationsystem.security.JwtAuthRequest;
import com.bill.billgenerationsystem.security.JwtAuthResponse;
import com.bill.billgenerationsystem.security.JwtTokenHelper;
import com.bill.billgenerationsystem.services.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;
    public UserInfoController(UserDetailsService userDetailsService, JwtTokenHelper jwtTokenHelper, AuthenticationManager authenticationManager, UserInfoService userInfoService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.authenticationManager = authenticationManager;
        this.userInfoService = userInfoService;
    }

    @PostMapping
    public UserInfo addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable String id) {
        return userInfoService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String  updateUser(@RequestBody UserInfo userInfo,@PathVariable String id) {
        return userInfoService.updateUser(userInfo,id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUsers();
    }





    //Token Generation
    @PostMapping("/login")
    public JwtAuthResponse createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
        this.authenticate(jwtAuthRequest.getUserName(),jwtAuthRequest.getPassword());
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUserName());
        String token=this.jwtTokenHelper.generateToken(userDetails);
        System.out.println("Token ="+token);

        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setToken(token);
        return jwtAuthResponse;
    }

    private void authenticate(String userName, String password) {

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userName,password);
        System.out.println("UserName:"+userName+"Password:"+password);
        this.authenticationManager.authenticate(authenticationToken);
    }
}
