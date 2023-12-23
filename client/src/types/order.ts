import type { Product, UnavailableProduct } from './product'

export interface Order {
  id: string
  price: number
  received: string
  status: string
  expectedDelivery: string | null
  items: OrderItem[]
}

export interface OngoingOrder {
  totalPrice: number
  items: OrderItem[]
  unavailableProducts?: UnavailableProduct[]
}

export interface PlacedOrder {
  id: string
  price: number
  status: string
  received: string
  expectedDelivery: string | null
  items: OrderItem[]
}

interface OrderItem {
  id: string
  product: Product
  amount: number
  price: number
}
