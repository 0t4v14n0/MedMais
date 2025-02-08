package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.historicoDoenca.HistoricoDoencaService;
import com.medMais.domain.historicoDoenca.dto.DataRegistroHistoricoDoenca;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/historicodoenca")
public class HistoricoDoencaController {
	
	@Autowired
	private HistoricoDoencaService historicoDoencaService;
	
	@Transactional
    @PutMapping
    public ResponseEntity<?> registroHistoricoDoenca(@RequestBody @Valid DataRegistroHistoricoDoenca data,
    															   Authentication authentication){
		return ResponseEntity.ok(historicoDoencaService.registroHistoricoDoenca(data, authentication.getName()));
	}

}
