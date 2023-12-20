import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useShoppingCartStore = defineStore('shoppingCart', () => {
  const states = {
    productIds: ref<string[]>([]),
    productAmount: ref<number>(0)
  }

  const methods = {
    addProductId: async (productId: string): Promise<void> => {
      states.productIds.value.push(productId)
      states.productAmount.value++
    },

    removeProductId: (productId: string): void => {
      const index = states.productIds.value.indexOf(productId)
      states.productIds.value.splice(index, 1)
      states.productAmount.value--
    },

    clearProductIds: (): void => {
      states.productIds.value = []
      states.productAmount.value = 0
    },

    getAllProductIds: (): string[] => states.productIds.value,

    getTotalItemsCount: (): number => states.productAmount.value
  }

  return {
    states,
    methods
  }
})
