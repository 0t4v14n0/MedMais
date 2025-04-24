package com.medMais.domain.pessoa.medico.agenda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaMedico, Long>{

	boolean existsByMedicoAndHorario(Medico medico, LocalDateTime horario);
	
	@Query("""
		    SELECT a FROM AgendaMedico a
		    WHERE a.medico.crm = :medicoCrm
		      AND a.disponivel = :disponivel
		      AND a.horario > :dataHora
		    ORDER BY a.horario ASC
		""")
		List<AgendaMedico> findDisponivelPorMedico(
		    @Param("disponivel") StatusAgenda disponivel,
		    @Param("medicoCrm")   Long medicoCrm,
		    @Param("dataHora")   LocalDateTime dataHora
		);

	@Query("""
		    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
		    FROM AgendaMedico a
		    WHERE a.horario = :horarioConsulta
		      AND a.medico.crm = :crm
		      AND a.disponivel = 'DISPONIVEL'
		""")
		boolean existsHorarioDisponivelPorMedico(@Param("horarioConsulta") LocalDateTime horarioConsulta,
		                                         @Param("crm") String string);

	@Query("""
		    SELECT a FROM AgendaMedico a
		    WHERE a.id = :id
		      AND a.medico.crm = :crm
		      AND a.disponivel = 'DISPONIVEL'
		""")
		AgendaMedico getAgendaMedicoHorarioDisponivel(@Param("id")  Long id,
		                                              @Param("crm") String crm);
	
}
