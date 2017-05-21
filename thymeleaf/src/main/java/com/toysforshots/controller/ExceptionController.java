package com.toysforshots.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model){
        model.addAttribute("exception", exception);
        return "postError";
    }

}