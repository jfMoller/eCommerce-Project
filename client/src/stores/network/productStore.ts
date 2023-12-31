import { defineStore } from 'pinia'
import type { Product } from '@/types/product'
import { callGet } from './requests'

export const useProductStore = defineStore('productStore', () => {
  const API = {
    getAllProducts: async (): Promise<Product[]> => await callGet('/products/all'),

    getProduct: async (productId: string | null): Promise<Product> =>
      await callGet(`/products/${productId}`),

    getSearchedProducts: async (searchInput: string, filter: string): Promise<Product[]> =>
      await callGet(`/products/search?query=${searchInput}&filter=${filter}`),

    getFeaturedProducts: async (): Promise<Product[]> => await callGet('/products/featured')
  }

  return {
    API
  }
})
