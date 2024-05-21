<template>
  <article class="flex flex-col justify-center items-center mt-2">
    <div class="bg-gray-200 h-[0.1rem] w-[30rem] my-2"></div>
    <ProductCards :placeholderAmount=4 :products="featuredProducts" class="w-full md:max-w-max mt-2" />
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