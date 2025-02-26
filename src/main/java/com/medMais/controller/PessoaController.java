package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.pessoa.PessoaService;
import com.medMais.domain.pessoa.dto.DataAtualizarPessoa;
import com.medMais.domain.pessoa.dto.DataAutentication;
import com.medMais.domain.pessoa.dto.DataDetalhesPessoa;
import com.medMais.infra.security.TokenDataJWT;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<TokenDataJWT> login(@RequestBody @Valid DataAutentication data) {
        return pessoaService.login(data);
    }
    
    @Transactional
    @PutMapping("/atualizar")
    public ResponseEntity<DataDetalhesPessoa> atualizar(@RequestBody @Valid DataAtualizarPessoa data,
																	  Authentication authentication) {
        return pessoaService.atualizarPessoa(data,authentication.getName());
    }
}
