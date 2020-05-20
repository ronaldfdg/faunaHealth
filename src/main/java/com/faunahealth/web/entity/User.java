package com.faunahealth.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private int id;
	
	@Column(name = "Nombre", nullable = false)
	private String name;
	
	@Column(name = "PrimerApell", nullable = false)
	private String primaryLastName;
	
	@Column(name = "SegundoApell", nullable = false)
	private String secondLastName;
	
	private String username;
	private String password;
	
	@Column(name = "PreguntaSecreta", nullable = false)
	private String secretQuestion;
	
	@Column(name = "RespuestaSecreta", nullable = false)
	private String answerQuestion;
	
	@Column(name = "Estado", nullable = false)
	private boolean status = true;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRol")
	private Role role;

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

	public String getPrimaryLastName() {
		return primaryLastName;
	}

	public void setPrimaryLastName(String primaryLastName) {
		this.primaryLastName = primaryLastName;
	}

	public String getSecondLastName() {
		return secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getAnswerQuestion() {
		return answerQuestion;
	}

	public void setAnswerQuestion(String answerQuestion) {
		this.answerQuestion = answerQuestion;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", primaryLastName=" + primaryLastName + ", secondLastName="
				+ secondLastName + ", username=" + username + ", password=" + password + ", secretQuestion="
				+ secretQuestion + ", answerQuestion=" + answerQuestion + ", status=" + status + "]";
	}
	
}
