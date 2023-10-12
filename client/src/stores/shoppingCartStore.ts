import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useShoppingCartStore = defineStore('shoppingCart', () => {
  const states = {
    product_ids: ref<string[]>([]),
    productAmount: ref<number>(0)
  }

  const methods = {
    addProductId: (product_id: string): void => {
      states.product_ids.value.push(product_id)
      states.productAmount.value++
    },

    removeProductId: (product_id: string): void => {
      const index = states.product_ids.value.indexOf(product_id)
      states.product_ids.value.splice(index, 1)
      states.productAmount.value--
    },

    clearProductIds: (): void => {
      states.product_ids.value = []
      states.productAmount.value = 0
    },

    getAllProductIds: (): string[] => states.product_ids.value,

    getTotalItemsCount: (): number => states.productAmount.value
  }

  return {
    states,
    methods
  }
})
