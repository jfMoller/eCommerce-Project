import { defineStore } from 'pinia'
import { callPost, callGet } from './requests'
import { useShoppingCartStore } from '../shoppingCartStore'

export const useOrderStore = defineStore('orderStore', () => {
  const shoppingCartStore = useShoppingCartStore()

  const API = {
    placeOrder: async () => {
      const response = await callPost('/orders/place', {
        productIds: shoppingCartStore.methods.getAllProductIds()
      })
      if (response.success) {
        shoppingCartStore.methods.clearProductIds()
      }
    },

    getOngoingOrder: async () =>
      await callPost('/orders/ongoing', {
        productIds: shoppingCartStore.methods.getAllProductIds()
      }),

    getPlacedOrders: async () => await callGet('/orders/all')
  }

  return {
    API
  }
})
