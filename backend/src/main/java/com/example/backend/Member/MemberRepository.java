package com.example.backend.Member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member, Integer> {
    @Query("{'name' : ?0}")
    List<Member> findByName(String name);
    @Query("{'email' : ?0}")
    List<Member> findByEmail(String email);
    @Query("{'membership' : ?0}")
    List<Member> findByMembership(String email);
    @Query("{'id' : ?0}")
    Member findByIdentifier(String id);
}