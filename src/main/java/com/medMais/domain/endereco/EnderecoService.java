package com.medMais.domain.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medMais.domain.endereco.dto.DataDetalhesEndereco;
import com.medMais.domain.endereco.dto.DataRegistroEndereco;
import com.medMais.domain.pessoa.Pessoa;
import com.medMais.domain.pessoa.PessoaService;
import com.medMais.infra.util.Utils;

import jakarta.validation.Valid;

@Service
public class EnderecoService {
	
    @Autowired
    private PessoaService pessoaService;
    
    @Autowired
    private Utils utils;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
	
	public ResponseEntity<DataDetalhesEndereco> atualizarEndereco(@Valid DataRegistroEndereco data, String login){
		
		Pessoa pessoa = pessoaService.buscaPessoa(login);
		Endereco endereco = pessoa.getEndereco();
		
		//Endereco
		if(!utils.isNullOrEmptyString(data.endereco())) endereco.setEndereco(data.endereco());
		if(!utils.isNullOrEmptyString(data.cidade())) endereco.setCidade(data.cidade());
		if(!utils.isNullOrEmptyString(data.estado())) endereco.setEstado(data.estado());
		if(!utils.isNullOrEmptyString(data.cep())) endereco.setCep(data.cep());
		if(!utils.isNullOrEmptyString(data.pais())) endereco.setPais(data.pais());
		if(!utils.isNullOrEmptyString(data.referencia())) endereco.setReferencia(data.referencia());
		
		enderecoRepository.save(endereco);
		
		return ResponseEntity.ok(new DataDetalhesEndereco(endereco));
	}

	public ResponseEntity<DataDetalhesEndereco> getEndereco(String name) {
		
		Pessoa p = pessoaService.buscaPessoa(name);
		
		return ResponseEntity.ok(new DataDetalhesEndereco(p.getEndereco()));
	}

}
