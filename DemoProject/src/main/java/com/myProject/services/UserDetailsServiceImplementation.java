package com.myProject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myProject.dao.OrderRepository;
import com.myProject.dao.ProductRepository;
import com.myProject.dao.UserRepository;
import com.myProject.jwtResponse.MessageResponse;
import com.myProject.model.Order;
import com.myProject.model.Product;
import com.myProject.model.UserInformation;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInformation userInfo = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User does not exists with username: " + username));

		return UserDetailsImplementation.getUserDetails(userInfo);
	}
	
	public ResponseEntity<?> editProduct(Long id,Product productInformation) {

		Optional<Product> productData = productRepository.findById(id);
		Product product = productData.get();

		if (productData.isPresent()) {

			if(product.getName().equals(productInformation.getName()))
			{
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Product name is already taken!"));
			}
			else {
				product.setName(productInformation.getName());
				product.setDescription(productInformation.getDescription());
				product.setImagePath(productInformation.getImagePath());
			return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
			}
		}
		else 
		{
		return ResponseEntity
			.badRequest()
			.body(new MessageResponse("Error: Product not found!"));
		}
	}
	
	public ResponseEntity<?> makeActiveProduct(Product product)
	{
		Optional<Product> productData = productRepository.findById(product.getId());
	    Product productDetail = productData.get();
	    productDetail.setActiveProduct(true);
	    return new ResponseEntity<>(productRepository.save(productDetail), HttpStatus.OK);
	}
	
	public ResponseEntity<?> makeInactiveProduct(Product product)
	{
		Optional<Product> productData = productRepository.findById(product.getId());
	    Product productDetail = productData.get();
	    productDetail.setActiveProduct(false);
	    return new ResponseEntity<>(productRepository.save(productDetail), HttpStatus.OK);
	}
	
	public ResponseEntity<List<Product>> getAllInActiveProduct() {
	    try {
	      List<Product> products = new ArrayList<Product>();

	        productRepository.findInActiveProduct().forEach(products::add);

	      if (products.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(products, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 }
	
	public ResponseEntity<List<Product>> getAllActiveProduct() {
	    try {
	      List<Product> products = new ArrayList<Product>();

	        productRepository.findActiveProduct().forEach(products::add);

	      if (products.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(products, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	public ResponseEntity<?> createProduct(Product productInformation) {
		 if (productRepository.existsByName(productInformation.getName())) 
		 {
			 return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Product is already added!"));
		 }
		 else
		 {
			 productInformation.setActiveProduct(true);
			 productRepository.save(productInformation);
			 return ResponseEntity.ok(new MessageResponse("Product is added successfully!"));
		 } 
	  }
	
	public ResponseEntity<?> makeActive(UserInformation userInformation)
	{
		Optional<UserInformation> userData = userRepository.findByUsername(userInformation.getUsername());
	    UserInformation user = userData.get();
	    user.setActive(true);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}
	
	public ResponseEntity<?> makeInactive(UserInformation userInformation)
	{
		Optional<UserInformation> userData = userRepository.findByUsername(userInformation.getUsername());
	    UserInformation user = userData.get();
	    user.setActive(false);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}
	
	public ResponseEntity<List<UserInformation>> getAllInActiveUser() {
	    try {
	      List<UserInformation> users = new ArrayList<UserInformation>();

	        userRepository.findInActiveUser().forEach(users::add);

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(users, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	public ResponseEntity<List<UserInformation>> getAllUser() {
	    try {
	      List<UserInformation> users = new ArrayList<UserInformation>();

	        userRepository.findAll().forEach(users::add);

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(users, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	public ResponseEntity<List<UserInformation>> getAllActiveUser() {
	    try {
	      List<UserInformation> users = new ArrayList<UserInformation>();

	        userRepository.findActiveUser().forEach(users::add);

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(users, HttpStatus.OK);
	      
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	public ResponseEntity<?> getProductById(Long id){
		Optional<Product> productData = productRepository.findById(id);
	    Product product = productData.get();
	   
	    return new ResponseEntity<>(product, HttpStatus.OK); 
	    
	}
	
	public ResponseEntity<?> makeOrder(Long id,Order orderInformation) {
		
			orderInformation.setUserId(id);
			orderInformation.setName(orderInformation.getName());
			orderInformation.setAddress(orderInformation.getAddress());
			orderInformation.setMobileNumber(orderInformation.getMobileNumber());
			orderInformation.setPincode(orderInformation.getPincode());
			orderInformation.setNumberOfItem(orderInformation.getNumberOfItem());
			orderInformation.setOrderStatus(false);
			orderInformation.setApproved(false);
			orderInformation.setRejected(false);
			orderInformation.setCanceled(false);
			orderRepository.save(orderInformation);
			return ResponseEntity.ok(new MessageResponse("Order is added successfully!"));
		}		
	
	public List<Order> getPendingOrder(Long id){
		
		 List<Order> orders = orderRepository.findPendingOrder(id);
		 return orders;
	}
	
	public List<Order> getApprovedOrder(Long id){
		
		 List<Order> orders = orderRepository.findApprovedOrder(id);
		 return orders;
	}

	public List<Order> getRejectedOrder(Long id){
		
		 List<Order> orders = orderRepository.findRejectedOrder(id);
		 return orders;
	}
	
	public List<Order> getCanceledOrder(Long id){
		
		 List<Order> orders = orderRepository.findCanceledOrder(id);
		 return orders;
	}
	
	public List<Order> getAllPendingOrder(){
		
		 List<Order> orders = orderRepository.findAllOrder();
		 return orders;
	}
	
	public ResponseEntity<?> approveOrder(Order orderInfo)
	{
		Optional<Order> orderData = orderRepository.findById(orderInfo.getId());
	    Order order = orderData.get();
	    order.setOrderStatus(true);
	    order.setApproved(true);
	    return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
	}
	
	public ResponseEntity<?> cancelOrder(Order orderInfo)
	{
		Optional<Order> orderData = orderRepository.findById(orderInfo.getId());
	    Order order = orderData.get();
	    order.setOrderStatus(true);
	    order.setCanceled(true);
	    return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
	}
	
	public ResponseEntity<?> rejectOrder(Order orderInfo)
	{
		Optional<Order> orderData = orderRepository.findById(orderInfo.getId());
	    Order order = orderData.get();
	    order.setOrderStatus(true);
	    order.setRejected(true);
	    return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
	}
	
	public List<Order> getAllOrder(){
		
		 List<Order> orders = orderRepository.findAll();
		 return orders;
	}
	
	public List<Integer> getOrderOfUser(List<UserInformation> userInformation){
		List<Integer> orders = new ArrayList<Integer>();
		
		for(int i=0;i<userInformation.size();i++) {
			int order = orderRepository.findOrderById(userInformation.get(i).getId());
			orders.add(order);
		}
		return orders;
	}
	
	public Order getOrderDetail(Long id) {
		
		Optional<Order> orderData = orderRepository.findById(id);
	    Order orderDetail = orderData.get();
		return orderDetail;
	}
	
	public ResponseEntity<?> editOrder(Long id,Order orderInformation) {
		
		Optional<Order> orderData = orderRepository.findById(id);
	    Order order = orderData.get();
	    order.setAddress(orderInformation.getAddress());
	    order.setMobileNumber(orderInformation.getMobileNumber());
	    order.setPincode(orderInformation.getPincode());
	    order.setNumberOfItem(orderInformation.getNumberOfItem());
		orderRepository.save(order);
		return ResponseEntity.ok(new MessageResponse("Order is edited successfully!"));
	}
	
	public List<Order> getAllApproved(){
		
		 List<Order> orders = orderRepository.findAllApproved();
		 return orders;
	}
	
	public List<Order> getAllRejected(){
		
		 List<Order> orders = orderRepository.findAllRejected();
		 return orders;
	}
	
}
