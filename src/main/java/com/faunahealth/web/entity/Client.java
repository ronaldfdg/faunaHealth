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
@Table(name="cliente")
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCliente")
	private int id;
	
	@Column(name="NumDocumento", nullable=false)
	private String documentNumber;
	
	@Column(name="NomCliente", nullable=false)
	private String name;
	
	@Column(name="PrimerApell", nullable=false)
	private String primaryLastName;
	
	@Column(name="SegundoApell", nullable=false)
	private String secondLastName;
	
	@Column(name="Direccion", nullable=false)
	private String address;
	
	@Column(name="Telefono")
	private String phone;
	
	@Column(name="Celular", nullable=false)
	private String cellPhone;
	
	@Column(name="Correo", nullable=false)
	private String emailAddress;
	
	@Column(name="Estado", nullable=false)
	private boolean status = true;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idDistrito")
	private District district;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idDocumento")
	private DocumentType documentType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", documentNumber=" + documentNumber + ", name=" + name + ", primaryLastName="
				+ primaryLastName + ", secondLastName=" + secondLastName + ", address=" + address + ", phone=" + phone
				+ ", cellPhone=" + cellPhone + ", emailAddress=" + emailAddress + ", status=" + status + "]";
	}
	
}
