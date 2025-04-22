package com.medMais.domain.pessoa.medico;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.medMais.domain.mail.MailService;
import com.medMais.domain.pessoa.PessoaService;
import com.medMais.domain.pessoa.medico.dto.DataAtualizarMedico;
import com.medMais.domain.pessoa.medico.dto.DataDetalhesMedico;
import com.medMais.domain.pessoa.medico.dto.DataRegistroMedico;
import com.medMais.domain.role.Role;
import com.medMais.domain.role.RoleService;
import com.medMais.infra.util.PasswordUtil;
import com.medMais.infra.util.Utils;

import jakarta.mail.MessagingException;
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
    
    @Autowired
    private MailService mailService;

	public ResponseEntity<DataDetalhesMedico> registerMedico(@Valid DataRegistroMedico data,
			   												  UriComponentsBuilder uriBuilder) {
		
		utils.validacoesCadastro(data.dataRegistroPessoa().login(),
								 data.dataRegistroPessoa().email(),
								 data.dataRegistroPessoa().cpf(),
								 data.crm());

		Role role = roleService.findByNameRole("MEDICO");
		var pessoa = new Medico(data, role);
		pessoa.setSenha(passwordUtil.encrypt(data.dataRegistroPessoa().senha()));
		medicoRepository.save(pessoa);
		var uri = uriBuilder.path("").buildAndExpand(pessoa.getId()).toUri();
		
		try {
			mailService.sendVerificacaoEmail(pessoa);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.created(uri).body(new DataDetalhesMedico(pessoa));
	}
	
	public ResponseEntity<DataDetalhesMedico> atualizarMedico(@Valid DataAtualizarMedico data,
																	 String login) {
		Medico medico = (Medico) pessoaService.buscaPessoa(login);

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

	public ResponseEntity<Page<DataDetalhesMedico>> findAll(Pageable pageable) {
		
	    Page<Medico> medicos = medicoRepository.findAll(pageable);
	    
        Page<DataDetalhesMedico> detalhesMedicos = medicos.map(DataDetalhesMedico::new);

        return ResponseEntity.ok(detalhesMedicos);
	}
	
	public List<Medico> findAllMedicos() {
		
	    List<Medico> medicos = medicoRepository.findAll();
	    
        return medicos;
	}

	public ResponseEntity<Page<DataDetalhesMedico>> getMedicosByEspecialidade(EspecialidadeMedica especialidade,
																			  Pageable pageable) {
		
		Page<Medico> medicos = medicoRepository.findAllByEspecialidade(especialidade,pageable);
		
		Page<DataDetalhesMedico> detalhesMedicos = medicos.map(DataDetalhesMedico::new);

        return ResponseEntity.ok(detalhesMedicos);
	}

	public ResponseEntity<List<EspecialidadeMedica>> getAllEspecialidadesDisponiveis() {
        List<EspecialidadeMedica> especialidades = medicoRepository.findDistinctEspecialidades();
        return ResponseEntity.ok(especialidades);
	}

}
