package com.medMais.domain.historicotransacoes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medMais.domain.historicotransacoes.enums.StatusTransacao;

public interface HistoricoTransacoesRepository extends JpaRepository<HistoricoTransacoes, Long> {

	@Query("SELECT h FROM HistoricoTransacoes h " +
		       "WHERE h.status = :status " +
		       "AND (h.medico.login = :name OR h.paciente.login = :name)")
	Page<HistoricoTransacoes> findByStatusForMedicoOrPaciente(@Param("status") StatusTransacao status,
		                                                      @Param("name")   String name,
		                                                        			   Pageable pageable);
	

	@Query("SELECT DISTINCT h.status FROM HistoricoTransacoes h WHERE h.paciente.login = :login OR h.medico.login = :login")
	List<StatusTransacao> findDistinctStatusTransacao(@Param("login") String login);


	@Query("SELECT h FROM HistoricoTransacoes h " +
		       "WHERE h.medico.login = :name OR h.paciente.login = :name")
		Page<HistoricoTransacoes> findByAllForMedicoOrPaciente(@Param("name") String name, Pageable pageable);


}
