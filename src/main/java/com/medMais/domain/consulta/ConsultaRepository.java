package com.medMais.domain.consulta;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.medMais.domain.consulta.dto.DataDetalhesConsulta;
import com.medMais.domain.consulta.enums.StatusConsulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
	
	@Query("SELECT a FROM Agenda a WHERE a.medico.id = :medicoId AND a.dataHora = :dataHora")
    Optional<Consulta> findByMedicoAndDataHora(@Param("medicoId") Long medicoId, @Param("dataHora") LocalDateTime dataHora);

	@Query("SELECT c FROM Consulta c " +
		       "JOIN c.medico m " +
		       "WHERE c.statusConsulta = :status " +
		       "AND m.login = :login")
	ResponseEntity<Page<DataDetalhesConsulta>> findByIdAndStatus(StatusConsulta status, String login,
			Pageable pageable);

}
