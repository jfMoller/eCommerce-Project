import { defineStore } from 'pinia'
import { callPost, callGet } from './requests'
import { useShoppingCartStore } from '../shoppingCartStore'

export const useOrderStore = defineStore('orderStore', () => {
  const shoppingCartStore = useShoppingCartStore()

  const API = {
    placeOrder: async (deliveryAddress: string, deliveryMethod: string, paymentMethod: string) => {
      const response = await callPost('/orders/place', {
        productIds: shoppingCartStore.methods.getAllProductIds(),
        deliveryAddress: deliveryAddress,
        deliveryMethod: deliveryMethod,
        paymentMethod: paymentMethod
      })
      if (response.success) {
        shoppingCartStore.methods.clearProductIds()
      }
    },

    getOngoingOrder: async () =>
      await callPost('/orders/ongoing', {
        productIds: shoppingCartStore.methods.getAllProductIds()
      }),

    getPlacedOrders: async () => await callGet('/orders/all'),

    getAvailableDeliveryMethods: async () => await callGet('/orders/delivery/methods'),

    getAvailablePaymentMethods: async () => await callGet('/orders/payment/methods')
  }

  return {
    API
  }
})
