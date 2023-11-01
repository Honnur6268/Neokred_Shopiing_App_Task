package com.shop.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.shop.dto.OrderDTO;
import com.shop.entity.Order;
import com.shop.entity.Product;
import com.shop.entity.ShoppingCart;
import com.shop.entity.User;
import com.shop.repository.ProductRepository;
import com.shop.repository.ShoppingCartRepository;
import com.shop.repository.UserRepository;
import com.shop.restcontroller.ShoppingCartRestController;
import com.shop.service.OrderService;
import com.shop.service.ProductService;
import com.shop.service.UserService;

@WebMvcTest(controllers = ShoppingCartRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ShoppingCartRestControllerTest {

	static User customer;

	static Order order;

	static OrderDTO orderDTO;

	static Product product;

	static ShoppingCart shoppingCart;

	static List<ShoppingCart> shoppingCartItems;

	static List<User> customerList;

	static List<Product> productList;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@MockBean
	private ProductService productService;

	@MockBean
	private UserService customerService;

	@MockBean
	private UserRepository customerRepository;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private ShoppingCartRepository cartRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		customer = new User("TestUser", "test@gmail.com", "test@gmail.com", "testpass", "USER");
		shoppingCartItems = Arrays.asList(new ShoppingCart(1, 1), new ShoppingCart(2, 1), new ShoppingCart(1, 1));
		order = new Order("TestDesc", "test@gmail.com", shoppingCartItems);
		product = new Product("testProduct", 1, 100.00f);
		customerList = Arrays.asList(new User("TestUser1", "test1@gmail.com", "test1@gmail.com", "test1pass", "USER"),
				new User("Test2User", "test2@gmail.com", "test2@gmail.com", "test2pass", "USER"));
		productList = Arrays.asList(new Product("testProduct1", 1, 100.00f), new Product("testProduct2", 1, 100.00f));
		orderDTO = new OrderDTO("Test", shoppingCartItems, "testUser@gmail.com", "testUser", "testUser");
	}

	@Test
	public void testGetCustomers() throws Exception {

		List<User> allCustomers = customerRepository.saveAll(customerList);

		when(customerService.getCustomers()).thenReturn(allCustomers);

		mockMvc.perform(get("/api/shop/customers")).andExpect(status().isOk());

		verify(customerService, times(1)).getCustomers();

		assertEquals(HttpStatus.OK, HttpStatus.OK);
	}

	@Test
	public void testGetProducts() throws Exception {
		List<Product> allProduct = productRepository.saveAll(productList);

		when(productService.getAllProducts()).thenReturn(allProduct);

		mockMvc.perform(get("/api/shop/products")).andExpect(status().isOk());

		verify(productService, times(1)).getAllProducts();

		assertEquals(HttpStatus.OK, HttpStatus.OK);
	}

	@Test
	public void testGetCartItems() throws Exception {
		List<ShoppingCart> allCartItemst = cartRepository.saveAll(shoppingCartItems);

		when(cartRepository.findAll()).thenReturn(allCartItemst);

		mockMvc.perform(get("/api/shop/cart")).andExpect(status().isOk());

		verify(cartRepository, times(1)).findAll();

		assertEquals(HttpStatus.OK, HttpStatus.OK);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		customer = null;
		shoppingCartItems = null;
		order = null;
		product = null;
		customerList = null;
		productList = null;
	}

}
