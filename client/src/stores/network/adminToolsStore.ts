import { defineStore } from 'pinia'
import type { CreateProductDto, EditProductDto, Product } from '@/types/product'
import { callPost, callPut, callDelete } from './requests'

export const useAdminToolsStore = defineStore('adminToolsStore', () => {
  const API = {
    addProduct: async (dto: CreateProductDto): Promise<Product> =>
      await callPost(`/products/add`, dto),

    editProduct: async (productId: string, dto: EditProductDto): Promise<Product> =>
      await callPut(`/products/edit/${productId}`, dto),

    deleteProduct: async (productId: string): Promise<Product> =>
      await callDelete(`/products/delete/${productId}`)
  }

  return {
    API
  }
})
