package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.plano.AssinaturaService;
import com.medMais.domain.plano.dto.DataAtualizarPlano;
import com.medMais.domain.plano.dto.DataDetalhesAssinatura;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/assinatura")
public class AssinaturaController {
	
	@Autowired
	private AssinaturaService assinaturaService;
	
	@Transactional
    @PostMapping("/trocar-plano")
    public ResponseEntity<DataDetalhesAssinatura> atualizar(@RequestBody @Valid DataAtualizarPlano data,
																		   Authentication authentication) {		
		return ResponseEntity.ok(assinaturaService.trocarPlano(authentication.getName(), data));
	}

}
