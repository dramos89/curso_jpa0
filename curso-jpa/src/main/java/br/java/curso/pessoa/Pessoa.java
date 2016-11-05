package br.java.curso.pessoa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNasc;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
