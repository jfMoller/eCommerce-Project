<template>
    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">My Orders</h2>
        <ul>
            <li v-for="order in orders" :key="order._id">{{ order.products }} - {{ order.price }}</li>
        </ul>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useOrderStore } from '@/stores/network/orderStore';
import type { Order } from '@/types/order';

export default defineComponent({
    name: 'ShowOrders',
    setup() {
        const orderStore = useOrderStore()
        const orders = ref<Order[]>([])

        onMounted(async () => {
            orders.value = await orderStore.API.getOrders()
        });

        return { orders }
    }


});



</script>
  