package com.example.backend.Member;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public LocalDateTime admissionDate;
    public Member(String name, Membership membership, String password, String email, double balance){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.email = email;
        this.membership = membership;
        this.balance = balance;
        this.admissionDate = LocalDateTime.now();
       // createPaymentsYear(paymentsController);
    }

    /*public void createPaymentsYear(PaymentsController paymentsController){
        LocalDate now = LocalDate.now();
        for(int i = 1; i <= 12; i++){
            if(membership != Membership.VIP){
                paymentsController.createPayment(id,balance,now);
            }else{
                paymentsController.createPayment("CLUBE",balance,now);
            }
            now = now.plusMonths(1);
        }
    }*/

}
