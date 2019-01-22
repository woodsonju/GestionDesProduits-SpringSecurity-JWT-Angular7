import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { CategoriesComponent } from './categories/categories.component';
import { ProductsComponent } from './products/products.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CatalogueService} from "./services/catalogue.service";
import {AuthService} from "./services/auth.service";
import { LoginComponent } from './login/login.component';
import { AdminNewCategoryComponent } from './admin-new-category/admin-new-category.component';
import { AdminNewProductComponent } from './admin-new-product/admin-new-product.component';
import { AdminNewUserComponent } from './admin-new-user/admin-new-user.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CategoriesComponent,
    ProductsComponent,
    LoginComponent,
    AdminNewCategoryComponent,
    AdminNewProductComponent,
    AdminNewUserComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    CatalogueService,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
