<template>
  <article class="mt-10">
    <h2 class="text-3xl font-semibold mb-6">Featured Products</h2>
      <ProductCards :placeholderAmount=4 :products="featuredProducts" />

  </article>
</template>
  
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useProductStore } from '@/stores/network/productStore';
import ProductCards from './ProductCards.vue';
import type { Product } from '@/types/product';

export default defineComponent({
  name: "FeaturedProducts",
  setup() {
    const productStore = useProductStore();
    const featuredProducts = ref<Product[]>([]);


    onMounted(async () => {
      featuredProducts.value = await productStore.API.getFeaturedProducts()
    })
    return {
      featuredProducts,
    };
  },
  components: {
    ProductCards
  }

});
</script>