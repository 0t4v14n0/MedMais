package com.medMais.domain.pessoa.medico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Medico findByLogin(String name);

	Page<Medico> findAll(Pageable pageable);

	Page<Medico> findAllByEspecialidade(EspecialidadeMedica especialidade, Pageable pageable);
	
	@Query("SELECT DISTINCT m.especialidade FROM Medico m")
    List<EspecialidadeMedica> findDistinctEspecialidades();

	Object findByCrm(String crm);

}
