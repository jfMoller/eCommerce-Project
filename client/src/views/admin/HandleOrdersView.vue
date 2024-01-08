<template>
  <section>
    <div class="flex space-x-8 border-b p-2 sm:p-4 justify-center sm:justify-start">
      <router-link :to="{ name: 'PendingOrders' }" class="text-black hover:text-gray-500 font-semibold"
        :class="{ 'text-blue-600': isOnPendingOrdersRoute, 'bg-white': !isOnPendingOrdersRoute }">
        Pending
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'SentOrders' }" class="text-black hover:text-gray-500 font-semibold"
        :class="{ 'text-blue-600': isOnSentOrdersRoute, 'bg-white': !isOnSentOrdersRoute }">
        Sent
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'AllOrders' }" class="text-black hover:text-gray-500 font-semibold"
        :class="{ 'text-blue-600': isOnAllOrdersRoute, 'bg-white': !isOnAllOrdersRoute }">
        All
      </router-link>
    </div>

    <RouterView class="overflow-x-scroll p-4" />
  </section>
</template>

<script lang="ts">
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'HandleOrdersView',

  setup() {
    const route = useRoute();

    const isOnPendingOrdersRoute = ref<boolean>(false)
    const isOnSentOrdersRoute = ref<boolean>(false)
    const isOnAllOrdersRoute = ref<boolean>(false)


    function assignHighlightedButton() {
      isOnPendingOrdersRoute.value = ['PendingOrders'].includes(route.name as string);
      isOnSentOrdersRoute.value = ['SentOrders'].includes(route.name as string);
      isOnAllOrdersRoute.value = ['AllOrders'].includes(route.name as string);
    }

    onMounted(() => {
      assignHighlightedButton();
    });

    watch(() => route.name, () => {
      assignHighlightedButton();
    });

    return { isOnPendingOrdersRoute, isOnSentOrdersRoute, isOnAllOrdersRoute };
  },

  components: {}
};
</script>
