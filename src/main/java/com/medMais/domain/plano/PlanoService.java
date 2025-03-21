package com.medMais.domain.plano;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.dto.DataAtualizarPlano;
import com.medMais.domain.plano.dto.DataDetalhesPlano;
import com.medMais.domain.plano.dto.DataRegisterPlano;
import com.medMais.domain.plano.enums.StatusPlano;
import com.medMais.infra.util.EnumValidator;
import com.medMais.infra.util.Utils;

@Service
public class PlanoService {
	
	@Autowired
	private PlanoRepository planoRepository;
	
	@Autowired
    private Utils utils;

	public DataDetalhesPlano criarPlano(DataRegisterPlano data) {
		Plano plano = new Plano(data);
		planoRepository.save(plano);
		return new DataDetalhesPlano(plano);
	}
	
	public DataDetalhesPlano atualizarPlano(DataAtualizarPlano data) {
		
		Plano plano = planoRepository.findByNome(data.nome())
                								.orElseThrow(() -> new RuntimeException("Plano não encontrado"));
		
		if (!utils.isNullOrEmptyString(data.nome()))plano.setNome(data.nome());
		
		if (!utils.isNullOrEmptyDouble(data.preco()))plano.setPreco(data.preco());
		if (!utils.isNullOrEmptyDouble(data.desconto()))plano.setDesconto(data.desconto());
		if (!utils.isNullOrEmptyDouble(data.taxasAdicionais()))plano.setTaxasAdicionais(data.taxasAdicionais());
		
		if (!utils.isNullOrEmptyInt(data.duracao()))plano.setDuracao(data.duracao());
		
		if (!utils.isNullOrEmptyList(data.beneficios()))plano.setBeneficios(data.beneficios());
		if (!utils.isNullOrEmptyList(data.limitacoes()))plano.setLimitacoes(data.limitacoes());
		if (!utils.isNullOrEmptyList(data.metodosPagamento()))plano.setMetodosPagamento(data.metodosPagamento());
		
		if (!EnumValidator.isValidEnum(StatusPlano.class, data.status())) plano.setStatus(data.status());
		if (!EnumValidator.isValidEnum(TipoPlano.class, data.tipo())) plano.setTipoPlano(data.tipo());
		
		planoRepository.save(plano);
		
		return new DataDetalhesPlano(plano);
	}

	public DataDetalhesPlano getPlano(TipoPlano plano) {
		
		Plano plan = planoRepository.findByTipoPlano(plano)
				 					.orElseThrow(() -> new RuntimeException("Plano não encontrado"));
		
		return new DataDetalhesPlano(plan);
	}

	public List<DataDetalhesPlano> getAllPlano() {
	    List<Plano> planos = planoRepository.findAll();

	    if (planos.isEmpty()) {
	        throw new RuntimeException("Nenhum plano encontrado");
	    }

	    return planos.stream()
	                 .map(DataDetalhesPlano::new)
	                 .collect(Collectors.toList());
	}

}
