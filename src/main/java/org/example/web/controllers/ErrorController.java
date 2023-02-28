package org.example.web.controllers;

import org.example.app.exceptions.BookShelfLoginException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {


    @GetMapping("/404")
    public String notFoundError() {
        return "404";
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handler(Model model, BookShelfLoginException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "errors/404";
    }

}
