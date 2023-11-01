package com.shop.service;

import java.util.List;

import com.shop.entity.User;

public interface UserService {

	public User saveCustomer(User customer);

	public Integer isCustomerPresent(User customer);

	public List<User> getCustomers();

}
