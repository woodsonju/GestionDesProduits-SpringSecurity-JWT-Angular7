package com.wj;

import com.wj.dao.CategoryRepository;
import com.wj.dao.ProductReposiotry;
import com.wj.dao.entities.Category;
import com.wj.dao.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
public class ProduitJwtMicroservicesApplication {
	
	public static void main(String[] args)
	{
		SpringApplication.run(ProduitJwtMicroservicesApplication.class, args);
	}
	
	/*
		@Bean : Tout ce qui est déclaré bean, ce sont des méthodes qui vont s'éxecuter au démarrage
	 */
	@Bean
	CommandLineRunner start(CategoryRepository categoryRepository, ProductReposiotry productReposiotry) {
		return args -> {


			//Creation de la collection produit dans la base de données
			
			//A chaque démarrage, pour ne pas à chaque fois ajouter les données
			//On les suppriment, après on les insèrent
			 
			categoryRepository.deleteAll();
			
			Stream.of("C1 Ordinateur","C2 Imprimante", "C3 Logiciel").forEach(c -> {
				categoryRepository.save(new Category(c.split(" ")[0], c.split(" ")[1], new ArrayList<>()));
			});
			
			categoryRepository.findAll().forEach(System.out::println);
			
			
			
			
			//Creation de la collection produit dans la base de données
			
			//On les supprime si il existe
			productReposiotry.deleteAll();
			
		
			Category c1 = categoryRepository.findById("C1").get();
			Stream.of("P1", "P2", "P3", "P4").forEach( name -> {
				Product p = productReposiotry.save(new Product(null, name, Math.random()*1000, c1));
				/*
					Afin d'avoir la liste des produits pour chaque catégorie
					Dans JPA c'est un peu différent quand on fait OnToMany avec mappedBy, 
					Hibernate sait déjà que c'est une association bidirectionnelle
				 */
				c1.getProducts().add(p);
				categoryRepository.save(c1); //On met à jour la catégorie c1
			});

			Category c2 = categoryRepository.findById("C2").get();
			Stream.of("P5", "P6").forEach( name -> {
				Product p = productReposiotry.save(new Product(null, name, Math.random()*1000, c2));
				//Afin d'avoir la liste des produits pour chaque catégorie
				c2.getProducts().add(p);
				categoryRepository.save(c2); //On met à jour la catégorie c2
			});

			Category c3 = categoryRepository.findById("C3").get();
			Stream.of("P7", "P8", "P9").forEach( name -> {
				Product p = productReposiotry.save(new Product(null, name, Math.random()*1000, c3));
				//Afin d'avoir la liste des produits pour chaque catégorie
				c3.getProducts().add(p);
				categoryRepository.save(c3); //On met à jour la catégorie c3
			});
			
			productReposiotry.findAll().forEach(p->
					System.out.println(p));
		};
	}
}

