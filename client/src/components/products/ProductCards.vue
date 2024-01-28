<template>
  <div v-if="products.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-2.5">
    <div v-for="product in props.products" :key="product.id">
      <ProductCard :product="product" />
    </div>
  </div>

  <PlaceholderCards v-else :placeholderAmount=props.placeholderAmount />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import type { Product } from '@/types/product';
import PlaceholderCards from './PlaceholderCards.vue';
import router from '@/router';
import ProductCard from './ProductCard.vue';

export default defineComponent({
  name: "ProductCards",
  props: {
    placeholderAmount: {
      type: Number,
      required: true,
    },
    products: {
      type: Array<Product>,
      required: true,
    }
  },

  setup(props) {

    function showProductView(productId: string) {
      router.push({ name: 'productView', params: { productId: productId } });
    }

    return {
      props,
      showProductView,
    };
  },
  components: { ProductCard, PlaceholderCards }
});
</script>