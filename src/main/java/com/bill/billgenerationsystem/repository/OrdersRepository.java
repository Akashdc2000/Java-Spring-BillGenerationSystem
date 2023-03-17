package com.bill.billgenerationsystem.repository;

import com.bill.billgenerationsystem.entities.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends MongoRepository<Orders,String> {
    Optional<List<Orders>> findAllByCustomerId(String customerId);

    Optional<List<Orders>> findAllByPosId(String posId);

    List<Orders> findAllByCreatedDate(String date);

    Optional<List<Orders>> findAllByPosIdAndCreatedDate(String id, String date);
}
