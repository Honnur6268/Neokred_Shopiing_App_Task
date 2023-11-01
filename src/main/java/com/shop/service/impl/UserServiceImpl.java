package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository customerRepository;

	public UserServiceImpl(UserRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public User saveCustomer(User customer) {
		return customerRepository.save(customer);
	}

	public Integer isCustomerPresent(User customer) {
		User customer1 = customerRepository.getUserByEmailAndName(customer.getEmail(), customer.getName());
		System.out.println("In Customer Service--> " + customer1);
		return customer1 != null ? customer1.getId() : null;
	}

	public List<User> getCustomers() {
		return customerRepository.findAll();
	}
}
