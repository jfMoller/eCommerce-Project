import { defineStore } from 'pinia'
import type { Product } from '@/types/products'
import { callGet } from './calls'

export const useProductAPI = defineStore('productAPI', () => {
  
  const methods = {
    getAllProducts: async (): Promise<Product[]> => await callGet('/products/all')
  }

  return {
    methods
  }
})
