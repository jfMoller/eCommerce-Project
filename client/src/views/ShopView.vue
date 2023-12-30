<template>
  <section>
    <ProductSearchInput @search="handleSearch" />
    <ProductCards :placeholderAmount="10" :products="products" />
  </section>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, watch } from 'vue';
import ProductCards from '@/components/products/ProductCards.vue';
import ProductSearchInput from '@/components/ProductSearchInput.vue';
import { useProductStore } from '@/stores/network/productStore';
import type { Product } from '@/types/product';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: "ShopView",
  setup() {
    const productStore = useProductStore();
    const products = ref<Product[]>([]);

    async function getAllProducts() {
      products.value = await productStore.API.getAllProducts();
    }

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

    const route = useRoute();

    onMounted(async () => {
      handleSearch(route.query.query as string, route.query.filter as string);
    })

    watch(
      () => ({
        query: route.query.query as string,
        filter: route.query.filter as string,
      }),
      (newQuery) => {
        const { query, filter } = newQuery;
        handleSearch(query || '', filter || null);
      },
    );

    return {
      products,
      handleSearch,
    };
  },
  components: {
    ProductSearchInput, ProductCards
  }
});
</script>
