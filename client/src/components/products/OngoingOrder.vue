<template>
  <div class="flex flex-col justify-center items-center">
    <h2 class="text-l font-bold text-center sm:text-left uppercase w-full sm:min-w-max sm:w-[70%] py-4">Inspect your cart
    </h2>
    <div class="p-0 sm:p-4 bg-white rounded shadow w-full sm:min-w-max sm:w-[70%] mb-4">
      <LoadingScreen />
      <ul>
        <div v-if="ongoingOrder !== null">
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
                  <div class="px-3 py-3 text-left flex text-l">

                    <p class="text-gray-700">{{ item.amount }}</p>
                  </div>

                  <div class="flex flex-col">
                    <button @click="() => addProduct(item.product.id)" :disabled="item.amount >= item.product.quantity"
                      class="text-left border-l border-b font-bold text-2xl px-2"
                      :class="{ 'bg-gray-100 text-gray-400 cursor-not-allowed': item.amount >= item.product.quantity }">+</button>
                    <button @click="() => removeProduct(item.product.id)"
                      class="text-left border-l font-bold text-2xl px-2">-</button>
                  </div>
                </div>

                <div class="flex justify-end w-full sm:ml-9 max-w-[2rem]">
                  <p class="font-semibold max-w-[4rem]">{{ item.price.toFixed(2) }}:-</p>
                </div>

              </div>

            </li>
          </div>
        </div>
        <div v-else class="bg-white opacity-40 flex justify-center items-center">
          <LoadingSpinner />
        </div>
      </ul>

      <div v-if="ongoingOrder !== null" class="w-full font-bold flex justify-between items-center mt-4 px-4">
        <p>Total price:</p>
        <p>{{ ongoingOrder?.totalPrice }}:-</p>
      </div>
    </div>

    <div v-if="isAuthenticated" class="sm:min-w-max sm:w-[70%] w-full space-y-4">
      <h2 class="text-l font-bold uppercase text-center sm:text-left">2. Fill in your delivery details</h2>
      <div class="p-4 bg-white rounded shadow">
        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">First name</label>
        <input v-model="deliveryDetails.firstName" type="text" id="firstName"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />

        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Last name</label>
        <input v-model="deliveryDetails.lastName" type="text" id="lastName"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />

        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Delivery Address</label>
        <input v-model="deliveryDetails.address" type="text" id="address"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />

        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Post code</label>
        <input v-model="deliveryDetails.postCode" type="text" id="postCode"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />

        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">City</label>
        <input v-model="deliveryDetails.city" type="text" id="city"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />

        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Country code</label>
        <input v-model="deliveryDetails.countryCode" type="text" id="countryCode"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />

        <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Mobile</label>
        <input v-model="deliveryDetails.mobile" type="text" id="mobile"
          class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
      </div>

      <div class="flex justify-between">
        <div class="p-4 bg-white rounded shadow w-full mr-4">
          <h2 class="text-l font-bold mb-6 text-center sm:text-left uppercase">3. Select delivery method</h2>
          <div class="mb-4">
            <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Delivery Method</label>
            <select v-model="selectedDeliveryMethod" id="selectedDeliveryMethod">
              <option v-for="deliveryMethod in deliveryMethods" :key="deliveryMethod" :value="deliveryMethod">
                {{ deliveryMethod }}</option>
            </select>
          </div>
        </div>


        <div class="p-4 bg-white rounded shadow w-full">
          <h2 class="text-l font-bold mb-6 text-center sm:text-left uppercase">4. Select payment method</h2>
          <div class="mb-4">
            <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Payment Method</label>
            <select v-model="selectedPaymentMethod" id="selectedDeliveryMethod">
              <option v-for="paymentMethod in paymentMethods" :key="paymentMethod" :value="paymentMethod">
                {{ paymentMethod }} </option>
            </select>
          </div>
        </div>
      </div>

      <div class="p-4 bg-white rounded shadow">
        <h2 class="text-l font-bold mb-3 text-center sm:text-left uppercase">{{ isAuthenticated ? '5' : '2' }}. Confirm
          your order</h2>
        <div class="w-full flex justify-center items-center">
          <button @click="placeOrder" class="bg-green-500 hover:bg-green-600 px-6 py-2 text-l text-white rounded">Confirm
            order</button>
        </div>
      </div>
    </div>
    <div v-else>

      <h2 class="text-l font-bold my-6 text-center sm:text-left uppercase">2. Login to complete your order</h2>
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
import { computed, defineComponent, onMounted, reactive, ref, watch } from 'vue';
import LoadingScreen from '../LoadingScreen.vue';
import LoadingSpinner from '../LoadingSpinner.vue';
import { useOrderStore } from '@/stores/network/orderStore';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import { useAuthenticationStore } from '@/stores/authenticationStore';
import type { OngoingOrder } from '@/types/order';
import { DeliveryMethod } from '@/types/order';
import { PaymentMethod } from '@/types/order';
import StyledRouterLink from '../StyledRouterLink.vue';

export default defineComponent({
  name: 'OngoingOrder',
  setup() {
    const orderStore = useOrderStore();
    const shoppingCartStore = useShoppingCartStore();
    const ongoingOrder = ref<OngoingOrder | null>(null);
    const isAuthenticated = computed(() => useAuthenticationStore().states.isAuthenticated);
    const deliveryMethods = ref<DeliveryMethod[] | []>()
    const paymentMethods = ref<PaymentMethod[] | []>()

    const deliveryDetails = reactive({
      firstName: 'a',
      lastName: 'b',
      address: 'c',
      postCode: 'd',
      city: 'e',
      countryCode: 'f',
      mobile: 'g'
    })

    const selectedDeliveryMethod = ref<string>('')
    const selectedPaymentMethod = ref<string>('')

    onMounted(() => {
      updateOngoingOrder();
      getDeliveryMethods();
      getPaymentMethods();
    });

    watch(
      () => shoppingCartStore.states.productAmount,
      (newItemsState: number) => {
        if (newItemsState) {
          updateOngoingOrder();
        }
      }
    );

    async function getDeliveryMethods() {
      deliveryMethods.value = await orderStore.API.getAvailableDeliveryMethods();
    }

    async function getPaymentMethods() {
      paymentMethods.value = await orderStore.API.getAvailablePaymentMethods();
    }

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
      await orderStore.API.placeOrder(
        selectedDeliveryMethod.value,
        deliveryDetails.address,
        selectedPaymentMethod.value)
      ongoingOrder.value = null
    }

    return {
      ongoingOrder,
      addProduct,
      removeProduct,
      deliveryMethods,
      selectedDeliveryMethod,
      deliveryDetails,
      paymentMethods,
      selectedPaymentMethod,
      placeOrder,
      isAuthenticated
    };
  },

  components: { LoadingScreen, LoadingSpinner, StyledRouterLink }
});
</script>
