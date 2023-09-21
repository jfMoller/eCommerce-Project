import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Product } from '@/types/products'

export const useShoppingCartStore = defineStore('shoppingCart', () => {
  const states = {
    items: ref<Product[]>([]),
    totalItems: ref<number>(0),
    totalPrice: ref<number>(0)
  }

  const methods = {
    addItem: (product: Product): void => {
      states.items.value.push(product)
      states.totalPrice.value += product.price
      states.totalItems.value++
    },

    removeItem: (product: Product): void => {
      const index = states.items.value.indexOf(product)
      states.items.value.splice(index, 1)
      states.totalPrice.value -= product.price
      states.totalItems.value--
    },

    getAllItems: (): Product[] => states.items.value,

    getTotalItemsCount: (): number => states.totalItems.value,

    getTotalPrice: (): string =>
      states.totalPrice.value === 0 ? '0' : states.totalPrice.value.toFixed(2).toString(),

    getItemIds: (): string[] => states.items.value.map((item) => item._id)
  }

  return {
    states,
    methods
  }
})
