import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useLoadingStore = defineStore('loadingStore', () => {
  const states = {
    isLoading: ref<boolean>(false)
  }

  const methods = {
    setIsLoading: (newState: boolean) => (states.isLoading.value = newState)
  }

  return {
    states,
    methods
  }
})
