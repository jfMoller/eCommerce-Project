<template>
  <section class="p-4">
    <h2 class="text-2xl font-bold mb-4">{{ generateHeader() }}</h2>
    <table class="w-full border">
      <thead class="text-left min-h-[0.5rem] max-h-[0.5rem]">
        <tr class="bg-gray-200">
          <th class="border p-2">User Email</th>
          <th class="border p-2">Price</th>
          <th class="border p-2">Status</th>
          <th class="border p-2">Received</th>
          <th class="border p-2">Expected Delivery</th>
          <th class="border p-2">Items</th>
        </tr>
      </thead>
      <tbody class="bg-gray-50">
        <tr v-for="order in orders" :key="order.id" class="relative cursor-pointer" :class="{
          'bg-white border-2': order === selectedOrder,
          'hover:bg-white': order !== selectedOrder
        }" @click="() => showUserOrderAside(order)">
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
      <UserOrderAside v-if="selectedOrder" :order="selectedOrder" :onSend="sendOrder" :onUpdate="changeExpectedDelivery"
        :onClose="hideUserOrderAside" />
    </table>
  </section>
</template>


<script lang="ts">
import { defineComponent, onMounted, ref, watch } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import UserOrderAside from '@/components/admintools/UserOrderAside.vue'
import { OrderStatus, orderStatusToString, type UserOrder } from '@/types/order';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'OrdersTable',

  setup() {
    const adminToolsStore = useAdminToolsStore();
    const orders = ref<UserOrder[]>([]);
    const selectedOrder = ref<UserOrder | null>(null);

    async function getAllOrders() {
      orders.value = await adminToolsStore.API.getAllOrders();
    }

    async function getAllOrdersByStatus(status: OrderStatus) {
      orders.value = await adminToolsStore.API.getAllOrdersWithStatus(orderStatusToString(status));
    }

    const route = useRoute();

    function generateHeader(): string {
      if (route.name === "PendingOrders") {
        return 'Pending Orders'
      }

      else if (route.name === "SentOrders") {
        return "Sent Orders"
      }

      else return 'All Orders';
    }


    async function loadOrders() {
      if (route.name === "AllOrders") {
        getAllOrders()
      }

      else if (route.name === "PendingOrders") {
        getAllOrdersByStatus(OrderStatus.PENDING)
      }

      else if (route.name === "SentOrders") {
        getAllOrdersByStatus(OrderStatus.SENT)
      }
    }

    onMounted(() => {
      loadOrders();
    })

    watch(() => route.name, async () => {
      loadOrders();
      hideUserOrderAside();
    })

    function showUserOrderAside(order: UserOrder) {
      selectedOrder.value = order;
    }

    async function hideUserOrderAside() {
      selectedOrder.value = null;
    }

    async function sendOrder(orderId: string, expectedDelivery: string) {
      await adminToolsStore.API.sendOrder(orderId, expectedDelivery);
      loadOrders();
      hideUserOrderAside();
    }

    async function changeExpectedDelivery(orderId: string, newExpectedDelivery: string) {
      await adminToolsStore.API.changeExpectedDelivery(orderId, newExpectedDelivery);
      loadOrders();
      hideUserOrderAside();
    }

    return { generateHeader, orders, OrderStatus, selectedOrder, showUserOrderAside, hideUserOrderAside, sendOrder, changeExpectedDelivery };
  },

  components: { UserOrderAside }
});
</script>
