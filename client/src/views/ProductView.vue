<template>
  <section>
    <div v-if="product" class="flex justify-center">
      <div class="bg-white p-4 w-full shadow-md rounded-lg flex">
        <img :src="product.imageUrl" :alt="product.name"
          class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer">
        <div class="flex flex-col justify-start items-start">
          <h3 class="text-l text-center font-semibold mb-3">{{ product.name }}</h3>
          <p>{{ product.description }}</p>
          <span class="text-xl font-semibold text-black">{{ product.price }} :-</span>

          <div class="flex flex-col justify-between items-center">
           <BuyNowButton :product="product" />
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
  
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import type { Product } from '@/types/product';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import { useProductStore } from '@/stores/network/productStore';
import { useRoute } from 'vue-router';
import BuyNowButton from '@/components/products/BuyNowButton.vue';

export default defineComponent({
  name: "ProductView",
  setup() {
    const route = useRoute();
    const shoppingCartStore = useShoppingCartStore();
    const productStore = useProductStore();
    const product = ref<Product | null>(null);

    onMounted(async () => {
      product.value = await productStore.API.getProduct(route.params.productId as string);
    })

    function addToCart(productId: string) {
      shoppingCartStore.methods.addProductId(productId)
    };

    return {
      product, addToCart,
    };
  },
  components: {
    BuyNowButton
}

});
</script>