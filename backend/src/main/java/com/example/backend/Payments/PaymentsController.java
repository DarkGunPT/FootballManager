package com.example.backend.Payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    @Autowired
    PaymentsRepository repository;

    @GetMapping("/all")
    public List<Payments> listPayments(){
        return repository.findAll();
    }

    @PostMapping("/create")
    public Payments createPayment(@RequestBody Payments request){
        Payments payment = new Payments(request.paymentFrom, request.paymentTo, request.value, request.date);
        return repository.save(payment);
    }

    @GetMapping("/{id}")
    public List<Payments> ammountToPay(@PathVariable String memberId){
        return repository.findByMemberId(memberId);
    }
}
