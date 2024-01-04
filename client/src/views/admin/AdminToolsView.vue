<template>
  <section>
    <div class="bg-white rounded-md p-5 flex">
      <div class="col-span-2 flex flex-col justify-start items-start space-y-5 min-w-max max-w-max mr-5">
        <button class="border min-w-full p-4 relative hover:bg-blue-50"
          :class="{ 'bg-blue-50': isOnProductsRoute, 'bg-white': !isOnProductsRoute }">
          <div
            :class="{ 'bg-blue-500 h-full w-2 absolute left-0 top-0 z-1': isOnProductsRoute, 'hidden': !isOnProductsRoute }">
          </div>
          <i class="fas fa-cube mr-2"></i>
          <router-link :to="{ name: 'HandleProductsView' }" class="text-black font-semibold">
            Products
          </router-link>
        </button>
        <button class="border min-w-full p-4 relative hover:bg-blue-50"
          :class="{ 'bg-blue-50': isOnOrdersRoute, 'bg-white': !isOnOrdersRoute }">
          <div :class="{ 'bg-blue-500 h-full w-2 absolute left-0 top-0': isOnOrdersRoute, 'hidden': !isOnOrdersRoute }">
          </div>
          <i class="fas fa-truck ml-[-0.6rem]"></i>
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
