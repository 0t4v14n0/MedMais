package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.consulta.ConsultaService;
import com.medMais.domain.consulta.dto.DataDetalhesConsulta;
import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.dto.DataRegistroMedico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medico")
public class MedicoController {
		
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@Transactional
    @PostMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody @Valid DataRegistroMedico data,
													       Authentication authentication){
		return medicoService.atualizarMedico(data, authentication.getName());
	}
	
	//Busca todas as Consultas por Staus "ABERTA, FECHADA, CANCELADA"
    @GetMapping("/{status}")
	public ResponseEntity<Page<DataDetalhesConsulta>> buscaPorStatus(@PathVariable StatusConsulta status,Authentication authentication,
			   														 @PageableDefault(size = 10,
			   														 				  sort = {"id"}) Pageable pageable){
    	return consultaService.buscaConsultas(status,authentication.getName(),pageable);
    }
}
