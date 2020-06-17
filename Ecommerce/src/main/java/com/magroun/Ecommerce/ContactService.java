package com.magroun.Ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactService {
	@Autowired
	private ContactRepository repo;
	
	public void save(Contact contact) {
		repo.save(contact);
	}	
	
}
