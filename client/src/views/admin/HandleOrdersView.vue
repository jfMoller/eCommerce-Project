<template>
  <section class="p-4">
    <div class="flex space-x-8 mb-4">
      <router-link :to="{ name: 'PendingOrders' }" class="text-blue-400 hover:text-blue-200">
        Pending
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'SentOrders' }" class="text-blue-400 hover:text-blue-200">
        Sent
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'AllOrders' }" class="text-blue-400 hover:text-blue-200">
        All
      </router-link>
    </div>

    <RouterView class="p-4" />
  </section>
</template>

<script lang="ts">
export default {
  name: 'HandleProductsView',
  components: {}
};
</script>
