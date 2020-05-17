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
import org.springframework.web.bind.annotation.RequestBody;

import com.myProject.dao.ProductRepository;
import com.myProject.dao.UserRepository;
import com.myProject.jwtResponse.MessageResponse;
import com.myProject.model.Product;
import com.myProject.model.UserInformation;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;

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
}
