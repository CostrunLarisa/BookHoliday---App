package com.unibuc.ro.controller;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private final ClientService clientService;

    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String login() {
        return "login.html";
    }

    @PostMapping("/")
    public ResponseEntity<String> login(ClientRequest clientRequest) {
        clientService.findByEmailAndPassword(clientRequest);
        return ResponseEntity.ok().body("User has successfully logged in!");
    }
    @GetMapping("/signup")
    public String signup(@ModelAttribute("errorMessage") String errorMessage, Model model) {
        return "signup.html";
    }
}
