package com.example.backend.Payments;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection = "Payments")
public class Payments {
    @Id
    public String id;
    public String paymentFrom;
    public String paymentTo;
    public double value;
    public boolean paid;
    public LocalDate date;

    public Payments(String paymentFrom, String paymentTo, double value, LocalDate date){
        this.id = UUID.randomUUID().toString();
        this.paymentFrom = paymentFrom;
        this.paymentTo = paymentTo;
        this.value = value;
        this.paid = false;
        this.date = date;
    }
}
