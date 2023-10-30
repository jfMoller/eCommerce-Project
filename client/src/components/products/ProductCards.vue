<template>
  <div v-if="products.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
    <div v-for="product in props.products" :key="product._id"
      class="bg-white p-4 shadow-md rounded-lg flex flex-col justify-between">
      <img :src="product.imageUrl" :alt="product.name" class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer"
        @click="() => showProductView(product._id)">
      <h3 class="text-l text-center font-semibold mb-3">{{ product.name }}</h3>
      <div class="flex flex-col justify-between items-center">
        <StyledButton text="Buy now" additionalClass="mb-3" :handleClick="() => addToCart(product._id)" />
        <span class="text-xl font-semibold text-black">{{ product.price }} :-</span>
      </div>

    </div>
  </div>

  <PlaceholderCards />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import StyledButton from '../StyledButton.vue';
import type { Product } from '@/types/product';
import { useShoppingCartStore } from '../../stores/shoppingCartStore'
import PlaceholderCards from './PlaceholderCards.vue';
import router from '@/router';

export default defineComponent({
  name: "ProductCards",
  props: {
    products: {
      type: Array<Product>,
      required: true,
    }
  },

  setup(props) {
    const shoppingCartStore = useShoppingCartStore()

    function showProductView(product_id: string) {
      router.push({ name: 'productView', params: { product_id: product_id } });
    }

    function addToCart(product_id: string) {
      shoppingCartStore.methods.addProductId(product_id)
    };

    return {
      props,
      showProductView,
      addToCart
    };
  },
  components: { StyledButton, PlaceholderCards }
});
</script>