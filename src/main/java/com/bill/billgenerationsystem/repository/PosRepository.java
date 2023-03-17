package com.bill.billgenerationsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bill.billgenerationsystem.entities.POS;
import org.springframework.stereotype.Repository;

@Repository
public interface PosRepository extends MongoRepository<POS,String> {
}
