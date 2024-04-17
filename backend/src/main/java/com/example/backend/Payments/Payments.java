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
    public PaymentInfo paymentFrom;
    public PaymentInfo paymentTo;
    public double value;
    public boolean paid;
    public LocalDate limitDate;
    public LocalDate paymentDate;

    public Payments(PaymentInfo paymentFrom, PaymentInfo paymentTo, double value, LocalDate limitDate, LocalDate paymentDate){
        this.id = UUID.randomUUID().toString();
        this.paymentFrom = paymentFrom;
        this.paymentTo = paymentTo;
        this.value = value;
        this.paid = false;
        this.limitDate = limitDate;
        this.paymentDate = paymentDate;
    }
}
