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
