import router from '.'

const navigationProvider = {
  navigate: (name: string) => router.push({ name }),

  navigateOnCondition: (condition: boolean, name1: string, name2: string) => {
    if (condition) {
      router.push({ name: name1 })
    } else {
      router.push({ name: name2 })
    }
  }
}

export default navigationProvider
