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
  SENT
}

export function orderStatusToString(status: OrderStatus): string {
  switch (status) {
    case OrderStatus.PENDING:
      return 'PENDING'
    case OrderStatus.SENT:
      return 'SENT'
    default:
      return 'INVALID_STATUS'
  }
}

export enum DeliveryMethod {
  HOME_DELIVERY
}

export function deliveryMethodToString(method: DeliveryMethod): string {
  switch (method) {
    case DeliveryMethod.HOME_DELIVERY:
      return 'HOME_DELIVERY'
    default:
      return 'INVALID_DELIVERY_METHOD'
  }
}

export enum PaymentMethod {
  PAY_ON_DELIVERY
}

export function paymentMethodToString(method: PaymentMethod): string {
  switch (method) {
    case PaymentMethod.PAY_ON_DELIVERY:
      return 'PAY_ON_DELIVERY'
    default:
      return 'INVALID_PAYMENT_METHOD'
  }
}
