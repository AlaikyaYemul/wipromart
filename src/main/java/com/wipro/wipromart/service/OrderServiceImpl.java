package com.wipro.wipromart.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.wipromart.entity.Customer;
import com.wipro.wipromart.entity.Order;
import com.wipro.wipromart.entity.OrderItem;
import com.wipro.wipromart.entity.Product;
import com.wipro.wipromart.exception.ResourceNotFoundException;
import com.wipro.wipromart.model.CustomerDTO;
import com.wipro.wipromart.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Order saveOrder(Order order) {
		
		long customerId = order.getCustomer().getCustomerId();
		
		//using customerId get the Customer details
		CustomerDTO customerDTO = customerService.getCustomerById(customerId);
		
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		
		List<OrderItem> orderItemList = order.getOrderItems();
		
		double orderTotal = 0;
		
		for(OrderItem oi : orderItemList) {
			int qty = oi.getQuantity();
			
			long productId = oi.getProductId();
			
			// get Product price by productId
			Product product = productService.getProductById(productId);
			
//			double productPrice = product.getProduct().getProductPrice();
			double productPrice = product.getProductPrice();
			
			//calculate item total
			double itemTotal = productPrice * qty;
			
			//update item total in OrderItem
			oi.setItemTotal(itemTotal);
			
//			oi.setProduct(product);
			
			//calculate orderTotal
			orderTotal = orderTotal + itemTotal;
			
			//set this orderItem to the Order object
			oi.setOrder(order);
			
			//save orderItem
			//orderItemRepository.save(oi); // using cascade option we can avoid this
			
		}
		
		//update the order object
		order.setOrderAmount(orderTotal);
		order.setOrderDate(LocalDate.now());
		order.setOrderStatus("Success");
		order.setCustomer(customer);
		order.setOrderItems(orderItemList);
		
		orderRepository.save(order);
		
		return order;
	}

	@Override
	public Order getOrderById(int orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if(optionalOrder.isEmpty()) {
			throw new ResourceNotFoundException("Order not found");
		}
		Order order = optionalOrder.get();
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

}
