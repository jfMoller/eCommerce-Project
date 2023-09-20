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
import { useProductAPI } from '@/stores/network/productAPI';
import type { Product } from '@/types/products';

export default defineComponent({
  name: "ShopView",
  setup() {
    const products = ref<Product[] | null>(null);

    onMounted(async () => {
      products.value = await useProductAPI().methods.getAllProducts();
    });
    return {
      products,
    };
  },
  components: {
    SearchInput, ProductCards
  }

});
</script>@/stores/network/productAPI