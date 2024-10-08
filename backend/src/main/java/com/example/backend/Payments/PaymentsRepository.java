package com.example.backend.Payments;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.List;

@Repository
public interface PaymentsRepository extends MongoRepository<Payments, Integer> {
    @Query("{'$or':[{'paymentFrom.id' : ?0}, {'paymentTo.id': ?0}]}")
    List<Payments> findByMemberId(String id);
    @DeleteQuery("{'$or': [{'paymentFrom.id' : ?0}, {'paymentTo.id': ?0}], 'paid': ?1}")
    List<Payments> findByMemberIdAndStatus(String id, boolean paid);
    @Query("{'paymentFrom.id' : ?0}")
    List<Payments> findPaymentsFrom(String id);
    @Query("{'paymentTo.id' : ?0}")
    List<Payments> findPaymentsTo(String id);
    @Query("{'id' : ?0}")
    Payments findByIdentifier(String id);

    @DeleteQuery("{'id' : ?0}")
    Payments deleteByIdentifier(String id);

}
