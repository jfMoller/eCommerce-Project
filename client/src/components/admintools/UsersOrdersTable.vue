<template>
  <section class="p-4 relative">
    <h2 class="text-2xl font-bold mb-4">All Orders</h2>
    <div class="flex">
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
        <tbody class="bg-gray-100">
          <tr v-for="order in orders" :key="order.id" class="hover:bg-white relative cursor-pointer"
          :class="{ 'bg-white': order === selectedOrder, 'hover:bg-gray-300': order !== selectedOrder }"
            @click="() => showSendOrderAside(order)">
            <td class="border p-2">{{ order.userEmail }}</td>
            <td class="border p-2">{{ order.price }}</td>
            <td class="border p-2">{{ order.status }}</td>
            <td class="border p-2">{{ order.received }}</td>
            <td class="border p-2">{{ order.expectedDelivery || 'N/A' }}
            </td>
            <td class="border p-2">
              <div v-for="item in order.items" :key="item.product.id">
                <div class="py-2">
                  <img :src="item.product.imageUrl" alt="Product Image" class="w-8 h-8 inline-block mr-2" />
                  <span class="font-bold">{{ item.product.name }}</span>
                  <span v-if="item.amount > 1" class="font-semibold text-blue-700"> x {{ item.amount }}</span>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <SendOrderAside v-if="selectedOrder" :order="selectedOrder" :onSend="sendOrder" :onClose="hideSendOrderAside" />
    </div>
  </section>
</template>


<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import SendOrderAside from '@/components/admintools/SendOrderAside.vue'
import type { UserOrder } from '@/types/order';

export default defineComponent({
  name: 'OrdersTable',

  setup() {
    const adminToolsStore = useAdminToolsStore();
    const orders = ref<UserOrder[]>([]);
    const selectedOrder = ref<UserOrder | null>(null);

    async function loadOrders() {
      orders.value = await adminToolsStore.API.getAllOrders();
    }

    onMounted(async () => {
      loadOrders();
    });

    function showSendOrderAside(order: UserOrder) {
      selectedOrder.value = order;
    }

    async function hideSendOrderAside() {
      selectedOrder.value = null;
      loadOrders();
    }

    async function sendOrder(orderId: string, expectedDeliveryDate: string) {
      const response = await adminToolsStore.API.sendOrder(orderId, expectedDeliveryDate);
      console.log(response);
    }

    return { orders, selectedOrder, showSendOrderAside, hideSendOrderAside, sendOrder };
  },

  components: { SendOrderAside }
});
</script>
