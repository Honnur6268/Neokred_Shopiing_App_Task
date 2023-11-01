package com.shop.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String orderDescription;

//	@OneToOne(cascade = CascadeType.MERGE)
//	@JoinColumn(name = "customer_email", insertable = true)
	private String customer_id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ShoppingCart.class)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private List<ShoppingCart> cartItems;

	public Order() {
	}

	public Order(String orderDescription, String customer_id, List<ShoppingCart> cartItems) {
		this.orderDescription = orderDescription;
		this.customer_id = customer_id;
		this.cartItems = cartItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public List<ShoppingCart> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<ShoppingCart> cartItems) {
		this.cartItems = cartItems;
	}
}
