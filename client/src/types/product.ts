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

export interface CreateProductDto {
  name: string
  description: string
  imageUrl: string
  price: number
  quantity: number
}

export interface EditProductDto {
  name: string
  description: string
  imageUrl: string
  price: number
  quantity: number
}
