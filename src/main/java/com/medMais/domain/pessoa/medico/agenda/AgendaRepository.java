package com.medMais.domain.pessoa.medico.agenda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;

public interface AgendaRepository extends JpaRepository<AgendaMedico, Long>{

	boolean existsByMedicoAndHorario(Medico medico, LocalDateTime horario);
	
	@Query("""
		    SELECT a FROM AgendaMedico a
		    WHERE a.medico.id = :medicoId
		      AND a.disponivel = :disponivel
		      AND a.horario > :dataHora
		    ORDER BY a.horario ASC
		""")
		List<AgendaMedico> findDisponivelPorMedico(
		    @Param("disponivel") StatusAgenda disponivel,
		    @Param("medicoId")   Long medicoId,
		    @Param("dataHora")   LocalDateTime dataHora
		);
	
}
