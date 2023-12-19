export interface Product {
  id: string
  name: string
  description: string
  imageUrl: string
  price: number
  quantity: number
}

export interface UnavailableProduct {
  message: string;
  productId: string;
  requestedAmount: number;
  availableAmount: number;
}
