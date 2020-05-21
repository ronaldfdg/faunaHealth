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
@Table(name = "proveedor")
public class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProveedor")
	private int id;
	
	@Column(name = "RazonSocial", nullable = false)
	private String businessName;
	
	@Column(name = "Direccion")
	private String address;
	
	@Column(name = "Telefono")
	private String phone;
	
	@Column(name = "Celular")
	private String cellPhone;
	
	@Column(name = "FechaRegistro", nullable = false)
	private Date registrationDate;
	
	@Column(name = "Correo")
	private String emailAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDistrito")
	private District district;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "Provider [id=" + id + ", businessName=" + businessName + ", address=" + address + ", phone=" + phone
				+ ", cellPhone=" + cellPhone + ", registrationDate=" + registrationDate + ", emailAddress="
				+ emailAddress + "]";
	}
	
}
