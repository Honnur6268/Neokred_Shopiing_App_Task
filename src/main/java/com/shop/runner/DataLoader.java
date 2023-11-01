package com.shop.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shop.entity.User;
import com.shop.entity.Product;
import com.shop.repository.UserRepository;
import com.shop.repository.ProductRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private UserRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		repository.deleteAll();

		User customer1 = new User("Honnur Ali", "honnurali68@gmail.com", "honnurali68@gmail.com", "6268", "USER");
		User customer2 = new User("Test User1", "testuser1@gmail.com", "testuser1@gmail.com", "testuser1", "USER");
		User customer3 = new User("Test User2", "testuser2@gmail.com", "testuser2@gmail.com", "testuser2", "USER");
		User customer4 = new User("Test User3", "testuser3@gmail.com", "testuser3@gmail.com", "testuser3", "USER");

		List<User> saveCustomers = Arrays.asList(customer1, customer2, customer3, customer4);
		repository.saveAll(saveCustomers);

		productRepository.deleteAll();

		Product product1 = new Product("Laptop", 2, 45900.00f);
		Product product2 = new Product("Mobile", 5, 8000.00f);
		Product product3 = new Product("Smart Watch", 1, 5000.00f);

		List<Product> saveProduct = Arrays.asList(product1, product2, product3);
		productRepository.saveAll(saveProduct);

	}

}
