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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.consulta.ConsultaService;
import com.medMais.domain.consulta.dto.DataDetalhesConsulta;
import com.medMais.domain.consulta.dto.ObsMedicoDTO;
import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.pessoa.medico.EspecialidadeMedica;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.dto.DataAtualizarMedico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medico")
public class MedicoController {
		
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@Transactional
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody @Valid DataAtualizarMedico data,
													       Authentication authentication){
		return medicoService.atualizarMedico(data, authentication.getName());
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getMedicos(Pageable pageable) {
	    return ResponseEntity.ok(medicoService.findAll(pageable));
	}
	
	@GetMapping("/all/{especialidade}")
	public ResponseEntity<?> getMedicosByEspecialidade(@PathVariable EspecialidadeMedica especialidade,Pageable pageable) {
	    return ResponseEntity.ok(medicoService.getMedicosByEspecialidade(especialidade,pageable));
	}
	
	@GetMapping("/especialidade")
	public ResponseEntity<?> getAllEspecialidade() {
	    return ResponseEntity.ok(medicoService.getAllEspecialidadesDisponiveis());
	}
	
	@Transactional
    @PostMapping("/consulta/finalizar/{id}")
    public ResponseEntity<?> terminarConsulta(@PathVariable Long id,
    									@RequestBody @Valid ObsMedicoDTO obs,
    														Authentication authentication){
		return consultaService.terminarConsulta(id, obs, authentication.getName());
	}
	
    @GetMapping("/consulta/{id}")
	public ResponseEntity<?> buscaConsulta(@PathVariable Long id,Authentication authentication){
    	return consultaService.buscaConsultaId(id,authentication.getName());
    }
	
	//Busca todas as Consultas por Staus "ABERTA, FECHADA, CANCELADA"
    @GetMapping("/consulta/{status}")
	public ResponseEntity<Page<DataDetalhesConsulta>> buscaPorStatus(@PathVariable StatusConsulta status,Authentication authentication,
			   														 @PageableDefault(size = 10,
			   														 				  sort = {"id"}) Pageable pageable){
    	return consultaService.buscaConsultas(status,authentication.getName(),pageable);
    }
    
}
