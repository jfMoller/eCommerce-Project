<template>
    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">My Orders</h2>
        <ul>
            <li v-for="order in orders" :key="order.id">{{ order.name }} - {{ order.total }}</li>
        </ul>
    </div>
</template>
  
<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'ShowOrders',
    setup() {

        //const orders = computed(() => useUserStore().getOrders());
        const orders = [{id: "1", name: "test", total: 10}];
        return { orders }

    }


});



</script>
  