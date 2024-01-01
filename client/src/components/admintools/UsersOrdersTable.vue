<template>
  <section class="p-4">
    <h2 class="text-2xl font-bold mb-4">Order Details</h2>
    <div class="overflow-x-auto">
      <table class="table-auto w-full border-collapse border">
        <thead>
          <tr class="bg-gray-200">
            <th class="border p-2">User Email</th>
            <th class="border p-2">Price</th>
            <th class="border p-2">Status</th>
            <th class="border p-2">Received</th>
            <th class="border p-2">Expected Delivery</th>
            <th class="border p-2">Items</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id" class="hover:bg-gray-100">
            <td class="border p-2">{{ order.userEmail }}</td>
            <td class="border p-2">{{ order.price }}</td>
            <td class="border p-2">{{ order.status }}</td>
            <td class="border p-2">{{ order.received }}</td>
            <td class="border p-2">{{ order.expectedDelivery || 'N/A' }}</td>
            <td class="border p-2">
                <div v-for="item in order.items" :key="item.product.id">
                  <div class="mb-1 py-2">
                    <img :src="item.product.imageUrl" alt="Product Image" class="w-8 h-8 inline-block mr-2" />
                    <span class="font-bold">{{ item.product.name }}</span>
                    - {{ item.product.price }}
                    <span v-if="item.amount > 1" class="font-semibold text-blue-700"> x {{ item.amount }}</span>
                  </div>
                </div>
              
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import type { OrderStatus, UserOrder } from '@/types/order';

export default defineComponent({
  name: 'OrdersTable',

  setup() {
    const adminToolsStore = useAdminToolsStore();
    const orders = ref<UserOrder[]>([]);

    onMounted(async () => {
      orders.value = await adminToolsStore.API.getAllPlacedOrders();
    });

    async function setExpectedDeliveryDate(orderId: string, date: string) {
      const response = await adminToolsStore.API.setExpectedDeliveryDate(orderId, date);
      console.log(response);
    }

    async function setOrderStatus(orderId: string, status: OrderStatus) {
      const response = await adminToolsStore.API.setOrderStatus(orderId, status);
      console.log(response);
    }

    return { orders, setExpectedDeliveryDate, setOrderStatus };
  },
});
</script>
