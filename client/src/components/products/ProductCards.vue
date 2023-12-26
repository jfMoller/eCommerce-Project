<template>
  <div v-if="products.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
    <div v-for="product in props.products" :key="product.id"
      class="bg-white p-4 shadow-md rounded-lg flex flex-col justify-between">
      <img :src="product.imageUrl" :alt="product.name"
        class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer" @click="() => showProductView(product.id)">
      <h3 class="text-l text-center font-semibold mb-3">{{ product.name }}</h3>
      <div class="flex flex-col justify-between items-center">
        <BuyNowButton :product="product" />
        <span class="text-xl font-semibold text-black">{{ product.price }} :-</span>
      </div>
    </div>
  </div>

  <PlaceholderCards v-else />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import type { Product } from '@/types/product';
import { useShoppingCartStore } from '../../stores/shoppingCartStore'
import PlaceholderCards from './PlaceholderCards.vue';
import router from '@/router';
import BuyNowButton from './BuyNowButton.vue';

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

    function showProductView(productId: string) {
      router.push({ name: 'productView', params: { productId: productId } });
    }

    function addToCart(productId: string) {
      shoppingCartStore.methods.addProductId(productId)
    };

    return {
      props,
      showProductView,
      addToCart
    };
  },
  components: { BuyNowButton, PlaceholderCards }
});
</script>