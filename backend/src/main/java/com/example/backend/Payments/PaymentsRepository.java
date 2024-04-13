package com.example.backend.Payments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends MongoRepository<Payments, Integer> {
    @Query("{'memberId' : ?0}")
    List<Payments> findByMemberId(String id);

}
