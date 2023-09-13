import { defineStore } from 'pinia'
import type { Product } from '@/types/products'
import { callGet } from './calls'

export const useAPI = defineStore('API', () => {
  
  async function getAllProducts(): Promise<Product[]> {
    const response = await callGet('/products/all')
    return response
  }

  return {
    getAllProducts
  }
})
