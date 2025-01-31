package com.wipro.wipromart.service;

import java.util.List;

import com.wipro.wipromart.model.CustomerDTO;

public interface CustomerService {
	
	CustomerDTO saveCustomer(CustomerDTO customer);
	
	CustomerDTO getCustomerById(long customerId);
	
	List<CustomerDTO> getAllCustomers();
	
	CustomerDTO updateCustomer(CustomerDTO customer);
	
	void deleteCustomerById(long customerId);

}
