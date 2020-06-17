package com.magroun.Ecommerce;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class User {
private long user_id;
private String first_name;
private String last_name;
private String email;
private String phone;
private String address1;
private String address2;
private String city;
private String state;
private String postalcode;
private String country;
private String password;
private String user_type;


//@OneToMany(mappedBy="User")
//private Set<Cart> cart;




protected User(){}

protected User(long user_id, String first_name, String last_name, String email, String phone, String address1, String address2,
		String city, String state, String postalcode, String country, String password, String user_type) {
	super();
	this.user_id = user_id;
	this.first_name = first_name;
	this.last_name = last_name;
	this.email = email;
	this.phone = phone;
	this.address1 = address1;
	this.address2 = address2;
	this.city = city;
	this.state = state;
	this.postalcode = postalcode;
	this.country = country;
	this.password = password;
	this.user_type = user_type;
}


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public long getUser_id() {
	return user_id;
}

public void setUser_id(long user_id) {
	this.user_id = user_id;
}

public String getFirst_name() {
	return first_name;
}

public void setFirst_name(String first_name) {
	this.first_name = first_name;
}

public String getLast_name() {
	return last_name;
}

public void setLast_name(String last_name) {
	this.last_name = last_name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getAddress1() {
	return address1;
}

public void setAddress1(String address1) {
	this.address1 = address1;
}

public String getAddress2() {
	return address2;
}

public void setAddress2(String address2) {
	this.address2 = address2;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getPostalcode() {
	return postalcode;
}

public void setPostalcode(String postalcode) {
	this.postalcode = postalcode;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}
public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getUser_type() {
	return user_type;
}

public void setUser_type(String user_type) {
	this.user_type = user_type;
}

}
