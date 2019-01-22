import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CategoriesComponent} from "./categories/categories.component";
import {ProductsComponent} from "./products/products.component";
import {LoginComponent} from "./login/login.component";
import {AdminNewProductComponent} from "./admin-new-product/admin-new-product.component";
import {AdminNewCategoryComponent} from "./admin-new-category/admin-new-category.component";
import {AdminNewUserComponent} from "./admin-new-user/admin-new-user.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'products/:idProds', component: ProductsComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'adminNewCategory', component: AdminNewCategoryComponent },
  { path: 'adminNewProduct', component: AdminNewProductComponent },
  { path: 'adminNewUser', component: AdminNewUserComponent },

  // { path: 'register', component: RegistrationComponent},
  //{ path: '', redirectTo: '/products', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
