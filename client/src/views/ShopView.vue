<template>
  <section>
    <SearchInput :options="products" />
    <ProductCards v-if="products" :products="products" />
    <div v-else>Loading</div>
  </section>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import ProductCards from '@/components/products/ProductCards.vue';
import SearchInput from '@/components/SearchInput.vue';
import { useAPI } from '@/stores/network/API';
import type { Product } from '@/types/products';

export default defineComponent({
  name: "ShopView",
  setup() {
    const API = useAPI();
    const products = ref<Product[] | null>(null);

    onMounted(async () => {
      products.value = await API.getAllProducts();
    });
    return {
      products,
    };
  },
  components: {
    SearchInput, ProductCards
  }

});
</script>