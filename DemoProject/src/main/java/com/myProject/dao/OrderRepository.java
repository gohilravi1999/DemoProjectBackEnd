package com.myProject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myProject.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("SELECT o FROM Order o WHERE o.orderStatus = 0 AND o.userId=:id")
	public List<Order> findPendingOrder(@Param("id") long id);
	
	@Query("SELECT o FROM Order o WHERE o.orderStatus = 0")
	public List<Order> findAllOrder();
	
	@Query("SELECT o FROM Order o WHERE o.isApproved = 1")
	public List<Order> findAllApproved();
	
	@Query("SELECT o FROM Order o WHERE o.isRejected = 1")
	public List<Order> findAllRejected();
	
	@Query("SELECT o FROM Order o WHERE o.isApproved = 1 AND o.userId=:id")
	public List<Order> findApprovedOrder(@Param("id") long id);
	
	@Query("SELECT o FROM Order o WHERE o.isRejected = 1 AND o.userId=:id")
	public List<Order> findRejectedOrder(@Param("id") long id);
	
	@Query("SELECT o FROM Order o WHERE o.isCanceled = 1 AND o.userId=:id")
	public List<Order> findCanceledOrder(@Param("id") long id);
}
