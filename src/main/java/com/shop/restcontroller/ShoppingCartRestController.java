package com.shop.restcontroller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.OrderDTO;
import com.shop.dto.ResponseOrderDTO;
import com.shop.entity.Order;
import com.shop.entity.Product;
import com.shop.entity.ShoppingCart;
import com.shop.entity.User;
import com.shop.repository.ShoppingCartRepository;
import com.shop.service.OrderService;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import com.shop.util.DateUtil;

@RestController
@RequestMapping("/api/shop")
public class ShoppingCartRestController {

	private Logger logger = LoggerFactory.getLogger(ShoppingCartRestController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService customerService;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@GetMapping("/customers")
	public ResponseEntity<List<User>> getAllCustomers() {
		List<User> customers = customerService.getCustomers();
		return ResponseEntity.ok(customers);
	}

	@PostMapping("/customers")
	public ResponseEntity<String> insertCustomer(@RequestBody User customer) {
		User customers = customerService.saveCustomer(customer);
		if (customers != null) {

			return ResponseEntity.ok("User Signed in Successfuly!");
		} else {
			return ResponseEntity.ok("Error During SignIn");
		}
	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> getAllProducts() {

		List<Product> productList = productService.getAllProducts();

		return ResponseEntity.ok(productList);
	}

	@PostMapping("/product")
	public ResponseEntity<String> insertProduct(@RequestBody Product product) {
		Product saveProduct = productService.saveProduct(product);
		if (saveProduct != null) {

			return ResponseEntity.ok("New Product Added Successfully!!");
		} else {
			return ResponseEntity.ok("Error Adding Product");
		}

	}

	@GetMapping(value = "/getorder/{orderId}")
	public ResponseEntity<Order> getOrderDetails(@PathVariable int orderId) {

		Order order = orderService.getOrderDetail(orderId);
		return ResponseEntity.ok(order);
	}

	@PostMapping("/placeorder")
	public ResponseEntity<ResponseOrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
		
		ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
		float amount = orderService.getCartAmount(orderDTO.getCartItems());
		Order order = null;
		User customer = new User(orderDTO.getCustomerName(), orderDTO.getCustomerEmail());
		Integer customerIdFromDb = customerService.isCustomerPresent(customer);
		
		if (customerIdFromDb != null) {
			if (!orderDTO.getCartItems().stream().anyMatch(pid -> pid.getQuantity() == 0)) {
				customer.setId(customerIdFromDb);
				logger.info("Customer found with id : " + customerIdFromDb);
				order = new Order(orderDTO.getOrderDescription(), customer.getEmail(), orderDTO.getCartItems());
				order = orderService.saveOrder(order);
				logger.info("Order processing....");
				responseOrderDTO.setAmount(amount);
				responseOrderDTO.setDate(DateUtil.getCurrentDateTime());
				responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
				responseOrderDTO.setOrderId(order.getId());
				responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());
				responseOrderDTO.setStatus("Order Placed!!");
			} else {
				responseOrderDTO.setStatus("OOPS!!.., Currently Product is not available. ");
				return new ResponseEntity<>(responseOrderDTO, HttpStatus.NOT_FOUND);
			}
		} else {
			responseOrderDTO.setStatus("User Not Found " + customer.getEmail() + ". Please Sigin before proceeding");
			return new ResponseEntity<>(responseOrderDTO, HttpStatus.NOT_FOUND);
		}

		logger.info("Order Placed successfully......");

		return ResponseEntity.ok(responseOrderDTO);
	}

	@GetMapping("/cart")
	public ResponseEntity<List<ShoppingCart>> getCartItems() {
		List<ShoppingCart> cartItems = shoppingCartRepository.findAll();
		return ResponseEntity.ok(cartItems);
	}

}
