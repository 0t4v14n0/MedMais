package com.medMais.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.mail.MailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class RecuperarSenhaController {
	
    @Autowired
    private MailService mailService;
    
    @Transactional
    @PostMapping("senha/recuperar/enviar")
    public ResponseEntity<?> EnviarRecuperarSenha(@RequestParam String emailOuLogin) throws UnsupportedEncodingException, MessagingException {
		try {
	    	mailService.enviarRecuperarSenha(emailOuLogin);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body("Erro");
		}
    	
        return ResponseEntity.ok("E-mail enviado de recuperacao !");
    }
    
    @Transactional
    @PostMapping("senha/recuperar/{token}")
    public ResponseEntity<?> recuperarSenha(
        @PathVariable String token,
        @RequestBody Map<String, String> request) {
        
        String senha = request.get("senha");
        mailService.trocarSenha(token, senha);
        
        return ResponseEntity.ok("Senha foi trocada com sucesso !");
    }

}
