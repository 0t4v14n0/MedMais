package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.endereco.EnderecoService;
import com.medMais.domain.endereco.dto.DataRegistroEndereco;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Transactional
    @PutMapping
    public ResponseEntity<?> atualizarEndereco(@RequestBody @Valid DataRegistroEndereco data,
    															   Authentication authentication){
		return ResponseEntity.ok(enderecoService.atualizarEndereco(data, authentication.getName()));
	}
	
    @GetMapping
    public ResponseEntity<?> getEndereco(Authentication authentication){
		return ResponseEntity.ok(enderecoService.getEndereco(authentication.getName()));
	}
	
}
