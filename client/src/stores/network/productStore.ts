import { defineStore } from 'pinia'
import type { Product } from '@/types/product'
import { callGet } from './requests'

export const useProductStore = defineStore('productStore', () => {
  const API = {
    getAllProducts: async (): Promise<Product[]> => await callGet('/products/all'),

    getProduct: async (product_id: string): Promise<Product> => await callGet(`/products/${product_id}`),
    
    getFeaturedProducts: async (): Promise<Product[]> => await callGet('/products/featured')
  }

  return {
    API
  }
})
