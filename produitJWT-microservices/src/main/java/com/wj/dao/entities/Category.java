package com.wj.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;


/*
    Ici avec mongoDB on initialise la Collection. Avec JPA l'initialisation d'une Collection n'est pas obligatoire
    car par défaut on utilise le mode lazy ou eager qui gère l'initialisation de la liste. 
    Avec mongoDB, on suppose que la liste est vide, car cette information sera enregistrée dans la base de données
    
    Pour faire le Mapping Objet Document on utilise l'annotation @Document
    Un objet Category devient un document JSON.
    Dans une basse de données MongoDB tous les données sont stockés dans des Collections.
    Ces Collections sont un ensemble de Document.
    Les  Document, ce sont des données en formats JSON qui representent  un enregistrement.
    
    @Data: génère l'ensemble des getters et setters.
    
    Comment gerer les relations avec les entités ? 
    Si on laisse sans annotation, à chaque fois qu'il va enregistrer une catégorie; à l'intérieur 
    d'un document catégorie, il va ajouter les produits. Les produit vont être enregistrer  à l'interieur 
    de la catégorie.
    Si on veut créer deux collections, un pour stocker les produits et un autre pour les catégories, il faut utiliser
    l'annotation @DBRef; cela veut dire que dans un document produit je vais enregistrer que l'id des categories. 
    Dans ce cas, quand je charge une catégorie, je peux deja connaitre la liste des produits de cette catégorie 
    
    Ici on ne met pas la methode  toString généré par lombok car elle comprend la liste des produits 
    qui génère un stack over flow , une boucle infini.
    On va plutot le génerer soit même, en enlevant la liste des produits, comme cela ne géra pas la liste
    des produits.
 */
@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Category {

    @Id
    private String id;
    private String name;
    
    @DBRef
    private Collection<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
