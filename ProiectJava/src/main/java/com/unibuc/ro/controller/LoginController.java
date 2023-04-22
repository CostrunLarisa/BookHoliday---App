package com.unibuc.ro.controller;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientRequest;
import com.unibuc.ro.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private final ClientService clientService;
    private Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String login() {
        return "login.html";
    }

    @GetMapping("/home")
    public String getHomePage () {
        return "home.html";
    }

    @PostMapping("/")
    public ResponseEntity<String> login(ClientRequest clientRequest) {
        clientService.findByEmailAndPassword(clientRequest);
        return ResponseEntity.ok().body("User has successfully logged in!");
    }
    @GetMapping("/signup")
    public String signup(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null && error.equals("true")) {
            model.addAttribute("errorMessage", "The client is not registered.");
        }
        return "signup.html";
    }
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }
    @GetMapping("/invalidSession")
    public String invalidSessionPage(){ return "invalidSession"; }
}
