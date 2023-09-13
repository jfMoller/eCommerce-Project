<template>
  <header class="bg-gray-900 py-4 px-10 shadow-md sticky top-0 z-999">

    <div class="flex justify-between items-center">
      <RouterLink to="/">
        <div class="text-4xl font-extrabold text-white">eCommerce</div>
      </RouterLink>
      <NavItems />
    </div>

  </header>
</template>

<script lang="ts">
import NavItems from './NavBarItems.vue';

export default {
  name: "NavBar",
  components: {
    NavItems
  },
}
</script>