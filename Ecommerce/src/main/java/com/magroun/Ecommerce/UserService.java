package com.magroun.Ecommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> listAll() {
		return repo.findAll();
	}
	
	public void save(User user) {
		repo.save(user);
	}
	
	public User get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
	
	public boolean test (String email) {
     return repo.existsUserByEmail(email);	

}
	public boolean verify_login(String email,String password) {
		User user; 
		user = repo.findByEmailIgnoreCase(email);
		if (user == null)return false; {}
		if(user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
		
		public String Findpassword(String email) {
			User user;
			user =repo.findByEmailIgnoreCase(email);
			return user.getPassword();
			
		}
		
		public User Finduserbyemail(String email) {
			User user;
			user =repo.findByEmailIgnoreCase(email);
			return user;
			
			
		}
		
		
		
	}
	
	
	
