package com.medMais.domain.consulta;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.medMais.domain.consulta.dto.DataAtualizarConsulta;
import com.medMais.domain.consulta.dto.DataDetalhesConsulta;
import com.medMais.domain.consulta.dto.DataRegistroConsulta;
import com.medMais.domain.consulta.dto.ObsMedicoDTO;
import com.medMais.domain.consulta.enums.HorarioConsulta;
import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.historicotransacoes.HistoricoTransacoesService;
import com.medMais.domain.historicotransacoes.enums.StatusTransacao;
import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.agenda.AgendaMedico;
import com.medMais.domain.pessoa.medico.agenda.AgendaService;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;
import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.pessoa.paciente.PacienteService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ConsultaService {
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private AgendaService agendaService;
	
	@Autowired
	private HistoricoTransacoesService historicoTransacoesService;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	//Agendar Consulta
	
	public ResponseEntity<DataDetalhesConsulta> agendarConsulta (DataRegistroConsulta dataRegistroConsulta, String login) {
				
		Paciente paciente = pacienteService.buscaPacienteLogin(login);
		
		Medico medico = medicoService.buscaMedicoCRM(dataRegistroConsulta.crm());

		// Verifica se o paciente tem saldo suficiente
		if (paciente.getSaldo().compareTo(medico.getValorConsulta()) < 0) {
		    throw new IllegalArgumentException("Não tem saldo suficiente...");
		}
		
		AgendaMedico agenda = agendaService.reservaHorario(dataRegistroConsulta.id(),dataRegistroConsulta.crm());
		
		paciente.debitarSaldo(medico.getValorConsulta());
		medico.creditarSaldo(medico.getValorConsulta());
		
		Consulta consulta = new Consulta();
		
		consulta.setPaciente(paciente);
		consulta.setMedico(medico);
		consulta.setStatusConsulta(StatusConsulta.ABERTA);
		consulta.setValorConsulta(medico.getValorConsulta());
		consulta.setCriadoEm(LocalDateTime.now());
		consulta.setAtualizadoEm(LocalDateTime.now());
		consulta.setData(agenda.getHorario());
		
		consultaRepository.save(consulta);
		
		historicoTransacoesService.addHistorico(medico, paciente, login,StatusTransacao.AGENDADO);
						
		return ResponseEntity.ok(new DataDetalhesConsulta(consulta));
	}
	
	//Trocar Agendamento Consulta
	
	public ResponseEntity<DataDetalhesConsulta> atualizarConsulta(@Valid DataAtualizarConsulta dataAtualizarConsulta, String login) {
		
		validacaoData(dataAtualizarConsulta.novoHorarioConsulta(),dataAtualizarConsulta.idConsulta());
				
		Consulta consulta = consultaRepository.findById(dataAtualizarConsulta.idConsulta())
			    .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada!"));
				
		agendaService.mudarStatusAgenda(consulta.getData(), StatusAgenda.DISPONIVEL);
		AgendaMedico agenda = agendaService.reservaHorario(dataAtualizarConsulta.idHorario(),consulta.getMedico().getCrm());
		
		consulta.setData(agenda.getHorario());
		
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
	    			
		Paciente paciente = consulta.getPaciente();		
		Medico medico = consulta.getMedico();
		
		agendaService.mudarStatusAgenda(consulta.getData(), StatusAgenda.DISPONIVEL);
		
		paciente.creditarSaldo(medico.getValorConsulta());
		medico.debitarSaldo(medico.getValorConsulta());
		
		consulta.setStatusConsulta(StatusConsulta.CANCELADA);

		consultaRepository.save(consulta);
		
		historicoTransacoesService.addHistorico(medico, paciente, name, StatusTransacao.CANCELADO);
		
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
		
	    Page<Consulta> consultas;

	    if (status == StatusConsulta.TODAS) {
	        consultas = consultaRepository.findAll(pageable);
	    } else {
	        consultas = consultaRepository.findByStatusAndLogin(status, login, pageable);
	    }
	    
	    Page<DataDetalhesConsulta> dtoPage = consultas.map(DataDetalhesConsulta::new);

	    return ResponseEntity.ok(dtoPage);
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
	
	public List<StatusConsulta> buscaStatusConsultas(String login) {
		return consultaRepository.findStatusByPessoaLogin(login);
	}

	//
	
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
	
	@Scheduled(cron = "0 0 2 * * ?") // Todo dia as 2h da manha
	@Transactional
	public void atualizarStatusConsultasAbertas() {
	    List<Consulta> consultasAbertas = consultaRepository.findAllByStatusConsulta(StatusConsulta.ABERTA);
	    LocalDateTime agora = LocalDateTime.now();

	    for (Consulta consulta : consultasAbertas) {
	        if (consulta.getData().isBefore(agora)) {
	            // Atualizar o status
	            consulta.setStatusConsulta(StatusConsulta.NAO_COMPARECIDA);
	            System.out.println("aquui");

	            // Devolver creditos
	            Paciente paciente = consulta.getPaciente();
	            paciente.creditarSaldo(consulta.getValorConsulta());

	            // Retirar credito
	            Medico medico = consulta.getMedico();
	            medico.debitarSaldo(consulta.getValorConsulta());
	        }
	    }

	    consultaRepository.saveAll(consultasAbertas);
	}

}
