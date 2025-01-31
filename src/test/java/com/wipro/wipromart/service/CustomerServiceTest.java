package com.wipro.wipromart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.wipromart.entity.Customer;
import com.wipro.wipromart.exception.ResourceNotFoundException;
import com.wipro.wipromart.model.CustomerDTO;
import com.wipro.wipromart.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {
	
	@InjectMocks
	private CustomerService customerService = new CustomerServiceImpl();
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Test
	public void testSaveCustomer() {
		
		Customer customer = new Customer();
		customer.setCustomerId(200);
		customer.setFirstName("abc");
		customer.setLastName("xyz");
		customer.setEmail("abc@tmail.com");
		customer.setMobile("8954462517");
		customer.setCity("city");
		
		when(customerRepository.save(customer)).thenReturn(customer);
		
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		
		CustomerDTO newCustomerDTO = customerService.saveCustomer(customerDTO);
		
		Customer newCustomer = modelMapper.map(newCustomerDTO, Customer.class);
		
		assertEquals(200,newCustomer.getCustomerId());
		assertEquals("abc",newCustomer.getFirstName());
		assertEquals("xyz",newCustomer.getLastName());
		assertEquals("abc@tmail.com",newCustomer.getEmail());
		assertEquals("8954462517",newCustomer.getMobile());
		assertEquals("city",newCustomer.getCity());		
	}

	
	@Test
	public void testSaveCustomerWithException() {
		
		Customer customer = new Customer();
		customer.setCustomerId(200);
		customer.setFirstName("abc");
		customer.setLastName("xyz");
		customer.setEmail("abc@tmail.com");
		customer.setMobile("8954462517");
		customer.setCity("city");
		
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		
		when(customerRepository.save(customer)).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> customerService.saveCustomer(customerDTO));
		
	}
	
	@Test
	public void testGetCustomerById() {
		
		Customer customer = new Customer();
		customer.setCustomerId(200);
		customer.setFirstName("abc");
		customer.setLastName("xyz");
		customer.setEmail("abc@tmail.com");
		customer.setMobile("8954462517");
		customer.setCity("city");
		
		Optional<Customer> optionalCustomer = Optional.of(customer);
		
		when(customerRepository.findById(200L)).thenReturn(optionalCustomer);
		
		CustomerDTO newCustomerDTO = customerService.getCustomerById(200);
		
		Customer newCustomer = modelMapper.map(newCustomerDTO, Customer.class);
		
		assertEquals(200,newCustomer.getCustomerId());
		assertEquals("abc",newCustomer.getFirstName());
		
	}
	
	@Test
	public void testGetCustomerByIdWithException() {
		
		Customer customer = new Customer();
		customer.setCustomerId(200);
		customer.setFirstName("abc");
		customer.setLastName("xyz");
		customer.setEmail("abc@tmail.com");
		customer.setMobile("8954462517");
		customer.setCity("city");
		
		when(customerRepository.findById(200L)).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class,() -> customerService.getCustomerById(200));
		
	}
	
	@Test
	public void testGetAllCustomers() {
		
		List<Customer> myCustomers = new ArrayList<>();
		
		Customer customer = new Customer();
		customer.setCustomerId(200);
		customer.setFirstName("abc");
		customer.setLastName("xyz");
		customer.setEmail("abc@tmail.com");
		customer.setMobile("8954462517");
		customer.setCity("city");
		
		Customer customer1 = new Customer();
		customer1.setCustomerId(300);
		customer1.setFirstName("asd");
		customer1.setLastName("wer");
		customer1.setEmail("asd@tmail.com");
		customer1.setMobile("546258756");
		customer1.setCity("city");
		
		Customer customer2 = new Customer();
		customer2.setCustomerId(400);
		customer2.setFirstName("cvb");
		customer2.setLastName("dfg");
		customer2.setEmail("cvb@tmail.com");
		customer2.setMobile("8574625148");
		customer2.setCity("city");
		
		myCustomers.add(customer);
		myCustomers.add(customer1);
		myCustomers.add(customer2);
		
		when(customerRepository.findAll()).thenReturn(myCustomers);
		
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		
		for(Customer c : myCustomers) {
			CustomerDTO customerDTO = modelMapper.map(c, CustomerDTO.class);
			customerDTOList.add(customerDTO);
		}
		
		
		List<CustomerDTO> customerList = customerService.getAllCustomers();
		
		assertEquals(myCustomers.size(),customerList.size());
	}
	
	@Test
	public void testGetAllCustomersWithException() {
		
		when(customerRepository.findAll()).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class,() -> customerService.getAllCustomers());
	}
	
}
