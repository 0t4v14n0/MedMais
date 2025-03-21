package com.medMais.domain.plano.dto;

import java.util.List;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.Plano;
import com.medMais.domain.plano.enums.StatusPlano;

public record DataDetalhesPlano(Long idPlanho,
								TipoPlano tipoPlano,
								double preco,
								double desconto,
								double taxasAdicionais,
								int duracao,
								List<String> beneficios,
								List<String> limitacoes,
								List<String> metodosPagamento,
								StatusPlano status) {

	public DataDetalhesPlano(Plano plano) {
		this(plano.getId(),
			 plano.getTipoPlano(),
			 plano.getPreco(),
			 plano.getDesconto(),
			 plano.getTaxasAdicionais(),
			 plano.getDuracao(),
			 plano.getBeneficios(),
			 plano.getLimitacoes(),
			 plano.getMetodosPagamento(),
			 plano.getStatus());
	}
}
