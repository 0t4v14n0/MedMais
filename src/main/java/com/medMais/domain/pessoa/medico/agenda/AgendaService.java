package com.medMais.domain.pessoa.medico.agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;
import com.medMais.domain.pessoa.medico.agenda.utils.CalcFeriados;

@Service
public class AgendaService {
	
	@Autowired
	MedicoService medicoService;
	
	@Autowired
	AgendaRepository agendaRepository;

	// garante 1 hora de antecedencia
	public List<AgendaMedico> listaHorariosDisponiveisPorMedico(Long medicoId) {
	    LocalDateTime umaHoraDepois = LocalDateTime.now().plusHours(1);
	    return agendaRepository.findDisponivelPorMedico(StatusAgenda.DISPONIVEL,medicoId, umaHoraDepois);
	}
	
	@Scheduled(cron = "0 0 2 1 * ?") // Todo dia 1 do mes as 02:00
	public void gerarAgendaMensal() {
	    List<Medico> medicos = medicoService.findAllMedicos();
	    LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);
	    LocalDate ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth());

	    for (Medico medico : medicos) {
	        gerarHorariosDisponiveisParaPeriodo(medico, primeiroDia, ultimoDia);
	    }
	}
	
	public void gerarAgendaMensalNovoMedico(Medico medico) {
		
	    LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);
	    LocalDate ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth());

	    gerarHorariosDisponiveisParaPeriodo(medico, primeiroDia, ultimoDia); 
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


}
