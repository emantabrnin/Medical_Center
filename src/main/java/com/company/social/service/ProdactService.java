package com.company.social.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.social.entities.ProductEntity;
import com.company.social.repository.ProdactRepo;

@Service
public class ProdactService {
      
    @Autowired
    private ProdactRepo productRepo;

	@Autowired
	private ImageStorageService imageStorageService;

    public List<ProductEntity> showAllProduct(){
        
		List<ProductEntity> products = new ArrayList<ProductEntity>();

		for(ProductEntity product : productRepo.findAllByOrderByIdDesc()) {
			products.add(product);
		}
		
		return products;
	}

    public ProductEntity getProduct(long id) {
		
		return productRepo.findById(id);
	}


    public boolean deleteProduct(Long id)
    {
		try {
			ProductEntity product = productRepo.getById(id);
			imageStorageService.delete(product.getImage());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        productRepo.delete(productRepo.findById(id).orElseThrow());
        return productRepo.findById(id).isEmpty();
    }

    public ProductEntity addProduct(ProductEntity product) {      
		return productRepo.save(product);
	}

    public ProductEntity updateProduct(Long id,ProductEntity product) {
		try {
			return productRepo.save(product);
		} catch (Exception e) {
	
		}
		return null;
	}
    
}
