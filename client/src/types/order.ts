import type { Product, UnavailableProduct } from './product'

interface OrderItem {
  id: string
  product: Product
  amount: number
  price: number
}

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

export interface UserOrder {
  id: string
  userEmail: string
  price: number
  status: string
  received: string
  expectedDelivery: string | null
  items: OrderItem[]
}

export enum OrderStatus {
  PENDING,
  SHIPPED,
  DELIVERED
}
