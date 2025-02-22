package com.medMais.domain.historicotransacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medMais.domain.historicotransacoes.dto.DataDetalhesHistoricoTransacoes;
import com.medMais.domain.historicotransacoes.enums.StatusTransacao;

@Service
public class HistoricoTransacoesService {
	
	@Autowired
	private HistoricoTransacoesRepository historicoTransacoesRepository;

	public ResponseEntity<Page<DataDetalhesHistoricoTransacoes>> buscaHistoricoStatus(StatusTransacao status,
																	       String name,
																		   Pageable pageable) {
		Page<HistoricoTransacoes> historico = historicoTransacoesRepository.findByStatusForMedicoOrPaciente(status,name,pageable);
		
		if (historico == null || historico.isEmpty()) {
		    throw new RuntimeException("Ainda não tem histórico de transações!");
		}
		
		Page<DataDetalhesHistoricoTransacoes> dto = historico.map(DataDetalhesHistoricoTransacoes::new);
		
		return ResponseEntity.ok(dto);
	}

}
