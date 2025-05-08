package com.medMais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.historicotransacoes.HistoricoTransacoesService;
import com.medMais.domain.historicotransacoes.dto.DataDetalhesHistoricoTransacoes;
import com.medMais.domain.historicotransacoes.enums.StatusTransacao;

@RestController
@RequestMapping("/historicotransacoes")
public class HistoricoTransacoesController {
	
	@Autowired
	private HistoricoTransacoesService historicoTransacoesService;
	
    @GetMapping("/{status}")
	public ResponseEntity<Page<DataDetalhesHistoricoTransacoes>> buscaPorStatusH(@PathVariable StatusTransacao status,
																							   Authentication authentication,
																							   @PageableDefault(size = 10,
																							   					sort = "id",
																							   					direction = Sort.Direction.DESC) Pageable pageable){
    	return historicoTransacoesService.buscaHistoricoStatus(status,authentication.getName(),pageable);
    }
    
    @GetMapping("/status")
	public ResponseEntity<List<StatusTransacao>> buscaStatusDisponivel(Authentication authentication){
    	return ResponseEntity.ok(historicoTransacoesService.buscaHistoricoDisponivelStatus(authentication.getName()));
    }

}
