package com.medMais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.PlanoService;
import com.medMais.domain.plano.dto.DataDetalhesPlano;
import com.medMais.domain.plano.dto.DataRegisterPlano;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/plano")
public class PlanoController {
	
	@Autowired
	private PlanoService planoService;
	
	@Transactional
    @PostMapping("/criar")
    public ResponseEntity<DataDetalhesPlano> criar(@RequestBody @Valid DataRegisterPlano data) {
		return ResponseEntity.ok(planoService.criarPlano(data));
	}
	
    @GetMapping
    public ResponseEntity<DataDetalhesPlano> getPlano(TipoPlano plano) {
		return ResponseEntity.ok(planoService.getPlano(plano));
	}
    
    @GetMapping
    public ResponseEntity<List<DataDetalhesPlano>> getAllPlano() {	
		return ResponseEntity.ok(planoService.getAllPlano());
	}

}
