<template>
  <section>
    <ProductCard v-if="product" :product="product" :isForProductView='true' />
  </section>
</template>
  
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import type { Product } from '@/types/product';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import { useProductStore } from '@/stores/network/productStore';
import { useRoute } from 'vue-router';
import ProductCard from '@/components/products/ProductCard.vue';

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
    ProductCard
}

});
</script>