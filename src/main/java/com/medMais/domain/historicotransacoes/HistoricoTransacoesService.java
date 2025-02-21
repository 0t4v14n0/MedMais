package com.medMais.domain.historicotransacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoricoTransacoesService {
	
	@Autowired
	private HistoricoTransacoesRepository historicoTransacoesRepository;

}
