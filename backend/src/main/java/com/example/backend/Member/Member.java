package com.example.backend.Member;


import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import java.util.UUID;

@Data
@Document(collection = "Members")
public class Member {
    @Id
    public String id;
    public String name;
    public Membership membership;
    public String password;
    public String email;
    public double balance; // Represents the net balance of income and expenses
    public LocalDate admissionDate;
    public Member(String name, Membership membership, String password, String email, double balance){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.email = email;
        this.membership = membership;
        this.balance = balance;
        this.admissionDate = LocalDate.now();
    }
}