package com.wipro.wipromart.model;

import java.util.List;

import com.wipro.wipromart.entity.Order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class CustomerDTO {
	
	private long customerId;
	
	@Pattern(regexp = "[A-Za-z]{5,15}", message = "Please provide valid firstName")
	private String firstName;
	
	private String lastName;
	
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@Pattern(regexp = "\\d{10}", message = "Kindly Provide a Valid Mobile number")
	private String mobile;
	
	private String city;
	
	private List<Order> orders;

}
