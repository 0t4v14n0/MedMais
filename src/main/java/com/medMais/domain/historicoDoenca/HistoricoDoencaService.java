package com.medMais.domain.historicoDoenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medMais.domain.historicoDoenca.dto.DataDetalhesHistoricoDoenca;
import com.medMais.domain.historicoDoenca.dto.DataRegistroHistoricoDoenca;
import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.pessoa.paciente.PacienteService;

import jakarta.validation.Valid;

@Service
public class HistoricoDoencaService {
	
	@Autowired
	private HistoricoDoencaRepository historicoDoencaRepository;
	
	@Autowired
	private PacienteService pacienteService;

	public ResponseEntity<DataDetalhesHistoricoDoenca> registroHistoricoDoenca(@Valid DataRegistroHistoricoDoenca data, String name) {
		
		Paciente paciente = pacienteService.buscaPacienteLogin(name);
		
		HistoricoDoenca historico = new HistoricoDoenca(data,paciente);
		
		historicoDoencaRepository.save(historico);
		
		return ResponseEntity.ok(new DataDetalhesHistoricoDoenca(historico));
	}

}
