package com.example.backend.Payments;

import com.example.backend.Member.Member;
import com.example.backend.Member.Membership;
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
        payment = new Payments(request.paymentFrom, request.paymentTo, request.value, request.limitDate, request.paymentDate);

        return repository.save(payment);
    }

    @PatchMapping("/pay/{id}")
    public Payments payPayment(@PathVariable String id){
        Payments existingPayment = repository.findByIdentifier(id);

        if(existingPayment!=null) {
            existingPayment.setPaid(true);
            existingPayment.setPaymentDate(LocalDate.now());
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
    @DeleteMapping("/delete/unpaid/{memberId}")
    public List<Payments> deleteUnpaidPayments(@PathVariable String memberId){
        return repository.findByMemberIdAndStatus(memberId, false);
    }

    @GetMapping("/{memberId}")
    public List<Payments> memberPayments(@PathVariable String memberId){
        return repository.findByMemberId(memberId);
    }

    @GetMapping("/from/{memberId}")
    public List<Payments> memberPaymentsFrom(@PathVariable String memberId){
        return repository.findPaymentsFrom(memberId);
    }

    @GetMapping("/to/{memberId}")
    public List<Payments> memberPaymentsTo(@PathVariable String memberId){
        return repository.findPaymentsTo(memberId);
    }

    @GetMapping("/get/{id}")
        public Payments getPayment(@PathVariable String id) {
        return repository.findByIdentifier(id);
    }

    @PutMapping("/update/{id}")
    public Payments update(@PathVariable String id, @RequestBody Payments request){
        Payments existingPayment = repository.findByIdentifier(id);

        if(existingPayment!=null){
            existingPayment.setPaymentFrom(request.paymentFrom);
            existingPayment.setPaymentTo(request.paymentTo);
            existingPayment.setPaid(request.paid);
            existingPayment.setValue(request.value);
            existingPayment.setLimitDate(request.limitDate);
            existingPayment.setPaymentDate(request.paymentDate);
            return repository.save(existingPayment);
        }else{
            return null;
        }
    }
}
