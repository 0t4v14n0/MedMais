package com.medMais.domain.historicotransacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medMais.domain.historicotransacoes.enums.StatusTransacao;

public interface HistoricoTransacoesRepository extends JpaRepository<HistoricoTransacoes, Long> {

	@Query("SELECT h FROM HistoricoTransacoes h " +
		       "WHERE h.status = :status " +
		       "AND (h.medico.name = :name OR h.paciente.name = :name)")
		Page<HistoricoTransacoes> findByStatusForMedicoOrPaciente(@Param("status") StatusTransacao status,
		                                                          @Param("name") String name,
		                                                          Pageable pageable);


}
