package com.medMais.domain.historicoDoenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medMais.domain.historicoDoenca.dto.DataAtualizarHistoricoDoenca;
import com.medMais.domain.historicoDoenca.dto.DataDetalhesHistoricoDoenca;
import com.medMais.domain.historicoDoenca.dto.DataRegistroHistoricoDoenca;
import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.pessoa.paciente.PacienteService;
import com.medMais.infra.util.Utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class HistoricoDoencaService {
	
	@Autowired
	private HistoricoDoencaRepository historicoDoencaRepository;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private Utils utils;

	public ResponseEntity<DataDetalhesHistoricoDoenca> registroHistoricoDoenca(@Valid DataRegistroHistoricoDoenca data, Long id, String name) {
		
		Paciente paciente = pacienteService.buscaPacienteId(id);
		
		Medico medico = medicoService.buscaMedicoLogin(name);
		
		HistoricoDoenca historico = new HistoricoDoenca(data,paciente,medico);
		
		historicoDoencaRepository.save(historico);
		
		return ResponseEntity.ok(new DataDetalhesHistoricoDoenca(historico));
	}
	
	public ResponseEntity<DataDetalhesHistoricoDoenca> atualizarHistoricoDoenca(@Valid DataAtualizarHistoricoDoenca data, Long id) {
		
		HistoricoDoenca historico = historicoPorId(id);
		
		if(!utils.isNullOrEmptyString(data.nomeDoenca()))historico.setNomeDaDoenca(data.nomeDoenca());
		if(!utils.isNullOrEmptyString(data.descricao()))historico.setDescricao(data.descricao());
		if(!utils.isNullOrEmptyString(data.tratamento()))historico.setTratamento(data.tratamento());
		if(!utils.isNullOrEmptyString(data.medicamentos()))historico.setMedicamentos(data.medicamentos());
		if(!utils.isNullOrEmptyString(data.observacoesMedicas()))historico.setObservacoesMedicas(data.observacoesMedicas());
		
		if(data.estadoAtual() != null)historico.setEstadoAtual(data.estadoAtual());
		if(data.gravidade() != null)historico.setGravidade(data.gravidade());;
		
		if(!utils.isNullOrEmptyDate(data.dataDiagnostico()))historico.setDataDiagnostico(data.dataDiagnostico());
		if(!utils.isNullOrEmptyDate(data.dataRecuperacao()))historico.setDataRecuperacao(data.dataRecuperacao());;
				
		historicoDoencaRepository.save(historico);
		
		return ResponseEntity.ok(new DataDetalhesHistoricoDoenca(historico));
	}
	
	public ResponseEntity<String> deletarHistoricoDoennca(Long id){
		
		HistoricoDoenca historico = historicoPorId(id);
		historicoDoencaRepository.delete(historico);
		
		return ResponseEntity.ok("Historico Deletado!");
	}
	
	public ResponseEntity<DataDetalhesHistoricoDoenca> getHistoricoDoenca(Long id){
		HistoricoDoenca historico = historicoPorId(id);
		return ResponseEntity.ok(new DataDetalhesHistoricoDoenca(historico));
	}
	
	public Page<DataDetalhesHistoricoDoenca> getAllHistoricoDoenca(Long id, Pageable pageable) {
		return historicoDoencaRepository.findAllByPacienteId(id, pageable);
	}
	
	private HistoricoDoenca historicoPorId(Long id) {
		HistoricoDoenca historico = historicoDoencaRepository.findById(id)
				 .orElseThrow(() -> new EntityNotFoundException("Historico de doenca nao encontrado"));
		return historico;
	}
}
