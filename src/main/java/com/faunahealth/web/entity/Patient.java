package com.faunahealth.web.entity;

import java.util.Date;

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
@Table(name="paciente")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPaciente")
	private int id;
	
	@Column(name="Alias", nullable = false)
	private String nickname;
	
	@Column(name="ColorPelo", nullable = false)
	private String hairColor;
	
	@Column(name="Señas")
	private String signs;
	
	@Column(name="FechaNac", nullable = false)
	private Date birthday;
	
	@Column(name="Sexo", nullable = false)
	private char gender;
	
	@Column(name="Esterilizado", nullable = false)
	private boolean sterilized;
	
	@Column(name="Fallecido", nullable = false)
	private boolean dead;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idCliente")
	private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idRaza")
	private Breed breed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public String getSigns() {
		return signs;
	}

	public void setSigns(String signs) {
		this.signs = signs;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public boolean isSterilized() {
		return sterilized;
	}

	public void setSterilized(boolean sterilized) {
		this.sterilized = sterilized;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", nickname=" + nickname + ", hairColor=" + hairColor + ", signs=" + signs
				+ ", birthday=" + birthday + ", gender=" + gender + ", sterilized=" + sterilized + ", dead=" + dead
				+ "]";
	}
	
}
