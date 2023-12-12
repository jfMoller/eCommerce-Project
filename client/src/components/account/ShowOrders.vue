<template>
    <div class="p-4 bg-white rounded shadow">
      <h2 class="text-xl font-semibold mb-4">My Orders</h2>
      <ul>
      
        <li v-for="order in orders" :key="order.received" class="mb-4 p-4 border rounded">
          <div class="text-sm text-gray-500 border-b pb-3">
            Status: {{ order.status }} | Received: {{ order.received }}
          </div>
          <div class="flex justify-between">
            <div>
              <div v-for="detail in order.productDetails" :key="detail.product.id">
                <div class="mb-1 py-2">
                  <img
                    :src="detail.product.imageUrl"
                    alt="Product Image"
                    class="w-8 h-8 inline-block mr-2"
                  />
                  <span class="font-bold">{{ detail.product.name }}</span>
                  - {{ detail.product.price }}
                  <span v-if="detail.amount > 1" class="font-semibold text-blue-700"> x {{ detail.amount }}</span>
                </div>
              </div>
            </div>
            <div class="font-bold">{{ order.price }}</div>
          </div>
        </li>
     
      </ul>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import { useOrderStore } from '@/stores/network/orderStore';
  import type { PlacedOrder } from '@/types/order';
  
  export default defineComponent({
    name: 'ShowOrders',
    setup() {
      const orderStore = useOrderStore();
      const orders = ref<PlacedOrder[]>([]);
  
      onMounted(async () => {
        const response = await orderStore.API.getPlacedOrders();
  console.log(response)
        orders.value = response;
      });
  
      return { orders };
    }
  });
  </script>
  