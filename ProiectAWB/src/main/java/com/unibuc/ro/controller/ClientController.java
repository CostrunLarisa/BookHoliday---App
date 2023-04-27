package com.unibuc.ro.controller;

import com.unibuc.ro.model.Client;
import com.unibuc.ro.model.ClientSignupRequest;
import com.unibuc.ro.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping
    public ModelAndView addClient(ClientSignupRequest client) {
        ModelAndView modelAndView =  new ModelAndView();
        clientService.save(client);
        LOGGER.info("After save");
        modelAndView.addObject("successMessage", "Client has been registered!");
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clientService.findById(id));
    }
    @GetMapping("/notFound")
    public String showErrorPage(@ModelAttribute("errorMessage") String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "signup";
    }
}
