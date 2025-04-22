package com.medMais.domain.pessoa.medico.agenda;

import java.time.LocalDateTime;

import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.medico.agenda.enums.StatusAgenda;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendaMedico")
public class AgendaMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    private StatusAgenda disponivel;

    private LocalDateTime criadoEm = LocalDateTime.now();
    

	public AgendaMedico(Medico medico, LocalDateTime horario) {
		this.medico = medico;
		this.horario = horario;
		this.disponivel = StatusAgenda.DISPONIVEL;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public LocalDateTime getHorario() {
		return horario;
	}

	public void setHorario(LocalDateTime horario) {
		this.horario = horario;
	}

	public StatusAgenda getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(StatusAgenda disponivel) {
		this.disponivel = disponivel;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

}
