package com.faunahealth.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="raza")
public class Breed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idRaza")
	private int id;
	
	@Column(name="Raza", nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idEspecie")
	private Specie specie;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Specie getSpecie() {
		return specie;
	}

	public void setSpecie(Specie specie) {
		this.specie = specie;
	}

	@Override
	public String toString() {
		return "Breed [id=" + id + ", name=" + name + "]";
	}
	
}
