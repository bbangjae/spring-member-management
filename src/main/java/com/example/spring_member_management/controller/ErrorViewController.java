package com.example.spring_member_management.controller;

import com.example.spring_member_management.exception.BaseResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorViewController {

    @GetMapping("/error/common")
    public String commonErrorPage(@RequestParam(required = false) String msg, Model model) {
        if (msg != null) {
            model.addAttribute("errorMessage", msg);
            return "error/common-error";
        }
        model.addAttribute("errorMessage", BaseResponseCode.INTERNAL_ERROR.getMessage());
        return "error/common-error";
    }
}