package com.medMais.domain.pessoa.paciente;

import java.math.BigDecimal;

import com.medMais.domain.pessoa.Pessoa;
import com.medMais.domain.pessoa.paciente.dto.DataRegistroPaciente;
import com.medMais.domain.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente extends Pessoa{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	    
    public Paciente(DataRegistroPaciente data, Role role) {
        super(data.dataRegistroPessoa().login(),
          	  data.dataRegistroPessoa().senha(),
          	  role,
          	  data.dataRegistroPessoa().nome(),
          	  data.dataRegistroPessoa().cpf(), 
          	  data.dataRegistroPessoa().email(), 
          	  data.dataRegistroPessoa().telefone(), 
          	  data.dataRegistroPessoa().dataNascimento(),
          	  data.dataRegistroPessoa().dataRegistroEndereco(),
          	  BigDecimal.valueOf(0.0)//saldo inicial
          	  );
    }

}
