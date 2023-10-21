<template>
  <div v-if="products.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
    <div v-for="product in props.products" :key="product._id" class="bg-white p-4 shadow-md rounded-lg">

      <img :src="product.imageUrl" :alt="product.name" class="w-full h-56 object-cover mb-4 rounded-md">
      <h3 class="text-xl font-semibold mb-2">{{ product.name }}</h3>
      <p class="text-gray-600 mb-2">{{ product.description }}</p>
      <div class="flex justify-between items-center">
        <span class="text-xl font-semibold text-blue-600">{{ product.price }}</span>
        <StyledButton text="Add to cart" :handleClick="() => addToCart(product._id)" />
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


    function addToCart(product_id: string) {
      shoppingCartStore.methods.addProductId(product_id)
    };

    return {
      props,
      addToCart
    };
  },
  components: { StyledButton, PlaceholderCards }
});
</script>