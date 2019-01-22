import { Component, OnInit } from '@angular/core';
import {CatalogueService} from "../services/catalogue.service";
import {Category} from "../models/category.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: Category[];
  currentCategory: string;

  constructor(private catalogueService: CatalogueService, private router: Router) { }

  ngOnInit() {
    this.catalogueService.getAllcategories().subscribe(
      (data: Category[]) => {
        this.categories = data;
        console.log(data);
    }, error1 => {
        console.log(error1);
      });
  }

  onGetProducts(category) {
    this.currentCategory=category;
    let lien = category._links.products.href;
    this.router.navigateByUrl("/products/"+btoa(lien)); //btoa : base 64 url
   // console.log();
  }


}
