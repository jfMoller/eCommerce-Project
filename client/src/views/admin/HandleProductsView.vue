<template>
  <section class="p-4">
    <div class="flex space-x-8 mb-4">
      <router-link :to="{ name: 'AddProduct' }" class="text-blue-400 hover:text-blue-200">
        Create Product
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'EditProduct' }" class="text-blue-400 hover:text-blue-200">
        Edit Product
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'DeleteProduct' }" class="text-blue-400 hover:text-blue-200">
        Delete Product
      </router-link>
    </div>

    <RouterView class="p-4" />
  </section>
</template>

<script lang="ts">
export default {
  name: 'HandleProductsView',
};
</script>
