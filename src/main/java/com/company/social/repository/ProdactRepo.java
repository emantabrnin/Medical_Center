package com.company.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.social.entities.ProductEntity;

@Repository
public interface ProdactRepo extends JpaRepository<ProductEntity,Long> {
    public ProductEntity findById(long id);
    public ProductEntity findById(int id);
    public List<ProductEntity> findAllByOrderByIdDesc();
    
}
