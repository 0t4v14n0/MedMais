package com.medMais.domain.historicoDoenca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medMais.domain.historicoDoenca.dto.DataDetalhesHistoricoDoenca;

public interface HistoricoDoencaRepository extends JpaRepository<HistoricoDoenca, Long>{

	@Query("SELECT h FROM HistoricoDoenca h WHERE h.paciente.id = :pacienteId")
	Page<DataDetalhesHistoricoDoenca> findAllByPacienteId(@Param("pacienteId") Long pacienteId, Pageable pageable);

}
