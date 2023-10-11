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
import { useProductStore } from '@/stores/network/productStore';
import type { Product } from '@/types/product';

export default defineComponent({
  name: "ShopView",
  setup() {
    const products = ref<Product[] | null>(null);

    onMounted(async () => {
      products.value = await useProductStore().API.getAllProducts();
    });
    return {
      products,
    };
  },
  components: {
    SearchInput, ProductCards
  }

});
</script>@/stores/network/productAPI@/stores/network/productStore@/types/product