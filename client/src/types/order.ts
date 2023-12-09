import type { Product } from './product'

export interface Order {
  id: string
  price: number
  received: string
  status: string
  expectedDelivery: string | null
  products: { amount: number; product: Product }[]
}

export interface OngoingOrder {
  totalPrice: number
  products: ProductGrouping[]
}

interface ProductGrouping {
  amount: number
  groupPrice: number
  product: Product
}

export interface PlacedOrder {
  id: string;
  user: string;
  products: {
    amount: number;
    product: {
      id: string;
      name: string;
      description: string;
      imageUrl: string;
      price: number;
      quantity: number;
    };
    groupPrice: number;
  }[];
  price: number;
  status: string;
  received: string;
  expectedDelivery: string | null;
}
