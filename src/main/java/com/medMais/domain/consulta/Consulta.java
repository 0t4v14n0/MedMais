package com.medMais.domain.consulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.paciente.Paciente;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    
    private BigDecimal valorConsulta;
    
    @Enumerated(EnumType.STRING)
    private StatusConsulta statusConsulta;

    private LocalDateTime data;
    
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    public Consulta() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public BigDecimal getValorConsulta() {
		return valorConsulta;
	}

	public void setValorConsulta(BigDecimal valorConsulta) {
		this.valorConsulta = valorConsulta;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(LocalDateTime atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public StatusConsulta getStatusConsulta() {
		return statusConsulta;
	}

	public void setStatusConsulta(StatusConsulta statusConsulta) {
		this.statusConsulta = statusConsulta;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
 
}
