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

    async function getSearchedProducts(query: string, filter: any) {
      products.value = await productStore.API.getSearchedProducts(query, filter);
    }

    function isEmpty(query: string): boolean {
      return query === '';
    }

    function hasNoFilter(filter: any): boolean {
      return filter === null;
    }

    async function handleSearch(query: string, filter: any) {
      if (isEmpty(query) && hasNoFilter(filter)) getAllProducts()
      else getSearchedProducts(query, filter);
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
