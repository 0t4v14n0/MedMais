package com.medMais.domain.plano;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.dto.DataDetalhesPlano;

@Service
public class PlanoService {
	
	@Autowired
	private PlanoRepository planoRepository;

	public DataDetalhesPlano criarPlano() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DataDetalhesPlano atualizarPlano() {
		// TODO Auto-generated method stub
		return null;
	}

	public DataDetalhesPlano getPlano(TipoPlano plano) {
		
		Plano plan = planoRepository.findByTipoPlano(plano)
				 					.orElseThrow(() -> new RuntimeException("Plano não encontrado"));
		
		return new DataDetalhesPlano(plan);
	}

	public List<DataDetalhesPlano> getAllPlano() {
	    List<Plano> planos = planoRepository.findAllPlano();

	    if (planos.isEmpty()) {
	        throw new RuntimeException("Nenhum plano encontrado");
	    }

	    return planos.stream()
	                 .map(DataDetalhesPlano::new)
	                 .collect(Collectors.toList());
	}

}
