import type { Product } from './product'

export interface Order {
  _id: string
  price: number
  received: string
  status: string
  expectedDelivery: string | null
  products: { amount: number; product: Product }[]
}
