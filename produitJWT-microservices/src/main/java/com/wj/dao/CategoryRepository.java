package com.wj.dao;

import com.wj.dao.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
    Utilisation de spring data rest : comme ça toutes les méthodes de l'interface
    MongoRepository sont accessible via notre web service
 */
@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, String> {
}
