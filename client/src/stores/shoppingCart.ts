import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { Product } from '@/types/products'

export const useShoppingCart = defineStore('shoppingCart', () => {
  const items = ref<Product[]>([])
  const totalItems = ref<number>(0)
  const totalPrice = ref<number>(0)

  function addItem(product: Product): void {
    items.value.push(product)
    totalPrice.value += product.price
    totalItems.value++
  }

  function removeItem(product: Product): void {
    const index = items.value.indexOf(product)
    items.value.splice(index, 1)
    totalPrice.value -= product.price
    totalItems.value--
  }

  function getAllItems(): Product[] {
    return items.value
  }

  function getTotalItemsCount(): number {
    return totalItems.value
  }

  function getTotalPrice(): string {
    return totalPrice.value === 0 ? '0' : totalPrice.value.toFixed(2).toString()
  }

  function getItemIds(): string[] {
    return items.value.map((item) => item._id)
  }

  return {
    items,
    addItem,
    removeItem,
    getAllItems,
    getTotalItemsCount,
    getTotalPrice,
    getItemIds
  }
})
