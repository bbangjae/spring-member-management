package com.example.spring_member_management.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateMemberNameException.class)
    public ModelAndView handleDuplicateMemberName(DuplicateMemberNameException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return new ModelAndView("error/duplicate-member-name", model.asMap());
    }
}