package com.medMais.domain.pessoa.medico.agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.agenda.dto.DataDetalhesAgenda;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;
import com.medMais.domain.pessoa.medico.agenda.utils.CalcFeriados;

@Service
public class AgendaService {
	
	@Autowired
	@Lazy
	private MedicoService medicoService;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	
	public ResponseEntity<List<DataDetalhesAgenda>> listaAgendaMedicoDisponivel(Long crm){
		
		List<AgendaMedico> lista = listaHorariosDisponiveisPorMedico(crm);
		
		if(lista == null) {
			throw new IllegalArgumentException("Sem agenda disponivel.");
		}
		
	    List<DataDetalhesAgenda> listaDTO = lista.stream()
	            .map(DataDetalhesAgenda::new)
	            .toList();

	    return ResponseEntity.ok(listaDTO);
	}

	// garante 1 hora de antecedencia
	public List<AgendaMedico> listaHorariosDisponiveisPorMedico(Long medicoCrm) {
	    LocalDateTime umaHoraDepois = LocalDateTime.now().plusHours(1);
	    return agendaRepository.findDisponivelPorMedico(StatusAgenda.DISPONIVEL,medicoCrm, umaHoraDepois);
	}
	
	@Scheduled(cron = "0 0 2 1 * ?") // Todo dia 1 do mes as 02:00
	public void gerarAgendaMensal() {
		
	    List<Medico> medicos = medicoService.findAllMedicos();	    
	    
	    LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);
	    LocalDate ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth());

	    for (Medico medico : medicos) {
	    	arquivarListaDeHorariosPassado(medico.getId());//pega a lista antiga e coloca como ARQUIVADO
	        gerarHorariosDisponiveisParaPeriodo(medico, primeiroDia, ultimoDia); //Gera uma nova lista de agenda
	    }
	}
	
	//pega o dia que for ativado o email...
	public void gerarAgendaMensalNovoMedico(Medico medico) {
		
	    LocalDate primeiroDia = LocalDate.now();
	    LocalDate ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth());

	    gerarHorariosDisponiveisParaPeriodo(medico, primeiroDia, ultimoDia); 
	}
	
	public AgendaMedico agendaMedicaPorData(LocalDateTime data) {
		AgendaMedico agenda = agendaRepository.findByHorario(data);
		return agenda;
	}
	
	public void gerarHorariosDisponiveisParaPeriodo(Medico medico, LocalDate inicio, LocalDate fim) {
		
	    List<LocalDate> feriados = CalcFeriados.obterFeriadosNacionais(inicio.getYear());
	    
	    for (LocalDate dia = inicio; !dia.isAfter(fim); dia = dia.plusDays(1)) {
	    	
	        // Atualiza lista de feriados se mudou de ano
	        if (!feriados.isEmpty() && dia.getYear() != feriados.get(0).getYear()) {
	            feriados = CalcFeriados.obterFeriadosNacionais(dia.getYear());
	        }

	        boolean diaUtil = dia.getDayOfWeek().getValue() <= 5;
	        boolean naoEhFeriado = !feriados.contains(dia);

	        if (diaUtil && naoEhFeriado) {
	            for (int hora = 8; hora <= 16; hora += 2) {
	            	
	                LocalDateTime horario = LocalDateTime.of(dia, LocalTime.of(hora, 0));
	                
	                if (!agendaRepository.existsByMedicoAndHorario(medico, horario)) {
	                    agendaRepository.save(new AgendaMedico(medico, horario));
	                }
	                
	            }
	        }
	    }
	}
	
	public void arquivarListaDeHorariosPassado(Long id) {
		
	    List<AgendaMedico> lista = listaHorariosDisponiveisPorMedico(id);

	    lista.forEach(agenda -> agenda.setDisponivel(StatusAgenda.ARQUIVADO));

	    agendaRepository.saveAll(lista);
	}

	public AgendaMedico reservaHorario(Long id , String crm) {
		
		AgendaMedico agenda = agendaRepository.getAgendaMedicoHorarioDisponivel(id,crm);
		agenda.setDisponivel(StatusAgenda.OCUPADO);
		agendaRepository.save(agenda);
		
		return agenda;
	}

	//muda Statuus
	public void mudarStatusAgenda(LocalDateTime data, StatusAgenda status) {
		AgendaMedico agenda = agendaMedicaPorData(data);
		agenda.setDisponivel(status);
		
		agendaRepository.save(agenda);
		
	}

}
