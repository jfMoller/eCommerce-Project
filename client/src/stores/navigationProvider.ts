import { defineStore } from 'pinia'
import router from '@/router'

export const useNavigationProvider = defineStore('navigationProvider', () => {
  function navigate(name: string) {
    router.push({ name: name })
  }

  function conditionalNavigate(condition: boolean, name1: string, name2: string) {
    if (condition) {
      router.push({ name: name1 })
    } else {
      router.push({ name: name2 })
    }
  }

  return {
    navigate,
    conditionalNavigate
  }
})
