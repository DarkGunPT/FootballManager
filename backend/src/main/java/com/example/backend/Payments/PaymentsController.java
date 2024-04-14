package com.example.backend.Payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
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
        Payments payment;
        if(request.date!=null){
            payment = new Payments(request.paymentFrom, request.paymentTo, request.value, request.date);
        }else{
            payment = new Payments(request.paymentFrom, request.paymentTo, request.value, LocalDate.now());
        }
        return repository.save(payment);
    }

    @PatchMapping("/pay/{id}")
    public Payments payPayment(@PathVariable String id){
        Payments existingPayment = repository.findByIdentifier(id);

        if(existingPayment!=null) {
            existingPayment.setPaid(true);
            return repository.save(existingPayment);
        } else {
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public Payments deletePayment(@PathVariable String id){
        Payments existingPayment = repository.findByIdentifier(id);
        if(existingPayment!=null){
            return repository.deleteByIdentifier(id);
        } else {
            return null;
        }
    }

    @GetMapping("/{memberId}")
    public List<Payments> memberPayments(@PathVariable String memberId){
        return repository.findByMemberId(memberId);
    }
}
