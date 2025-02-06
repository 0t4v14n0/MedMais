package com.medMais.domain.pessoa.medico;

import java.math.BigDecimal;

import com.medMais.domain.pessoa.Pessoa;
import com.medMais.domain.pessoa.medico.dto.DataRegistroMedico;
import com.medMais.domain.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicos")
public class Medico extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String crm;
	private BigDecimal valorConsulta;
    
    @Enumerated(EnumType.STRING)
    private EspecialidadeMedica especialidade;
            
    public Medico(DataRegistroMedico data, Role role) {
        super(data.dataRegistroPessoa().login(),
        	  data.dataRegistroPessoa().senha(),
        	  role,
        	  data.dataRegistroPessoa().nome(),
        	  data.dataRegistroPessoa().cpf(), 
        	  data.dataRegistroPessoa().email(), 
        	  data.dataRegistroPessoa().telefone(), 
        	  data.dataRegistroPessoa().dataNascimento(),
        	  data.dataRegistroPessoa().dataRegistroEndereco(),
        	  BigDecimal.valueOf(0.0)//saldo inicial 0
        	  );
        
        this.crm = data.crm();
        this.valorConsulta = data.valorConsulta();
        this.especialidade = data.especialidadeMedica();
    }

	public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

	public BigDecimal getValorConsulta() {
		return valorConsulta;
	}

	public void setValorConsulta(BigDecimal valorConsulta) {
		this.valorConsulta = valorConsulta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public EspecialidadeMedica getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeMedica especialidade) {
		this.especialidade = especialidade;
	}

}
