import {Product} from "./product.model";

export class Category {
  id: number;
  name: string;
  products: Array<Product>;
}
