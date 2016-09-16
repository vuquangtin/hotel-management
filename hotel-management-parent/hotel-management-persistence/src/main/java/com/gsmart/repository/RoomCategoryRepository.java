package com.gsmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gsmart.model.RoomCategory;

@Repository
public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Integer> {
}
