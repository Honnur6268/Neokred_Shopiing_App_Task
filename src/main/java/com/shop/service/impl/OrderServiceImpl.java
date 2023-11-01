package com.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.Order;
import com.shop.entity.Product;
import com.shop.entity.ShoppingCart;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import com.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	public Order getOrderDetail(int orderId) {
		Optional<Order> order = this.orderRepository.findById(orderId);
		return order.isPresent() ? order.get() : null;
	}

	public float getCartAmount(List<ShoppingCart> shoppingCartList) {

		float totalCartAmount = 0f;
		float singleCartAmount = 0f;
		int availableQuantity = 0;

		for (ShoppingCart cart : shoppingCartList) {

			int productId = cart.getProductId();
			Optional<Product> product = productRepository.findById(productId);
			if (product.isPresent()) {
				Product product1 = product.get();
				if (product1.getAvailableQuantity() < cart.getQuantity()) {
					singleCartAmount = product1.getPrice() * product1.getAvailableQuantity();
					cart.setQuantity(product1.getAvailableQuantity());
				} else {
					singleCartAmount = cart.getQuantity() * product1.getPrice();
					availableQuantity = product1.getAvailableQuantity() - cart.getQuantity();
				}
				totalCartAmount = totalCartAmount + singleCartAmount;
				product1.setAvailableQuantity(availableQuantity);
				availableQuantity = 0;
				cart.setProductName(product1.getName());
				cart.setAmount(singleCartAmount);
				productRepository.save(product1);
			}
		}
		return totalCartAmount;
	}

	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

}
