package com.gsmart.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;

public interface OrdersRepository extends JpaRepository<Orders, Integer>  ,  JpaSpecificationExecutor<Orders> {
	List<Orders> findAll(Specification<Orders> spec);
	List<Orders> findByRoom(Room room);
}
