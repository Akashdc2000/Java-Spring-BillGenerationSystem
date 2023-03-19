package com.bill.billgenerationsystem.repository;

import com.bill.billgenerationsystem.entities.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo,String> {
    Optional<UserInfo> findByUserName(String username);

//    Optional<UserInfo> findByRoles(String roleAdmin);

    Optional<List<UserInfo>> findAllByRoles(String roleAdmin);

    Optional<UserInfo> findByEmail(String email);

    UserInfo findByUserIdAndRoles(String id,String role);
}
