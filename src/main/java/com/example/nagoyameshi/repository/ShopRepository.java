package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer>{
	public Page<Shop> findByNameLike(String keyword, Pageable pageable);
	
	public Page<Shop> findByNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable);    
    public Page<Shop> findByAddressLike(String area, Pageable pageable);
    public Page<Shop> findByBudgetLessThanEqual(Integer budget, Pageable pageable);
    public Page<Shop> findByCategoryId(Integer categoryId, Pageable pageable);
}
