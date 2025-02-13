package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.mail.MailService;

@RestController
@RequestMapping("/auth")
public class ConfirmacaoEmailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/confirmar-email")
    public ResponseEntity<?> confirmarEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(mailService.confirmarEmail(token));
    }
}
