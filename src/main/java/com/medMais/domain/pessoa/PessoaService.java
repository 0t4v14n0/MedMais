package com.medMais.domain.pessoa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.dto.DataAtualizarPessoa;
import com.medMais.domain.pessoa.dto.DataAutentication;
import com.medMais.domain.pessoa.dto.DataDetalhesPessoa;
import com.medMais.domain.role.Role;
import com.medMais.infra.exception.ErrorHandler;
import com.medMais.infra.security.TokenDataJWT;
import com.medMais.infra.security.TokenService;
import com.medMais.infra.util.Utils;

import jakarta.validation.Valid;

@Service
public class PessoaService {
	
    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private Utils utils;
	
	public ResponseEntity<TokenDataJWT> login(@Valid DataAutentication data) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
		var authentication = manager.authenticate(authenticationToken);
		
		//impede o login antes da confirmacao do email.
		Pessoa pessoa = (Pessoa) authentication.getPrincipal();
		
		if (!pessoa.isEmailConfirmado()) {
			throw new ErrorHandler.EmailNotConfirmedException("Você precisa confirmar seu e-mail para acessar a conta!");
		}
		
		var tokenJWT = tokenService.gerarToken((Pessoa) authentication.getPrincipal());  
		
		List<String> roles = pessoa.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
		
        return ResponseEntity.ok(new TokenDataJWT(tokenJWT,roles)); 
	}
	
	public ResponseEntity<DataDetalhesPessoa> atualizarPessoa(@Valid DataAtualizarPessoa data,
			 														 String login) {
		Pessoa pessoa = buscaPessoa(login);

		//pessoa
		if (!utils.isNullOrEmptyString(data.login())) pessoa.setLogin(data.login());		
		if (!utils.isNullOrEmptyString(data.senha())) pessoa.setSenha(data.senha());		
		if (!utils.isNullOrEmptyString(data.nome())) pessoa.setNome(data.nome());		
		if (!utils.isNullOrEmptyString(data.cpf())) pessoa.setCpf(data.cpf());		
		if (!utils.isNullOrEmptyString(data.email())) pessoa.setEmail(data.email());		
		if (!utils.isNullOrEmptyString(data.telefone())) pessoa.setTelefone(data.telefone());		
		if (!utils.isNullOrEmptyDate(data.dataNascimento())) pessoa.setDataNascimento(data.dataNascimento());		

		pessoaRepository.save(pessoa);

		return ResponseEntity.ok(new DataDetalhesPessoa(pessoa));
	}
	
	public Pessoa buscaPessoa(String login) {
		return (Pessoa) pessoaRepository.findByLogin(login);
	}

}
