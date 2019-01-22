import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Category} from "../models/category.model";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})

export class CatalogueService {

  private host: string="http://localhost:8082";


  constructor(private http: HttpClient,
              private authService: AuthService) {

  }

  getAllcategories() {
    return this.http.get(this.host + '/categories');
  }

  getRessource(url) {
    return this.http.get(url);
  }

  deleteRessource(url){
  let headers = new HttpHeaders({'Authorization':'Bearer '+ this.authService.jwtToken});
    return this.http.delete(url, {headers:headers});
  }

  postRessource(category){
    let headers = new HttpHeaders({'Authorization':'Bearer '+ this.authService.jwtToken});
    return this.http.post(this.host + '/categories', category, {headers:headers});
  }

  putRessource(url, category) {
    let headers = new HttpHeaders({'Authorization':'Bearer '+ this.authService.jwtToken});
    return this.http.put(url, category, {headers:headers});
  }

  patchRessource(url, category) {
    let headers = new HttpHeaders({'Authorization':'Bearer '+ this.authService.jwtToken});
    return this.http.patch(url, category, {headers:headers});
  }

}
