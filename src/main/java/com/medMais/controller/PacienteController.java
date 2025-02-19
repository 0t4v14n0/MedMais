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
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.pessoa.paciente.PacienteService;
import com.medMais.domain.pessoa.paciente.dto.DataAtualizarPaciente;
import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPaciente;
import com.medMais.domain.pessoa.paciente.dto.DataRegistroPaciente;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;
	
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<DataDetalhesPaciente> register(@RequestBody @Valid DataRegistroPaciente data,
			UriComponentsBuilder uriBuilder ) {
    	return pacienteService.registroPaciente(data, uriBuilder);		
    }
    
    @PutMapping("/atualizar")
    public ResponseEntity<DataDetalhesPaciente> atualizar(@RequestBody @Valid DataAtualizarPaciente data,
																			  Authentication authentication) {
    	return pacienteService.atualizarPaciente(data, authentication.getName());		
    }
}
