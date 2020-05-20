package com.myProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.model.Order;
import com.myProject.services.UserDetailsServiceImplementation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	UserDetailsServiceImplementation userDetailsServiceImp;

	@PostMapping("/makeOrder/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> makeOrder(@PathVariable Long id, @RequestBody Order orderInformation) {
		 
		 return userDetailsServiceImp.makeOrder(id,orderInformation);
	  }
	
	@GetMapping("/getPendingOrder/{id}")
	@PreAuthorize("hasRole('USER')")
	public List<Order> getPendingOrder(@PathVariable Long id){
		
		return userDetailsServiceImp.getPendingOrder(id);
	}
	
	@GetMapping("/getApprovedOrder/{id}")
	@PreAuthorize("hasRole('USER')")
	public List<Order> getApprovedOrder(@PathVariable Long id){
		
		return userDetailsServiceImp.getApprovedOrder(id);
	}
	
	@GetMapping("/getRejectedOrder/{id}")
	@PreAuthorize("hasRole('USER')")
	public List<Order> getRejectedOrder(@PathVariable Long id){
		
		return userDetailsServiceImp.getRejectedOrder(id);
	}
	
	@GetMapping("/getCanceledOrder/{id}")
	@PreAuthorize("hasRole('USER')")
	public List<Order> getCanceledOrder(@PathVariable Long id){
		
		return userDetailsServiceImp.getCanceledOrder(id);
	}
	
	@GetMapping("/getAllPendingOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Order> getAllPendingOrder(){
		
		return userDetailsServiceImp.getAllPendingOrder();
	}
	
	@GetMapping("/getAllOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Order> getAllOrder(){
		
		return userDetailsServiceImp.getAllOrder();
	}
	
	@PutMapping("/approveOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> approveOrder(@RequestBody Order order)
	{
		return userDetailsServiceImp.approveOrder(order);
	}
	
	@PutMapping("/rejectOrder")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> rejectOrder(@RequestBody Order order)
	{
		return userDetailsServiceImp.rejectOrder(order);
	}
	
	@PutMapping("/editOrder/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> editOrder(@PathVariable("id") Long id, @RequestBody Order orderInformation)
	{
		return userDetailsServiceImp.editOrder(id,orderInformation);
	}
	
	
	@GetMapping("/getOrderDetail/{id}")
	@PreAuthorize("hasRole('USER')")
	public Order getOrderDetail(@PathVariable("id") Long id){
		
		return userDetailsServiceImp.getOrderDetail(id);
	}
	
	@PutMapping("/cancelOrder")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> cancelOrder(@RequestBody Order order)
	{
		return userDetailsServiceImp.cancelOrder(order);
	}
}
