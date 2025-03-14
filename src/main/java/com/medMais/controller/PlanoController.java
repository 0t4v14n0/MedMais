package com.medMais.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.medMais.domain.pessoa.dto.DataAtualizarPessoa;
import com.medMais.domain.plano.DataDetalhesPlano;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/plano")
public class PlanoController {
	
	@Transactional
    @PostMapping("/trocar")
    public ResponseEntity<DataDetalhesPlano> atualizar(@RequestBody @Valid DataAtualizarPessoa data,
			  																Authentication authentication) {
		
		return ResponseEntity.ok(null);
	}

}
