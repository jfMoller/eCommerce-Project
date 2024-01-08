<template>
  <section class="px-0 py-0 lg:p-10">
    <div class="bg-white rounded-md sm:flex sm:p-4">
      <div class="flex justify-evenly sm:flex-col sm:justify-start sm:items-center sm:mr-4 sm:space-y-4">
        <button class="border p-2 w-full min-w-max sm:p-4 hover:bg-blue-50 hover:text-blue-600"
          :class="{ 'bg-blue-50 text-blue-600': isOnProductsRoute, 'bg-white': !isOnProductsRoute }">
          <i class="fas fa-cube"></i>
          <router-link :to="{ name: 'HandleProductsView' }" class="text-black font-semibold">
            Products
          </router-link>
        </button>
        <button class="border p-2 w-full min-w-max sm:p-4 hover:bg-blue-50 hover:text-blue-600"
          :class="{ 'bg-blue-50 text-blue-600': isOnOrdersRoute, 'bg-white': !isOnOrdersRoute }">
          <i class="fas fa-truck sm:ml-[-0.6rem]"></i>
          <router-link :to="{ name: 'HandleOrdersView' }" class="text-black ml-1 font-semibold">
            Orders
          </router-link>
        </button>
      </div>
      <RouterView class="w-full border" />
    </div>
  </section>
</template>

<script lang="ts">
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'AdminToolsView',

  setup() {
    const route = useRoute();

    const isOnProductsRoute = ref<boolean>(false)
    const isOnOrdersRoute = ref<boolean>(false)


    function assignHighlightedButton() {
      isOnProductsRoute.value = ['AddProduct', 'EditProduct', 'DeleteProduct'].includes(route.name as string);
      isOnOrdersRoute.value = ['PendingOrders', 'SentOrders', 'AllOrders'].includes(route.name as string);
    }

    onMounted(() => {
      assignHighlightedButton();
    });

    watch(() => route.name, () => {
      assignHighlightedButton();
    });

    return { isOnProductsRoute, isOnOrdersRoute };
  },

  components: {},
};
</script>
