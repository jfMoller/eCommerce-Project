<template>
  <header class="bg-gray-900 py-4 px-10 shadow-md sticky top-0 z-999">

    <div class="flex justify-between items-center">
      <RouterLink to="/">
        <div class="text-4xl font-extrabold text-white">eCommerce</div>
      </RouterLink>

      <div @click="toggleAsideVisibility" class="block sm:hidden cursor-pointer">
        <i class="fas fa-bars text-white h-15"></i>
      </div>
      <NavBarItems additionalClass="hidden sm:flex space-x-6 flex items-center text-xl px-4" />
    </div>

  </header>
  <aside v-if="isAsideOpen" class="sm:hidden fixed right-0 w-[20rem] h-screen bg-gray-800 z-99 py-20 text-left">
    <nav class="space-y-6 flex flex-col items-center justify-center text-xl">
      <NavBarItems class="flex flex-col space-y-6 items-center text-xl w-full" />
    </nav>
  </aside>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import NavBarItems from './NavBarItems.vue';

export default defineComponent({
  name: "NavBar",

  setup() {
    const isAsideOpen = ref<boolean>(false);

    function toggleAsideVisibility() {
      isAsideOpen.value = !isAsideOpen.value
      console.log(isAsideOpen.value)
    }

    return { isAsideOpen, toggleAsideVisibility }

  },
  components: {
    NavBarItems
  },
})
</script>