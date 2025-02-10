package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.consulta.ConsultaService;
import com.medMais.domain.consulta.dto.DataAtualizarConsulta;
import com.medMais.domain.consulta.dto.DataDetalhesConsulta;
import com.medMais.domain.consulta.dto.DataRegistroConsulta;
import com.medMais.domain.consulta.enums.StatusConsulta;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
	
    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/agendar")
    public ResponseEntity<?> agendarConsulta(@RequestBody @Valid DataRegistroConsulta dataRegistroConsulta,
		       																		Authentication authentication) {
        return ResponseEntity.ok(consultaService.agendarConsulta(dataRegistroConsulta, authentication.getName()));        
    }
    
    @PostMapping("/atualizar")
    public ResponseEntity<?> atualizarConsulta(@RequestBody @Valid DataAtualizarConsulta dataAtualizarConsulta,
		       																		Authentication authentication) {
        return ResponseEntity.ok(consultaService.atualizarConsulta(dataAtualizarConsulta, authentication.getName()));        
    }
    
    @PostMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarConsulta(@PathVariable Long id,
		       												Authentication authentication) {
        return ResponseEntity.ok(consultaService.cancelarConsulta(id, authentication.getName()));        
    }
    
    @GetMapping("/{status}")
	public ResponseEntity<Page<DataDetalhesConsulta>> buscaPorStatus(@PathVariable StatusConsulta status,
																				   Authentication authentication,
			   														 @PageableDefault(size = 10,
			   														 				  sort = {"id"}) Pageable pageable){
    	return consultaService.buscaConsultas(status, authentication.getName(), pageable);
    }

}
