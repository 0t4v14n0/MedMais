package com.medMais.domain.plano;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;

public interface PlanoRepository extends JpaRepository<Plano, Long> {

	Optional<Plano> findByTipoPlano(TipoPlano novoPlano);

	List<Plano> findAllPlano();

}
