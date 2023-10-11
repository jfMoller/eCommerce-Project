<template>
  <article class="mt-10">
    <h2 class="text-3xl font-semibold mb-6">Your order</h2>

    <div class="flex flex-col items-center">
      <ProductItems :products="orderProducts" />

      <div class="flex flex-col items-center mb-4">
        <h2 class="text-xl font-bold">Total cost:</h2>
        <p class="text-lg">{{ totalPrice }}</p>
      </div>

      <StyledButton text="Go to payment" additionalClass="rounded-md bg-blue-500 text-white hover:bg-blue-700"
        :handleClick="placeOrder" />
    </div>
  </article>
</template>
    
<script lang="ts">
import { defineComponent, computed } from 'vue';
import ProductItems from './ProductItems.vue';
import StyledButton from '../StyledButton.vue';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import { useOrderStore } from '@/stores/network/orderStore';

export default defineComponent({
  name: "OrderSummary",
  setup() {
    const shoppingCartStore = useShoppingCartStore()
    const orderStore = useOrderStore()

    const orderProducts = computed(() => shoppingCartStore.methods.getAllItems())

    const totalPrice = computed(() => shoppingCartStore.methods.getTotalPrice())


    async function placeOrder() {
      const response = await orderStore.API.placeOrder();
      console.log(response)
     
    }

    return {
      orderProducts, totalPrice, placeOrder
    };
  },
  components: {
    ProductItems, StyledButton
  }

});
</script>@/stores/shoppingCartStore