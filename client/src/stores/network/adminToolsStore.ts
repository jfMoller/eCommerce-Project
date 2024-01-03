import { defineStore } from 'pinia'
import type { CreateProductDto, EditProductDto, Product } from '@/types/product'
import { callGet, callPost, callPut, callPatch, callDelete } from './requests'
import type { UserOrder } from '@/types/order'

export const useAdminToolsStore = defineStore('adminToolsStore', () => {
  const API = {
    addProduct: async (dto: CreateProductDto): Promise<Product> =>
      await callPost(`/admintools/product/add`, dto),

    editProduct: async (productId: string, dto: EditProductDto): Promise<Product> =>
      await callPut(`/admintools/product/edit/${productId}`, dto),

    deleteProduct: async (productId: string): Promise<Product> =>
      await callDelete(`/admintools/product/delete/${productId}`),

    getAllOrders: async (): Promise<UserOrder[]> => await callGet('/admintools/order/all'),

    sendOrder: async (orderId: string, expectedDeliveryDate: string) =>
      await callPatch(`/admintools/order/send`, { orderId: orderId, expectedDeliveryDate: expectedDeliveryDate }),

  }

  return {
    API
  }
})
