package com.medMais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.historicoDoenca.HistoricoDoencaService;
import com.medMais.domain.historicoDoenca.dto.DataAtualizarHistoricoDoenca;
import com.medMais.domain.historicoDoenca.dto.DataDetalhesHistoricoDoenca;
import com.medMais.domain.historicoDoenca.dto.DataRegistroHistoricoDoenca;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/historicodoenca")
public class HistoricoDoencaController {
	
	@Autowired
	private HistoricoDoencaService historicoDoencaService;
	
	@Transactional
    @PostMapping("/{id}")
    public ResponseEntity<?> registroHistoricoDoenca(@RequestBody @Valid DataRegistroHistoricoDoenca data,
    													   @PathVariable Long id,
    															         Authentication authentication){
		return ResponseEntity.ok(historicoDoencaService.registroHistoricoDoenca(data,id, authentication.getName()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getHistoricoDoenca(@PathVariable Long id){
		return ResponseEntity.ok(historicoDoencaService.getHistoricoDoenca(id));
	}
	
	@GetMapping("/all/{id}")
	public ResponseEntity<Page<DataDetalhesHistoricoDoenca>> getTodosHistoricoDoenca(@PathVariable Long id, Pageable pageable){
		return ResponseEntity.ok(historicoDoencaService.getAllHistoricoDoenca(id,pageable));
	}
			
	@Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarHistoricoDoenca(@PathVariable Long id,@RequestBody @Valid DataAtualizarHistoricoDoenca data){
		return ResponseEntity.ok(historicoDoencaService.atualizarHistoricoDoenca(data,id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> atualizarHistoricoDoenca(@PathVariable Long id){
		return ResponseEntity.ok(historicoDoencaService.deletarHistoricoDoennca(id));
	}
	
}
