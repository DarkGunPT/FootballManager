package com.example.backend.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Member> searchByName(@RequestParam String type, @RequestParam  String value){
        return switch (type) {
            case "name" -> repository.findByName(value);
            case "email" -> repository.findByEmail(value);
            case "membership" -> repository.findByMembership(value);
            default -> repository.findAll();
        };
    }
   @PostMapping("/create")
    public Member create(@RequestBody Member request){
        Member member = new Member(request.getName(), request.getMembership(), request.getPassword(), request.getEmail(), request.getBalance());
        return repository.save(member);
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
