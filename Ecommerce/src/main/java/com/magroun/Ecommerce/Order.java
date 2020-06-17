package com.magroun.Ecommerce;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private long order_id;

@ManyToOne()
@JoinColumn(name = "user_id")
private User user;

private float total_amount;

private String status;

private Date date = new Date();

public long getOrder_id() {
	return order_id;
}

public void setOrder_id(long order_id) {
	this.order_id = order_id;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public float getTotal_amount() {
	return total_amount;
}

public void setTotal_amount(float total_amount) {
	this.total_amount = total_amount;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}


	
}
