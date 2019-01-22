import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  private host: string = 'http://localhost:8080';
  public jwtToken: string;
  private username: string;
  private roles: string[];

  /*
	Permet l'authentification
	{observe: 'response'} : Permet de récupérer le contenu de la réponse, cela met permet
	de récupérer l'entête Authorization.
	{observe: 'response'} :  Ce n'est plus la peine de me convertir le resultat en format
	JSON; donne moi toute la reponse http.
	Ensuite dans la reponse http, je peux récuperer les entêtes, le contenu
  */
  login(user) {
    return this.http.post(this.host+'/login', user, {observe: 'response'});
  }



  saveToken(jwt: string) {
    localStorage.setItem('token', jwt);  // On stock le Token dans le Local Storage
    this.jwtToken = jwt;  //On le met dans le contexte de l'application
    this.decodeJWT(); //On decode le jwt afin de récuperer les roles et le username
  }


  //installer la librairie @auth0/angular-jwt: npm install @auth0/angular-jwt --save
  decodeJWT() {
    const jwtHelper = new JwtHelperService(); //Decode le jwt
    const objJWT = jwtHelper.decodeToken(this.jwtToken);
    this.roles = objJWT.roles; //Recupère les roles
    this.username = objJWT.username; //Recupère le username
  }

/*
    On recherche dans le tableau si il y a le rôle admin
    si indexOf >= 0. c'est qu'il existe
 */

  isAdmin() {
    return this.roles.indexOf('ADMIN')>=0;
  }

  isUser() {
    return this.roles.indexOf('USER')>=0;
  }

  //Si le role est défini cela veut dire que l'utilisateur est authentifié
  isAuthenticated() {
    return this.roles;
  }

  //On recupère le Token dans le local Storage
  loadToken() {
    this.jwtToken = localStorage.getItem('token');
    this.decodeJWT();
  }

  // On supprime le Token dans le local Storage
  // Il faut aussi renitialier jwtToken
  logout(){
    localStorage.removeItem('token');
    this.initParams();
  }

  initParams(){
    this.jwtToken = null;
    this.username = undefined;
    this.roles = undefined;
  }

}
