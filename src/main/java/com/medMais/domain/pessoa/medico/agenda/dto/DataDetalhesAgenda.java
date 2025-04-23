package com.medMais.domain.pessoa.medico.agenda.dto;

import java.time.LocalDateTime;

import com.medMais.domain.pessoa.medico.agenda.AgendaMedico;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;

public record DataDetalhesAgenda(Long id,
							     LocalDateTime horario,
							     StatusAgenda disponivel,
							     LocalDateTime criadoEm) {
	
	public DataDetalhesAgenda(AgendaMedico agenda) {
		this(agenda.getId(),agenda.getHorario(),agenda.getDisponivel(),agenda.getCriadoEm());
	}

}
