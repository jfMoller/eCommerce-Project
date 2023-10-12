import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Product } from '@/types/product'

export const useShoppingCartStore = defineStore('shoppingCart', () => {
  const states = {
    items: ref<Product[]>([]),
    totalItems: ref<number>(0),
  }

  const methods = {
    addItem: (product: Product): void => {
      states.items.value.push(product)
      states.totalItems.value++
    },

    removeItem: (product: Product): void => {
      const index = states.items.value.indexOf(product)
      states.items.value.splice(index, 1)
      states.totalItems.value--
    },

    getAllItems: (): Product[] => states.items.value,

    getTotalItemsCount: (): number => states.totalItems.value,

    getItemIds: (): string[] => states.items.value.map((item) => item._id)
  }

  return {
    states,
    methods
  }
})
