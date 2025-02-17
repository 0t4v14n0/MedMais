package com.medMais.domain.consulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.medMais.domain.consulta.dto.DataAtualizarConsulta;
import com.medMais.domain.consulta.dto.DataDetalhesConsulta;
import com.medMais.domain.consulta.dto.DataRegistroConsulta;
import com.medMais.domain.consulta.dto.ObsMedicoDTO;
import com.medMais.domain.consulta.enums.HorarioConsulta;
import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.historicotransacoes.HistoricoTransacoes;
import com.medMais.domain.historicotransacoes.enums.StatusTransacao;
import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.pessoa.paciente.PacienteService;

import jakarta.validation.Valid;

@Service
public class ConsultaService {
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	//Agendar Consulta
	
	public ResponseEntity<DataDetalhesConsulta> agendarConsulta (DataRegistroConsulta dataRegistroConsulta, String login) {
		
		validacaoData(dataRegistroConsulta.horarioConsulta(),dataRegistroConsulta.id());
		
		Paciente paciente = pacienteService.buscaPacienteLogin(login);
		
		Medico medico = medicoService.buscaMedicoID(dataRegistroConsulta.id());
		
		BigDecimal saldoPaciente = paciente.getSaldo();
		BigDecimal valorConsulta = medico.getValorConsulta();
		BigDecimal saldoMedico = medico.getSaldo();

		// Verifica se o paciente tem saldo suficiente
		if (saldoPaciente.compareTo(valorConsulta) < 0) {
		    throw new IllegalArgumentException("Não tem saldo suficiente...");
		}
		
		HistoricoTransacoes h = new HistoricoTransacoes();
		h.setMedico(medico);
		h.setPaciente(paciente);
		h.setRemetente(paciente.getId());
		h.setStatus(StatusTransacao.AGENDADO);
		
		paciente.setSaldo(saldoPaciente.subtract(valorConsulta));
		medico.setSaldo(saldoMedico.add(valorConsulta));
		
		Consulta consulta = new Consulta();
		
		consulta.setPaciente(paciente);
		consulta.setMedico(medico);
		consulta.setStatusConsulta(StatusConsulta.ABERTA);
		consulta.setValorConsulta(medico.getValorConsulta());
		consulta.setCriadoEm(LocalDateTime.now());
		consulta.setAtualizadoEm(LocalDateTime.now());
		consulta.setData(dataRegistroConsulta.horarioConsulta());
		
		consultaRepository.save(consulta);
				
		return ResponseEntity.ok(new DataDetalhesConsulta(consulta));
	}
	
	//Trocar Agendamento Consulta
	
	public ResponseEntity<DataDetalhesConsulta> atualizarConsulta(@Valid DataAtualizarConsulta dataAtualizarConsulta, String login) {
		
		validacaoData(dataAtualizarConsulta.novoHorarioConsulta(),dataAtualizarConsulta.idConsulta());
				
		Consulta consulta = consultaRepository.findById(dataAtualizarConsulta.idConsulta())
			    .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada!"));
		
		consulta.setData(dataAtualizarConsulta.novoHorarioConsulta());
		
		consultaRepository.save(consulta);
		
		return ResponseEntity.ok(new DataDetalhesConsulta(consulta));
	}
	
	//Cancelar Consulta

	public ResponseEntity<?> cancelarConsulta(@Valid Long id, String name) {
		
		Consulta consulta = consultaRepository.findByIdAndNomePacienteOrMedico(id, name)
											  .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada!"));
		
	    if (consulta.getStatusConsulta() == StatusConsulta.CANCELADA || consulta.getStatusConsulta() == StatusConsulta.FECHADA) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consulta já foi cancelada ou fechada anteriormente.");
	    }
	    
		consulta.setStatusConsulta(StatusConsulta.CANCELADA);
			
		Paciente paciente = consulta.getPaciente();		
		Medico medico = consulta.getMedico();
		
		BigDecimal saldoPaciente = paciente.getSaldo();
		BigDecimal valorConsulta = medico.getValorConsulta();
		BigDecimal saldoMedico = medico.getSaldo();
		
		paciente.setSaldo(saldoPaciente.add(valorConsulta));
		medico.setSaldo(saldoMedico.subtract(valorConsulta));

		consultaRepository.save(consulta);
		
	    Map<String, Object> response = new HashMap<>();
	    response.put("mensagem", "Consulta cancelada com sucesso!");
	    response.put("statusConsulta", consulta.getStatusConsulta());
	    
	    //evitar mandar dados desnecessariossa
	    if(paciente.getLogin() == name) {
	    	response.put("pacienteSaldoAtual", paciente.getSaldo());
	    }else if (medico.getLogin() == name) {
	    	response.put("medicoSaldoAtual", medico.getSaldo());
	    }
	    
	    return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<DataDetalhesConsulta> buscaConsultaId(Long id, String name) {
		
		Consulta consula = buscaConsultaID(id);
		Medico medico = consula.getMedico();
		
		if(medico.getLogin() != name) {
			throw new RuntimeException("Nao tem autorizacao !");
		}
		
		return ResponseEntity.ok(new DataDetalhesConsulta(consula));
	}

	public ResponseEntity<Page<DataDetalhesConsulta>> buscaConsultas(StatusConsulta status, String login, Pageable pageable) {
		return consultaRepository.findByIdAndStatus(status,login,pageable);
	}
	
	public ResponseEntity<DataDetalhesConsulta> terminarConsulta(Long id, ObsMedicoDTO obs, String name) {
		
		Consulta consulta = buscaConsultaID(id);
		
		Medico medico = consulta.getMedico();
		
		if(medico.getLogin() != name) {// validacao para outro medico nao fechar de outro med
			throw new RuntimeException("Nao tem autorizacao !");
		}
		
		consulta.setStatusConsulta(StatusConsulta.FECHADA);
		consulta.setAtualizadoEm(LocalDateTime.now());
		consulta.setObservacoes(obs.obsPublica());
		
		return ResponseEntity.ok(new DataDetalhesConsulta(consulta));
	}
	
	//metodos
	
	private Consulta buscaConsultaID(Long id) {
		Consulta consulta = consultaRepository.findById(id)
									   		  .orElseThrow(() -> new RuntimeException("Consulta nao encontrada !"));
		return consulta;
	}
	
	private void validacaoData(LocalDateTime date, Long id) {
		
		//validacao de horario valido
		if(!HorarioConsulta.isHorarioValido(date.toLocalTime())) {
			throw new IllegalArgumentException("Horário inválido para consulta.");
		}
		
		//validacao de horario disponivel
		Optional<Consulta> agendaExistente = consultaRepository.findByMedicoAndDataHora(id, date);
		
        if (agendaExistente.isPresent()) {
            throw new RuntimeException("Horário já ocupado para o médico.");
        }
		
	}

}
