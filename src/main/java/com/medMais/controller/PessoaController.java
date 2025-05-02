package com.medMais.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.pessoa.PessoaService;
import com.medMais.domain.pessoa.dto.DataAtualizarPessoa;
import com.medMais.domain.pessoa.dto.DataAutentication;
import com.medMais.domain.pessoa.dto.DataDetalhesPessoa;
import com.medMais.domain.pessoa.medico.MedicoService;
import com.medMais.domain.pessoa.medico.dto.DataDetalhesMedico;
import com.medMais.domain.pessoa.paciente.PacienteService;
import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPaciente;
import com.medMais.infra.security.TokenDataJWT;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<TokenDataJWT> login(@RequestBody @Valid DataAutentication data) {
        return pessoaService.login(data);
    }
    
    @Transactional
    @PutMapping("/atualizar")
    public ResponseEntity<DataDetalhesPessoa> atualizar(@RequestBody @Valid DataAtualizarPessoa data,
																	  Authentication authentication) {
        return pessoaService.atualizarPessoa(data,authentication.getName());
    }
    
    @GetMapping("/dados")
    public ResponseEntity<?> getDadosPessoa(Authentication authentication) {
    	
        String username = authentication.getName();
        var roles = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());

        if (roles.contains("PACIENTE")) {
            DataDetalhesPaciente data = pacienteService.buscaDadosPorUsuario(username);
            return ResponseEntity.ok(data);
        } else if (roles.contains("MEDICO")) {
            DataDetalhesMedico data = medicoService.buscaDadosPorUsuario(username);
            return ResponseEntity.ok(data);
        } 
//        else if (roles.contains("ROLE_ADMIN")) {
//            DataDetalhesAdmin data = adminService.buscaDadosPorUsuario(username);
//            return ResponseEntity.ok(data);
//        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Perfil não reconhecido.");
    }
}
