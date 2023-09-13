<template>
  <section>
    <ProductCheckoutSummary v-if="amountOfCartItems > 0" />
    <EmptyShoppingCartNotice v-else />
    <FeaturedProducts />
  </section>
</template>

<script lang="ts">
import ProductCheckoutSummary from '@/components/products/ProductCheckoutSummary.vue';
import { defineComponent, computed } from 'vue';
import { useShoppingCart } from '@/stores/shoppingCart';
import EmptyShoppingCartNotice from '@/components/EmptyShoppingCartNotice.vue'
import FeaturedProducts from '@/components/products/FeaturedProducts.vue';


export default defineComponent({
  setup() {

    const shoppingCart = useShoppingCart()

    const amountOfCartItems = computed(() => shoppingCart.getTotalItemsCount())

    return { amountOfCartItems }
  },



  components: {
    ProductCheckoutSummary,
    EmptyShoppingCartNotice,
    FeaturedProducts,

  },
})
</script>