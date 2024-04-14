package com.example.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;

@Controller
@CrossOrigin
public class FrontendController {
    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("name", "coming for the controller");
        return "login";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("name", "coming for the controller");
        return "register";
    }

    @GetMapping("homepage")
    public String homepage(Model model, @RequestParam(name = "member", required = false) String memberJson) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Member member = objectMapper.readValue(memberJson, Member.class);
                model.addAttribute("member", member);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return "homepage";
    }

    @GetMapping("profile")
    public String profile(Model model, @RequestParam(name = "member", required = false) String memberJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Member member = objectMapper.readValue(memberJson, Member.class);
            model.addAttribute("member", member);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "profile";
    }

}
