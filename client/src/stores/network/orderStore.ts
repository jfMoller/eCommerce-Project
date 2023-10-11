import { defineStore } from 'pinia'
import { callPost, callGet } from './requests'
import { useShoppingCartStore } from '../shoppingCartStore'

export const useOrderStore = defineStore('orderStore', () => {
  const shoppingCartStore = useShoppingCartStore()

  const API = {
    placeOrder: async () =>
      await callPost('/orders/place', { product_ids: shoppingCartStore.methods.getItemIds() }),

    getOrders: async () => await callGet('/orders/all')
  }

  return {
    API
  }
})
