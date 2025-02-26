package com.medMais.domain.pessoa.paciente;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.mail.MailService;
import com.medMais.domain.pessoa.PessoaRepository;
import com.medMais.domain.pessoa.paciente.dto.DataAtualizarPaciente;
import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPaciente;
import com.medMais.domain.pessoa.paciente.dto.DataRegistroPaciente;
import com.medMais.domain.role.Role;
import com.medMais.domain.role.RoleService;
import com.medMais.infra.util.PasswordUtil;
import com.medMais.infra.util.Utils;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Service
public class PacienteService {
	
	@Autowired
	private PasswordUtil passwordUtil;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private Utils utils;

	public ResponseEntity<DataDetalhesPaciente> registroPaciente(@Valid DataRegistroPaciente data,
																	   UriComponentsBuilder uriBuilder) {
		//Validacoes de Login e Email
		if(pessoaRepository.findByLogin(data.dataRegistroPessoa().login()) != null) {
			throw new RuntimeException("Esse login ja foi cadastrado !");
		}
		if(pessoaRepository.findByEmail(data.dataRegistroPessoa().email()) != null) {
			throw new RuntimeException("Esse email ja foi cadastrado !");
		}
		
		Role role = roleService.findByNameRole("PACIENTE");
		var pessoa = new Paciente(data, role);
		pessoa.setSenha(passwordUtil.encrypt(data.dataRegistroPessoa().senha()));
		
		pessoa.gerarTokenConfirmacao();
		
		try {
			mailService.sendVerificacaoEmail(pessoa);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		
	    pacienteRepository.save(pessoa);
		
		var uri = uriBuilder.path("").buildAndExpand(pessoa.getId()).toUri();		
		return ResponseEntity.created(uri).body(new DataDetalhesPaciente(pessoa));
	}
	
	public ResponseEntity<DataDetalhesPaciente> atualizarPaciente(@Valid DataAtualizarPaciente data, String name) {
		
		Paciente paciente = buscaPacienteLogin(name);
		
		if(!utils.isNullOrEmptyString(data.contatoEmergencia()))paciente.setContatoEmergencia(data.contatoEmergencia());
		if(!utils.isNullOrEmptyDouble(data.altura()))paciente.setAltura(data.altura());
		if(!utils.isNullOrEmptyDouble(data.peso()))paciente.setPeso(data.peso());
		if(data.tipoSanguineo() != null)paciente.setTipoSanguineo(data.tipoSanguineo());
		
		pacienteRepository.save(paciente);
		
		return ResponseEntity.ok(new DataDetalhesPaciente(paciente));
	}

	public Paciente buscaPacienteLogin(String nome) {
		return pacienteRepository.findByLogin(nome);
	}
	
	public Paciente buscaPacienteId(Long id) {
		return pacienteRepository.findById(id).orElseThrow();
	}

}
