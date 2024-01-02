import { defineStore } from 'pinia'
import type { CreateProductDto, EditProductDto, Product } from '@/types/product'
import { callGet, callPost, callPut, callPatch, callDelete } from './requests'
import type { OrderStatus, UserOrder } from '@/types/order'

export const useAdminToolsStore = defineStore('adminToolsStore', () => {
  const API = {
    addProduct: async (dto: CreateProductDto): Promise<Product> =>
      await callPost(`/products/add`, dto),

    editProduct: async (productId: string, dto: EditProductDto): Promise<Product> =>
      await callPut(`/products/edit/${productId}`, dto),

    deleteProduct: async (productId: string): Promise<Product> =>
      await callDelete(`/products/delete/${productId}`),

    getAllPlacedOrders: async (): Promise<UserOrder[]> => await callGet('/users/orders/all'),

    setExpectedDeliveryDate: async (orderId: string, date: string) =>
      await callPatch(`/orders/delivery`, { orderId: orderId, date: date }),

    setOrderStatus: async (orderId: string, status: OrderStatus) =>
      await callPatch(`/orders/set/status`, { orderId: orderId, status: status })
  }

  return {
    API
  }
})
