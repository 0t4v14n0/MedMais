package com.medMais.domain.plano;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

	Optional<Assinatura> findByUsuarioId(Long usuarioId);

	Optional<Assinatura> findByOrderId(String orderId);

}
