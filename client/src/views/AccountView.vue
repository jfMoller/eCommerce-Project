<template>
  <section>
    <div class="bg-white rounded-md shadow-sm font-semibold space-x-4 p-4 mb-1">
      <div class="flex justify-start items-center space-x-4 text-l">
        <router-link :to="{ name: 'EditAccountView' }" class="text-blue-700 hover:text-blue-500">
          Edit my information
        </router-link>
        <router-link :to="{ name: 'ShowAccountOrdersView' }" class="text-blue-700 hover:text-blue-500">
          Show my orders
        </router-link>
      </div>
    </div>
    <RouterView class="p-4" />
  </section>
</template>

<script lang="ts">
export default {
  name: 'AccountView',
  components: {}
}
</script>
