package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.entity.CustomUserDetails;
import com.shop.entity.User;
import com.shop.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = customerRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("NO USER FOUND WITH THIS NAME!!!");
		return new CustomUserDetails(user);
	}
}
