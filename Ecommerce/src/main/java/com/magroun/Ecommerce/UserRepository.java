package com.magroun.Ecommerce;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	boolean existsUserByEmail(String email);

	
	@Query(value="select * FROM user ORDER BY RAND() LIMIT 4;", 	
			nativeQuery = true)
	public List<Product> getrandom();
	
	User findByEmailIgnoreCase(String email);
	
	
}
