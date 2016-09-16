package com.gsmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsmart.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

}
