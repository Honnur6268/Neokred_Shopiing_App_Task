package com.shop.service;

import java.util.List;

import com.shop.entity.Product;

public interface ProductService {

	public List<Product> getAllProducts();
	
	public Product saveProduct(Product product);

}
