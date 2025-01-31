package com.wipro.wipromart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.wipromart.entity.Customer;
import com.wipro.wipromart.exception.ResourceNotFoundException;
import com.wipro.wipromart.model.CustomerDTO;
import com.wipro.wipromart.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		
		Customer newCustomer = customerRepository.save(customer);
		
		customerDTO = modelMapper.map(newCustomer, CustomerDTO.class);
		
		return customerDTO;
	}

	@Override
	public CustomerDTO getCustomerById(long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		
		if(optionalCustomer.isEmpty()) {
			
			throw new ResourceNotFoundException("Customer not found with id: "+customerId);
			
		}
		
		Customer customer = optionalCustomer.get();
		
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

		return customerDTO;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		
		List<Customer> customerList = customerRepository.findAll();
		
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		
		for(Customer c : customerList) {
			CustomerDTO customerDTO = modelMapper.map(c, CustomerDTO.class);
			customerDTOList.add(customerDTO);
		}
		
		return customerDTOList;
	}

	@Override
	public CustomerDTO updateCustomer(CustomerDTO customer) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customer.getCustomerId());
		if(optionalCustomer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found with id: "+customer.getCustomerId());	
		}
		Customer customer2 = modelMapper.map(customer, Customer.class);
		customer2 = customerRepository.save(customer2);
		customer = modelMapper.map(customer2, CustomerDTO.class);
		
		return customer;
	}

	@Override
	public void deleteCustomerById(long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if(optionalCustomer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found with id: "+customerId);	
		}
		Customer customer = optionalCustomer.get();
		
		customerRepository.delete(customer);
		
	}

}
