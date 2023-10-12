<template>
  <h2 class="text-l font-bold mb-6 text-center uppercase">Your Shopping Cart</h2>
  <div class="p-4 bg-white rounded shadow">
    <ul class="p-4 mb-4">
      <div v-for="productEntry in ongoingOrder?.products" :key="productEntry.product._id">
        <li class="mb-1 py-2 border-b w-full flex justify-between items-center">
          <div class="flex items-center">
            <img :src="productEntry.product.imageUrl" alt="Product Image"
              class="w-[3rem] sm:w-[5rem] inline-block mr-5" />
            <div>
              <span class="font-bold">{{ productEntry.product.name }}</span>
              - {{ productEntry.product.price }}
            </div>
          </div>
          <div class="flex items-center border rounded-md">
            <div class="px-3 py-3 text-center flex text-l">
              <p class="text-gray-700">{{ productEntry.amount }}</p>
            </div>
            <div class="flex flex-col">
              <button @click="() => addProduct(productEntry.product)"
                class="text-center border-l border-b font-bold text-2xl px-2">+</button>
              <button @click="() => removeProduct(productEntry.product)"
                class="text-center border-l font-bold text-2xl px-2">-</button>
            </div>
          </div>
        </li>
      </div>

    </ul>
  </div>
  <div class="bg-white rounded shadow font-bold flex justify-between items-center p-6 mt-4">
    <p>Total price:</p>
    <p>{{ ongoingOrder?.totalPrice }}</p>
  </div>
  <h2 class="text-l font-bold my-6 text-center uppercase">2. Confirm your order</h2>
  <div class="w-full flex justify-center items-center">
    <button @click="placeOrder" class="bg-green-500 hover:bg-green-600 px-6 py-2 text-l text-white rounded">Confirm
      order</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, watch } from 'vue';
import { useOrderStore } from '@/stores/network/orderStore';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import type { OngoingOrder } from '@/types/order';
import type { Product } from '@/types/product';

export default defineComponent({
  name: 'OngoingOrder',
  setup() {
    const orderStore = useOrderStore();
    const shoppingCartStore = useShoppingCartStore();
    const ongoingOrder = ref<OngoingOrder | null>(null);

    onMounted(updateOngoingOrder);

    watch(
      () => shoppingCartStore.states.totalItems,
      (newItemsState: number) => {
        if (newItemsState) {
          updateOngoingOrder();
        }
      }
    );

    async function updateOngoingOrder() {
      ongoingOrder.value = await orderStore.API.getOngoingOrder();
    }

    function addProduct(product: Product) {
      shoppingCartStore.methods.addItem(product);
    }

    function removeProduct(product: Product) {
      shoppingCartStore.methods.removeItem(product);
    }

    function placeOrder() {
      orderStore.API.placeOrder()
    }

    return { ongoingOrder, addProduct, removeProduct, placeOrder };
  }
});
</script>
