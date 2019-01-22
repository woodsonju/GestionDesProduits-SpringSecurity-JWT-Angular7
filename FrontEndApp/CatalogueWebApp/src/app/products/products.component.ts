import { Component, OnInit } from '@angular/core';
import {CatalogueService} from "../services/catalogue.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {Product} from "../models/product.model";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products;

  constructor(private catalogueService: CatalogueService,
              private route: ActivatedRoute,
              private router: Router) {
    router.events.subscribe(event => {
      if(event instanceof NavigationEnd){
        //console.log(this.route.snapshot.params['urlProds']);
        let urlProds = atob(this.route.snapshot.params['idProds']);  //On decode idProds
        //console.log(urlProds);
        this.getProducts(urlProds);
      }
    });

  }

  ngOnInit() {
  }

  getProducts(urlProds) {
      this.catalogueService.getRessource(urlProds).subscribe(
        (data: Product) => {
          this.products = data;
          console.log(data);
        }, error1 => {
          console.log(error1);
        });
  }


}

/*
  ActivatedRoute: C'est un service qui permet d'injecter la route actuellement activée
 */
