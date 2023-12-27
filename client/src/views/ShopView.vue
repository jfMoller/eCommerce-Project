<template>
  <section>
    <SearchInput @search="handleSearch" />
    <ProductCards :placeholderAmount="10" :products="products" />
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
    const productStore = useProductStore();
    const products = ref<Product[]>([]);

    async function getAllProducts() {
      products.value = await productStore.API.getAllProducts();
    }
    onMounted(async () => {
      getAllProducts();
    });

    async function getSearchedProducts(searchInput: string) {
      products.value = await productStore.API.getSearchedProducts(searchInput);
    }

    function isEmpty(searchInput: string): boolean {
      return searchInput === '';
    }

    async function handleSearch(searchInput: string) {
      if (isEmpty(searchInput)) getAllProducts()
      else getSearchedProducts(searchInput);
    };

    return {
      products,
      handleSearch,
    };
  },
  components: {
    SearchInput, ProductCards
  }
});
</script>
