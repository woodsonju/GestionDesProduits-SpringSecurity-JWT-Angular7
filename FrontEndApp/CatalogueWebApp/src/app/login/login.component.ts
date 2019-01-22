import { Component, OnInit } from '@angular/core';
import {CatalogueService} from "../services/catalogue.service";
import {NgForm} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
  }

  /*
      Récupère le JWT de l'entête Autorization.
      Enregistre le Token dans le local Storage et decodage du Token
      pour récuperer le username et les roles
   */
  onLogin(user: NgForm) {
    this.authService.login(user).subscribe(
      response => {
       console.log(response.headers.get('Authorization'));
        const jwt = response.headers.get('Authorization');
        this.authService.saveToken(jwt);
        this.router.navigateByUrl('/');
      }, error1 => {
        console.log(error1);
      }
    )
  }
}
