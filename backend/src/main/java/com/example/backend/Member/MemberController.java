package com.example.backend.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/players")
public class MemberController {
    @Autowired
    MemberRepository repository;
    @GetMapping("/all")
    public List<Member> listMembers(){
        return repository.findAll();
    }

    @PostMapping("/create")
    public Member create(){
        Member member = new Member("Francisco", Membership.ADMIN, "teste", "nuno@gmail.com", 500);
        return repository.save(member);
    }
}
