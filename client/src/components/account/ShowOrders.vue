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
            <div v-for="item in order.items" :key="item.product.id">
              <div class="mb-1 py-2">
                <img :src="item.product.imageUrl" alt="Product Image" class="w-8 h-8 inline-block mr-2" />
                <span class="font-bold">{{ item.product.name }}</span>
                - {{ item.product.price }}
                <span v-if="item.amount > 1" class="font-semibold text-blue-700"> x {{ item.amount }}</span>
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
      orders.value = response;
    });

    return { orders };
  }
});
</script>
  