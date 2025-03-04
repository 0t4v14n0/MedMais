package com.medMais.domain.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	UserDetails findByLogin(String username);

	Object findByEmail(String email);

	Pessoa findByTokenConfirmacao(String token);

}
