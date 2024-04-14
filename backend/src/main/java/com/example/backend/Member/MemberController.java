package com.example.backend.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberRepository repository;

    @GetMapping("/all")
    public List<Member> listMembers(){
        return repository.findAll();
    }

    @GetMapping("/search")
    public List<Member> searchMembers(@RequestParam String type, @RequestParam  String value){
        return switch (type) {
            case "id" -> repository.findMemberByIdentifier(value);
            case "name" -> repository.findByName(value);
            case "email" -> repository.findByEmail(value);
            case "membership" -> repository.findByMembership(value);
            default -> repository.findAll();
        };
    }

   @PostMapping("/create")
    public Member create(@RequestBody Member request) throws URISyntaxException, IOException, InterruptedException {
        Member member = new Member(request.getName(), request.getMembership(), request.getPassword(), request.getEmail(), request.getBalance());
        createPaymentsYear(member);
        return repository.save(member);
    }

    public void createPaymentsYear(Member member) throws URISyntaxException, IOException, InterruptedException {
        // Criar o cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        String requestBody;
        LocalDate now = LocalDate.now();

        for(int i = 1; i <= 12; i++){
            if(member.getMembership() != Membership.VIP){
                requestBody = String.format("{\"paymentFrom\": \"%s\", \"paymentTo\": \"%s\", \"value\": %.2f, \"date\": \"%s\"}", "CLUB", member.getId(), member.getBalance(), now);
            }else{
                requestBody = String.format("{\"paymentFrom\": \"%s\", \"paymentTo\": \"%s\", \"value\": %.2f, \"date\": \"%s\"}", member.getId(), "CLUB", member.getBalance(), now);
            }

            // Construir a solicitação POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/payments/create"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

            now = now.plusMonths(1);
        }

    }
    @DeleteMapping("/delete/{id}")
    public Member delete(@PathVariable String id){
        Member existingMember = repository.findByIdentifier(id);
        if(existingMember!=null){
           return repository.deleteByIdentifier(id);
        } else {
            return null;
        }
    }

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam String email, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();
        List<Member> existingMember = repository.findByEmail(email);
        if (!existingMember.isEmpty() && Objects.equals(existingMember.get(0).getPassword(), password)) {
            result.put("exists", true);
            result.put("member", existingMember.get(0));
        } else {
            result.put("exists", false);
            result.put("member", null);
        }
        return result;
    }

    @PatchMapping("/update/{id}")
    public Member update(@PathVariable String id, @RequestBody Member request, @RequestParam Boolean gotBalance){
        Member existingMember = repository.findByIdentifier(id);

        if(existingMember!=null){
            if (request.getName() != null) {
                existingMember.setName(request.getName());
            }
            if (request.getMembership() != null) {
                existingMember.setMembership(request.getMembership());
            }
            if (request.getPassword() != null) {
                existingMember.setPassword(request.getPassword());
            }
            if (request.getEmail() != null) {
                existingMember.setEmail(request.getEmail());
            }
            if (gotBalance) {
                existingMember.setBalance(request.getBalance());
            }
            return repository.save(existingMember);
        }else{
            return null;
        }
    }
}
