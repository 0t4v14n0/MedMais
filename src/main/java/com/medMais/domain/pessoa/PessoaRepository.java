package com.medMais.domain.pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Object findByEmail(String email);

	Pessoa findByTokenConfirmacao(String token);

	@Query("SELECT p FROM Pessoa p WHERE p.login = :login")
	Pessoa findByLogin(@Param("login") String login);

	Object findByCpf(String cpf);

	boolean existsByEmailOrLogin(String email, String login);

	@Query("SELECT p FROM Pessoa p WHERE " +
		       "(p.email = :emailOuLogin OR p.login = :emailOuLogin) " +
		       "AND p.emailConfirmado = true")
	Optional<Pessoa> findByEmailOrLoginWithConfirmedEmail(
		    @Param("emailOuLogin") String emailOuLogin);

}
