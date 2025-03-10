package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.dto.DataRegistroMedico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MedicoService medicoService;
	
	@Transactional
    @PostMapping("/cadastroMedico")
    public ResponseEntity<?> registro(@RequestBody @Valid DataRegistroMedico data,
    													  UriComponentsBuilder uriBuilder) {
        return medicoService.registerMedico(data,uriBuilder);
    }
	
}
