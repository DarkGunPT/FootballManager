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
    public String homepage(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
            try {
                model.addAttribute("memberID", memberID);
            } catch (Exception  e) {
                e.printStackTrace();
            }
            return "homepage";
    }

    @GetMapping("profile")
    public String profile(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "profile";
    }

    @GetMapping("outgoingPayments")
    public String outgoingPayments(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "outgoingPayments";
    }

    @GetMapping("incomingPayments")
    public String incomingPayments(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "incomingPayments";
    }

    @GetMapping("paymentsHistoric")
    public String paymentsHistoric(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "paymentsHistoric";
    }

}
