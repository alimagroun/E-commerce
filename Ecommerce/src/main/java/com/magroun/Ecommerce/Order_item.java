package com.magroun.Ecommerce;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Order_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	order_item_id;
	
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
	
    private float price;
    private int quantity;
	public long getOrder_item_id() {
		return order_item_id;
	}
	public void setOrder_item_id(long order_item_id) {
		this.order_item_id = order_item_id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    
}
