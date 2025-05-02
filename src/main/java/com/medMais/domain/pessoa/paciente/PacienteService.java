package com.medMais.domain.pessoa.paciente;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.mail.MailService;
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
    private MailService mailService;
    
    @Autowired
    private Utils utils;

	public ResponseEntity<DataDetalhesPaciente> registroPaciente(@Valid DataRegistroPaciente data,
																	   UriComponentsBuilder uriBuilder) {
		utils.validacoesCadastro(data.dataRegistroPessoa().login(),
								 data.dataRegistroPessoa().email(),
								 data.dataRegistroPessoa().cpf(),
								 null);//paciente nao tem crm
		
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
		return pacienteRepository.findByLogin(nome)
								 .orElseThrow(() -> new RuntimeException("Paciente nao encontrado..."));
	}
	
	public Paciente buscaPacienteId(Long id) {
		return pacienteRepository.findById(id)
				 			     .orElseThrow(() -> new RuntimeException("Paciente nao encontrado..."));
	}

	public DataDetalhesPaciente buscaDadosPorUsuario(String username) {
		
		Paciente p = pacienteRepository.findByLogin(username)
				 .orElseThrow(() -> new RuntimeException("Paciente nao encontrado..."));
		return new DataDetalhesPaciente(p);
	}

}
