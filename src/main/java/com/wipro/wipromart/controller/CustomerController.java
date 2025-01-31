package com.wipro.wipromart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.wipromart.model.CustomerDTO;
import com.wipro.wipromart.service.CustomerService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/save")
	public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerDTO customerDTO){
		
		customerDTO = customerService.saveCustomer(customerDTO);
		
		return new ResponseEntity<>(customerDTO,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/get/{customerId}")
	public ResponseEntity<CustomerDTO> fetchCustomerById(@PathVariable long customerId){
		
		CustomerDTO customerDTO = customerService.getCustomerById(customerId);
		
		return new ResponseEntity<>(customerDTO,HttpStatus.OK);
		
	}
	
	@GetMapping("/get/all")
	public List<CustomerDTO> fetchAllCustomer(){
		
		List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
		
		return customerDTOList;
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customer){
		customer = customerService.updateCustomer(customer);
		return new ResponseEntity<>(customer,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<Void> deleteCustomerById(@PathVariable long customerId){
		
		customerService.deleteCustomerById(customerId);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
