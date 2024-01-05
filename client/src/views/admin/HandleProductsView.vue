<template>
  <section>
    <div class="flex space-x-8 border-b p-4">
      <router-link :to="{ name: 'AddProduct' }" class="text-black hover:text-gray-500 font-semibold"
        :class="{ 'text-blue-600': isOnAddProductRoute, 'bg-white': !isOnAddProductRoute }">
        Add
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'EditProduct' }" class="text-black hover:text-gray-500 font-semibold"
        :class="{ 'text-blue-600': isOnEditProductRoute, 'bg-white': !isOnEditProductRoute }">
        Edit
      </router-link>
      <p class="text-gray-400">|</p>
      <router-link :to="{ name: 'DeleteProduct' }" class="text-black hover:text-gray-500 font-semibold"
        :class="{ 'text-blue-600': isOnDeleteProductRoute, 'bg-white': !isOnDeleteProductRoute }">
        Delete
      </router-link>
    </div>

    <RouterView class="p-4" />
  </section>
</template>

<script lang="ts">
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'HandleProductsView',

  setup() {
    const route = useRoute();

    const isOnAddProductRoute = ref<boolean>(false)
    const isOnEditProductRoute = ref<boolean>(false)
    const isOnDeleteProductRoute = ref<boolean>(false)


    function assignHighlightedButton() {
      isOnAddProductRoute.value = ['AddProduct'].includes(route.name as string);
      isOnEditProductRoute.value = ['EditProduct'].includes(route.name as string);
      isOnDeleteProductRoute.value = ['DeleteProduct'].includes(route.name as string);
    }

    onMounted(() => {
      assignHighlightedButton();
    });

    watch(() => route.name, () => {
      assignHighlightedButton();
    });

    return { isOnAddProductRoute, isOnEditProductRoute, isOnDeleteProductRoute };
  },

  components: {}
};
</script>
