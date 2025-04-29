package com.medMais.domain.consulta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medMais.domain.consulta.enums.StatusConsulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
	
	@Query("SELECT c FROM Consulta c WHERE c.medico.id = :medicoId AND c.data = :dataHora")
	Optional<Consulta> findByMedicoAndDataHora(@Param("medicoId") Long id, @Param("dataHora") LocalDateTime dataHora);

	@Query("SELECT c FROM Consulta c " +
		       "JOIN c.medico m " +
		       "JOIN c.paciente p " +
		       "WHERE c.statusConsulta = :status " +
		       "AND (m.login = :login OR p.login = :login)")
	Page<Consulta> findByStatusAndLogin(@Param("status") StatusConsulta status, 
		                                @Param("login") String login, 
		                                Pageable pageable);


	@Query("SELECT c FROM Consulta c " +
		   "JOIN c.paciente p " +
		   "JOIN c.medico m " +
		   "WHERE c.id = :idConsulta " +
		   "AND (p.nome = :nome OR m.nome = :nome)")
	Optional<Consulta> findByIdAndNomePacienteOrMedico(@Param("idConsulta") Long idConsulta, 
                                                   	   @Param("nome") String nome);

	
	@Query("SELECT DISTINCT c.statusConsulta FROM Consulta c " +
		       "JOIN c.medico m " +
		       "JOIN c.paciente p " +
		       "WHERE m.login = :login OR p.login = :login")
	List<StatusConsulta> findStatusByPessoaLogin(@Param("login") String login);
}
