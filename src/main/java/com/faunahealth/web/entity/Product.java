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
@Table(name = "producto")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProducto")
	private int id;
	
	@Column(name = "Descripcion", nullable = false)
	private String name;
	
	@Column(name = "FechaCompra", nullable = false)
	private Date purchaseDate;
	
	@Column(name = "Costo", nullable = false)
	private double cost;
	
	@Column(name = "PrecioVenta", nullable = false)
	private double salePrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEspecie")
	private Specie specie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoProducto")
	private ProductKind productKind;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProveedor")
	private Provider provider;

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

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public Specie getSpecie() {
		return specie;
	}

	public void setSpecie(Specie specie) {
		this.specie = specie;
	}

	public ProductKind getProductKind() {
		return productKind;
	}

	public void setProductKind(ProductKind productKind) {
		this.productKind = productKind;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", purchaseDate=" + purchaseDate + ", cost=" + cost
				+ ", salePrice=" + salePrice + ", specie=" + specie + ", productKind=" + productKind + ", provider="
				+ provider + "]";
	}
	
}
