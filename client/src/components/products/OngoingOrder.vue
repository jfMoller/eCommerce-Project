<template>
  <div class="flex flex-col justify-center items-center">
  <h2 class="text-l font-bold mb-6 text-center uppercase">Your Shopping Cart</h2>
  <div class="p-4 bg-white rounded shadow relative min-w-max w-[70%]">
    <LoadingScreen />

    <ul>
      <div v-for="item in ongoingOrder?.items" :key="item.product.id">
        <li class="mb-1 py-2 border-b w-full flex justify-between items-center">
          <div class="flex items-center">
            <img :src="item.product.imageUrl" alt="Product Image"
              class="max-w-[4rem] sm:max-w-[5rem] inline-block mr-5" />
            <div>
              <span class="font-bold">{{ item.product.name }}</span>
              - {{ item.product.price }}
            </div>
          </div>
          <div class="flex flex-col items-center sm:flex-row">
            <div class="flex items-center border rounded-md">
              <div class="px-3 py-3 text-center flex text-l">

                <p class="text-gray-700">{{ item.amount }}</p>
              </div>

              <div class="flex flex-col">
                <button @click="() => addProduct(item.product.id)" :disabled="item.amount >= item.product.quantity"
                  class="text-center border-l border-b font-bold text-2xl px-2"
                  :class="{ 'bg-gray-100 text-gray-400 cursor-not-allowed': item.amount >= item.product.quantity }">+</button>
                <button @click="() => removeProduct(item.product.id)"
                  class="text-center border-l font-bold text-2xl px-2">-</button>
              </div>
            </div>

            <div class="flex justify-end w-full sm:ml-9 max-w-[2rem]">
              <p class="font-semibold max-w-[4rem]">{{ item.price }}:-</p>
            </div>

          </div>

        </li>
      </div>

    </ul>
  </div>
  <div class="w-full sm:w-[70%] bg-white rounded shadow font-bold flex justify-between items-center p-4 mt-4">
    <p>Total price:</p>
    <p>{{ ongoingOrder?.totalPrice }}:-</p>
  </div>

  <div v-if="isAuthenticated">
    <h2 class="text-l font-bold my-6 text-center uppercase">2. Confirm your order</h2>
    <div class="w-full flex justify-center items-center">
      <button @click="placeOrder" class="bg-green-500 hover:bg-green-600 px-6 py-2 text-l text-white rounded">Confirm
        order</button>
    </div>
  </div>
  <div v-else>

    <h2 class="text-l font-bold my-6 text-center uppercase">2. Login to complete your order</h2>
    <div class="flex flex-col justify-center">
        <button
          class="w-max-min bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none transition duration-300">
          <StyledRouterLink text="Login" path="/login" additional-class="text-md" />
        </button>
        <div class="flex justify-center items-center mt-4">
          <p class="mr-1 text-sm">New customer? </p>
          <StyledRouterLink text="Start here" path="/signup" textClass="text-blue-700 hover:text-blue-600"
            additional-class="text-blue-700 hover:text-blue-400 text-sm" />
        </div>
      </div>

  </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, ref, watch } from 'vue';
import LoadingScreen from '../LoadingScreen.vue';
import { useOrderStore } from '@/stores/network/orderStore';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import { useAuthenticationStore } from '@/stores/authenticationStore';
import type { OngoingOrder } from '@/types/order';
import StyledRouterLink from '../StyledRouterLink.vue';

export default defineComponent({
  name: 'OngoingOrder',
  setup() {
    const orderStore = useOrderStore();
    const shoppingCartStore = useShoppingCartStore();
    const ongoingOrder = ref<OngoingOrder | null>(null);
    const isAuthenticated = computed(() => useAuthenticationStore().states.isAuthenticated);

    onMounted(updateOngoingOrder);

    watch(
      () => shoppingCartStore.states.productAmount,
      (newItemsState: number) => {
        if (newItemsState) {
          updateOngoingOrder();
        }
      }
    );

    async function updateOngoingOrder() {
      ongoingOrder.value = await orderStore.API.getOngoingOrder();
    }

    function addProduct(productId: string) {
      shoppingCartStore.methods.addProductId(productId);
    }

    function removeProduct(productId: string) {
      shoppingCartStore.methods.removeProductId(productId);
    }

    async function placeOrder() {
      await orderStore.API.placeOrder()
    }

    return { ongoingOrder, addProduct, removeProduct, placeOrder, isAuthenticated };
  },

  components: { LoadingScreen, StyledRouterLink }
});
</script>
