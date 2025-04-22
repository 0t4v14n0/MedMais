package com.medMais.domain.pessoa.medico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Medico findByLogin(String name);
	
	@Query("SELECT m FROM Medico m WHERE m.emailConfirmado = true")
	List<Medico> findAllWithEmailConfirmed();
	
	@Query("SELECT m FROM Medico m WHERE m.emailConfirmado = true")
	Page<Medico> findAllWithEmailConfirmedPageable(Pageable pageable);

	@Query("SELECT m FROM Medico m WHERE m.emailConfirmado = true AND m.id = :medicoId")
	Page<Medico> findMedicoByIdAndEmailConfirmadoTrue(@Param("medicoId") Long medicoId, Pageable pageable);

	@Query("SELECT m FROM Medico m WHERE m.emailConfirmado = true AND m.especialidade = :especialidade")
	Page<Medico> findByEspecialidadeAndEmailConfirmadoTrue(@Param("especialidade") EspecialidadeMedica especialidade, Pageable pageable);
	
	@Query("SELECT DISTINCT m.especialidade FROM Medico m WHERE m.emailConfirmado = true")
    List<EspecialidadeMedica> findDistinctEspecialidades();

	Object findByCrm(String crm);

}
