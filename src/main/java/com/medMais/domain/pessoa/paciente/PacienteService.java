package com.medMais.domain.pessoa.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPaciente;
import com.medMais.domain.pessoa.paciente.dto.DataRegistroPaciente;
import com.medMais.domain.role.Role;
import com.medMais.domain.role.RoleService;
import com.medMais.infra.util.PasswordUtil;

import jakarta.validation.Valid;

@Service
public class PacienteService {
	
	@Autowired
	private PasswordUtil passwordUtil;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PacienteRepository pacienteRepository;

	public ResponseEntity<DataDetalhesPaciente> registerUsuario(@Valid DataRegistroPaciente data,
																	   UriComponentsBuilder uriBuilder) {
		Role role = roleService.findByNameRole("PACIENTE");
		var pessoa = new Paciente(data, role);
		pessoa.setSenha(passwordUtil.encrypt(data.dataRegistroPessoa().senha()));
		pacienteRepository.save(pessoa);
		var uri = uriBuilder.path("").buildAndExpand(pessoa.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DataDetalhesPaciente(pessoa));
	}

	public Paciente buscaPacienteLogin(String nome) {
		return pacienteRepository.findByLogin(nome);
	}

}
