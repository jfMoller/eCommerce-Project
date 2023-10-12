import { defineStore } from 'pinia'
import { callPost, callGet } from './requests'
import { useShoppingCartStore } from '../shoppingCartStore'

export const useOrderStore = defineStore('orderStore', () => {
  const shoppingCartStore = useShoppingCartStore()

  const API = {
    placeOrder: async () => {
      const response = await callPost('/orders/place', {
        product_ids: shoppingCartStore.methods.getAllProductIds()
      })
      if (response.success) {
        shoppingCartStore.methods.clearProductIds()
      }
    },

    getOngoingOrder: async () =>
      await callPost('/orders/ongoing', {
        product_ids: shoppingCartStore.methods.getAllProductIds()
      }),

    getPlacedOrders: async () => await callGet('/orders/all')
  }

  return {
    API
  }
})
