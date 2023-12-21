import { defineStore } from 'pinia'
import type { CreateProductDto, EditProductDto, Product } from '@/types/product'
import { callPost, callPut } from './requests'

export const useAdminToolsStore = defineStore('adminToolsStore', () => {
  const API = {
    insertProduct: async (dto: CreateProductDto): Promise<Product> =>
      await callPost(`/products/insert`, dto),

    editProduct: async (productId: string, dto: EditProductDto): Promise<Product> =>
      await callPut(`/products/edit/${productId}`, dto)
  }

  return {
    API
  }
})
