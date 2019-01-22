import { Component, OnInit } from '@angular/core';
import {CatalogueService} from "../services/catalogue.service";
import {Category} from "../models/category.model";

@Component({
  selector: 'app-admin-new-category',
  templateUrl: './admin-new-category.component.html',
  styleUrls: ['./admin-new-category.component.css']
})
export class AdminNewCategoryComponent implements OnInit {

  categories: Category[];
  mode= 'list';
  currentCategory;

  constructor(private catalogueService: CatalogueService) { }

  ngOnInit() {
    this.onGetAllCategories()
  }


  onGetAllCategories() {
    this.catalogueService.getAllcategories().subscribe(
      (data: Category[]) => {
        this.categories = data;
      }, error1 => {
        console.log(error1);
      });
  }
  onDeleteCategory(category) {
    const c = confirm("Êtes vous sûre de vouloir supprimer ?");
    if(!c)  return;
    const lien = category._links.self.href;  //Permet d'acceder à un Categorie
    this.catalogueService.deleteRessource(lien).subscribe(
      data => {
        this.mode = 'list'; //je suis dans le mode list
        this.onGetAllCategories(); //On recharge les catégories après suppression
        console.log(data);
      }, error1 => {
        console.log(error1);
      });
  }

  onNewCategory() {
    this.mode = 'newCategory';
  }

  onSaveCategory(cat) {
   // console.log(cat);
    this.catalogueService.postRessource(cat).subscribe(
      (data: Category) => {
        this.mode = 'list';
        this.onGetAllCategories(); //On recharge la liste des catégories
        console.log(data);
      }, error1 => {
        console.log(error1);
      });
  }

  onEditCategory(cat) {
    const lien = cat._links.self.href;
    this.catalogueService.getRessource(lien).subscribe(
      (data: Category) => {
        this.currentCategory = data;
       // console.log(data);
        this.mode='editCategory';
      }, error1 => {
        console.log(error1);
      });
  }

  onUpdateCategory(cat) {
    const lien = this.currentCategory._links.self.href;
    this.catalogueService.putRessource(lien, cat).subscribe(
      (data: Category) => {
        this.mode = 'list';
        this.onGetAllCategories(); //On recharge la liste des catégories
        console.log(data);
      }, error1 => {
        console.log(error1);
      });
  }

}

/*
    category._links.self.href;  => Permet d'acceder à un Categorie
    c'est pareil que d'écrire 'http://localhost:8080/contacts/update/'+ contact.id
    Ou http://localhost:8080/contacts/update/2    avec id=2
 */
