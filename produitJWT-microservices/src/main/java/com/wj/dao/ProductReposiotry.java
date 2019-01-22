package com.wj.dao;

import com.wj.dao.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductReposiotry extends MongoRepository<Product, String> {
    
}
