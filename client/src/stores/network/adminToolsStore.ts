import { defineStore } from 'pinia'
import type { CreateProductDto, Product } from '@/types/product'
import { callPost } from './requests'

export const useAdminToolsStore = defineStore('adminToolsStore', () => {
  const API = {
    insertProduct: async (dto: CreateProductDto): Promise<Product> =>
      await callPost(`/products/insert`, dto)
  }

  return {
    API
  }
})
