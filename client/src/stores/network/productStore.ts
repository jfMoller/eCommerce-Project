import { defineStore } from 'pinia'
import type { Product } from '@/types/products'
import { callGet } from './calls'

export const useProductStore = defineStore('productStore', () => {
  
  const API = {
    getAllProducts: async (): Promise<Product[]> => await callGet('/products/all')
  }

  return {
    API
  }
})
