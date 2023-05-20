package com.AppRH.AppRH.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Beneficios implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@NotEmpty
	private String nome;
	
	private String cpf;
	
	@NotEmpty
	private String dataParceria;
	
	@NotEmpty
	private String empresaPrestadora;
	
	@NotEmpty
	private String valorDisponibilizado;
	
	@ManyToOne
	private Funcionario funcionario;
	
	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmpresaPrestadora() {
		return empresaPrestadora;
	}

	public void setEmpresaPrestadora(String empresaPrestadora) {
		this.empresaPrestadora = empresaPrestadora;
	}

	public String getDataParceria() {
		return dataParceria;
	}

	public void setDataParceria(String dataParceria) {
		this.dataParceria = dataParceria;
	}
	
	public String getValorDisponibilizado() {
		return valorDisponibilizado;
	}

	public void setValorDisponibilizado(String valorDisponibilizado) {
		this.valorDisponibilizado = valorDisponibilizado;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
