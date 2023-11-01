package com.shop.service;

import java.util.List;

import com.shop.entity.Order;
import com.shop.entity.ShoppingCart;

public interface OrderService {

	public Order getOrderDetail(int orderId);

	public float getCartAmount(List<ShoppingCart> shoppingCartList);

	public Order saveOrder(Order order);

}
