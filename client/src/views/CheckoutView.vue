<template>
  <section>
    <OrderSummary v-if="amountOfCartItems > 0" />
    <EmptyShoppingCartNotice v-else />
    <FeaturedProducts />
  </section>
</template>

<script lang="ts">
import OrderSummary from '@/components/products/OrderSummary.vue';
import { defineComponent, computed } from 'vue';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import EmptyShoppingCartNotice from '@/components/EmptyShoppingCartNotice.vue'
import FeaturedProducts from '@/components/products/FeaturedProducts.vue';


export default defineComponent({
  setup() {
    const shoppingCartStore = useShoppingCartStore();

    const amountOfCartItems = computed(() => shoppingCartStore.methods.getTotalItemsCount())

    return { amountOfCartItems }
  },

  



  components: {
    OrderSummary,
    EmptyShoppingCartNotice,
    FeaturedProducts,

  },
})
</script>