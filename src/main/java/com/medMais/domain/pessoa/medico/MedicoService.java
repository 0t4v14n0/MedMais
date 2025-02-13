package com.medMais.domain.pessoa.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.pessoa.PessoaService;
import com.medMais.domain.pessoa.medico.dto.DataDetalhesMedico;
import com.medMais.domain.pessoa.medico.dto.DataRegistroMedico;
import com.medMais.domain.role.Role;
import com.medMais.domain.role.RoleService;
import com.medMais.infra.util.PasswordUtil;
import com.medMais.infra.util.Utils;

import jakarta.validation.Valid;

@Service
public class MedicoService {
	
	@Autowired
	private PasswordUtil passwordUtil;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PessoaService pessoaService;
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private Utils utils;

	public ResponseEntity<DataDetalhesMedico> registerMedico(@Valid DataRegistroMedico data,
			   												  UriComponentsBuilder uriBuilder) {
		Role role = roleService.findByNameRole("MEDICO");
		var pessoa = new Medico(data, role);
		pessoa.setSenha(passwordUtil.encrypt(data.dataRegistroPessoa().senha()));
		medicoRepository.save(pessoa);
		var uri = uriBuilder.path("").buildAndExpand(pessoa.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DataDetalhesMedico(pessoa));
	}
	
	public ResponseEntity<DataDetalhesMedico> atualizarMedico(@Valid DataRegistroMedico data,
																	 String login) {
		Medico medico = (Medico)pessoaService.buscaPessoa(login);
		
		//medico
		if (!utils.isNullOrEmptyString(data.crm())) medico.setCrm(data.crm());
		if (!utils.isNullOrEmptyFloat(data.valorConsulta())) medico.setValorConsulta(data.valorConsulta());
		if (!utils.isNullOrEmptyCategoria(data.especialidadeMedica())) medico.setEspecialidade(data.especialidadeMedica());	
	
		medicoRepository.save(medico);
		
		return ResponseEntity.ok(new DataDetalhesMedico(medico));
	}
	
	public Medico buscaMedicoID(Long id) {
		return medicoRepository.findById(id)
                			   .orElseThrow(() -> new RuntimeException("Médico não encontrado."));
	}

	public Medico buscaMedicoLogin(String name) {
		return medicoRepository.findByLogin(name);
	}

}
