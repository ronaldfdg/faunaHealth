package com.faunahealth.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipodocumento")
public class DocumentType {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idTipoDocumento")
	private int id;
	
	@Column(name="TipoDocumento", nullable = false)
	private String name;

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

	@Override
	public String toString() {
		return "DocumentType [id=" + id + ", name=" + name + "]";
	}
	
}
