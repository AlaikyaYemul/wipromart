package com.wipro.wipromart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wipro.wipromart.entity.Product;
import com.wipro.wipromart.service.ProductService;


@ExtendWith(MockitoExtension.class) public class ProductControllerTest {

	@InjectMocks ProductController productController;

	@Mock ProductService productService;

	@Test 
	public void testAddProduct() throws Exception{

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new
				ServletRequestAttributes(request));

		Product product = new Product(200L,"abc",5000,LocalDate.now(),"category");

		when(productService.saveProduct(any(Product.class))).thenReturn(product);

		ResponseEntity<Product> newProduct = productController.addProduct(product);

		assertThat(newProduct.getStatusCodeValue()).isEqualTo(201); // product is added 
		assertThat(newProduct.getBody().getProductId()).isEqualTo(200L);

//		assertThat(newProduct.getHeaders().getLocation().getPath()).isEqualTo("/200"); // in case the function returns uri object

	}

	@Test public void testFetchAllProducts() {

		Product product = new Product(200L,"abc",5000,LocalDate.now(),"category");
		Product product1 = new Product(300L,"xyz",7000,LocalDate.now(),"category");

		List<Product> myProducts = new ArrayList<>();

		myProducts.add(product); myProducts.add(product1);

		when(productService.getAllProducts()).thenReturn(myProducts);

		List<Product> productList = productController.fetchAllProducts();

		assertThat(productList.size()).isEqualTo(myProducts.size());
		assertThat(productList.get(0).getProductId()).isEqualTo(myProducts.get(0).
				getProductId());
		assertThat(productList.get(1).getProductId()).isEqualTo(myProducts.get(1).
				getProductId());

	}

}


/*
 * @WebMvcTest(ProductController.class) public class ProductControllerTest{
 * 
 * @Autowired private MockMvc mvc;
 * 
 * @Test public void testFetchAllProducts() throws Exception {
 * 
 * mvc.perform(MockMvcRequestBuilders.get("/product/get/all").accept(MediaType.
 * APPLICATION_JSON)) .andDo(print()) .andExpect(status().isOk())
 * .andExpect(MockMvcResultMatchers.jsonPath("$.product").exists())
 * .andExpect(MockMvcResultMatchers.jsonPath("$.product[*].productId").
 * isNotEmpty());
 * 
 * }
 * 
 * }
 */