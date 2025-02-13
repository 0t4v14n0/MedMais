package com.medMais.domain.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	UserDetails findByLogin(String username);

	Object findByEmail(@NotNull @NotBlank String email);

	Pessoa findByTokenConfirmacao(String token);

}
