package com.medMais.domain.plano.dto;

import java.time.LocalDate;

import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.plano.Assinatura;
import com.medMais.domain.plano.enums.StatusPagamento;

public record DataDetalhesAssinatura(Long id,
									 Paciente paciente,
									 DataDetalhesPlano plano,
									 LocalDate dataInicio,
									 LocalDate dataExpiracao,
									 boolean ativo,
									 StatusPagamento statusPagamento) {

	public DataDetalhesAssinatura(Assinatura assinatura) {
		this(assinatura.getId(),
			 assinatura.getUsuario(),
			 new DataDetalhesPlano(assinatura.getPlano()),
			 assinatura.getDataInicio(),
			 assinatura.getDataExpiracao(),
			 assinatura.isAtivo(),
			 assinatura.getStatusPagamento());
	}

}
