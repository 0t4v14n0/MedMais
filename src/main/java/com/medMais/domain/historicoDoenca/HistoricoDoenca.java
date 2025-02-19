package com.medMais.domain.historicoDoenca;

import java.time.LocalDate;

import com.medMais.domain.historicoDoenca.dto.DataRegistroHistoricoDoenca;
import com.medMais.domain.historicoDoenca.enums.EstadoDoenca;
import com.medMais.domain.historicoDoenca.enums.Gravidade;
import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.paciente.Paciente;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historicodoenca")
public class HistoricoDoenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    private String nomeDaDoenca;

    private String descricao;

    private LocalDate dataDiagnostico;

    private LocalDate dataRecuperacao;

    @Enumerated(EnumType.STRING)
    private EstadoDoenca estadoAtual;  // ATIVA, CURADA, CRÔNICA, CONTROLADA

    private String tratamento;

    private String medicamentos;

    private String observacoesMedicas;

    @Enumerated(EnumType.STRING)
    private Gravidade gravidade;  // LEVE, MODERADA, GRAVE
    
    public HistoricoDoenca(){}
    
    public HistoricoDoenca(DataRegistroHistoricoDoenca data, Paciente paciente, Medico medico){
    	this.paciente = paciente;
    	this.medico = medico;
    	this.nomeDaDoenca = data.nomeDoenca();
    	this.descricao = data.descricao();
    	this.dataDiagnostico = data.dataDiagnostico();
    	this.dataRecuperacao = data.dataRecuperacao();
    	this.estadoAtual = data.estadoAtual();
    	this.tratamento = data.tratamento();
    	this.medicamentos = data.medicamentos();
    	this.observacoesMedicas = data.observacoesMedicas();
    	this.gravidade = data.gravidade();
    }

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

	public String getNomeDaDoenca() {
		return nomeDaDoenca;
	}

	public void setNomeDaDoenca(String nomeDaDoenca) {
		this.nomeDaDoenca = nomeDaDoenca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataDiagnostico() {
		return dataDiagnostico;
	}

	public void setDataDiagnostico(LocalDate dataDiagnostico) {
		this.dataDiagnostico = dataDiagnostico;
	}

	public LocalDate getDataRecuperacao() {
		return dataRecuperacao;
	}

	public void setDataRecuperacao(LocalDate dataRecuperacao) {
		this.dataRecuperacao = dataRecuperacao;
	}

	public EstadoDoenca getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(EstadoDoenca estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public String getTratamento() {
		return tratamento;
	}

	public void setTratamento(String tratamento) {
		this.tratamento = tratamento;
	}

	public String getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}

	public String getObservacoesMedicas() {
		return observacoesMedicas;
	}

	public void setObservacoesMedicas(String observacoesMedicas) {
		this.observacoesMedicas = observacoesMedicas;
	}

	public Gravidade getGravidade() {
		return gravidade;
	}

	public void setGravidade(Gravidade gravidade) {
		this.gravidade = gravidade;
	}

}
