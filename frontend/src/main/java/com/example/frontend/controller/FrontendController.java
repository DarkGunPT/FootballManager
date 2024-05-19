package com.example.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("manageUsers")
    public String manageUsers(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "manageUsers";
    }

    @GetMapping("createUser")
    public String createUser(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "createUser";
    }

    @GetMapping("editUser")
    public String editUser(Model model, @RequestParam(name = "memberID", required = false) String memberID, @RequestParam(name = "userToEditID", required = false) String userToEditID) {
        try {
            model.addAttribute("memberID", memberID);
            model.addAttribute("userToEditID", userToEditID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "editUser";
    }

    @GetMapping("managePayments")
    public String managePayments(Model model, @RequestParam(name = "memberID", required = false) String memberID) {
        try {
            model.addAttribute("memberID", memberID);
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "managePayments";
    }

}
