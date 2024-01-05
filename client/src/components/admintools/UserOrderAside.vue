<template>
    <div v-if="props.order" class="min-w-[30rem] bg-white border-l fixed h-screen top-[5.79rem] right-0 p-5">

        <div class="flex justify-between">
            <h2 class="text-lg font-bold">{{
                props.order?.status === "PENDING" ? "SEND ORDER"
                : props.order?.status === "SENT" ? "UPDATE ORDER"
                    : '' }}</h2>
            <button class="font-semibold px-3 rounded-md border" @click="props.onClose">X</button>
        </div>
        <div class="border-t mt-4"></div>

        <div>
            <div class="space-y-4 my-4">
                <h4><span class="font-bold">User Email:</span> {{ props.order.userEmail }}</h4>
                <h4><span class="font-bold">Price:</span> {{ props.order.price }}</h4>
                <h4><span class="font-bold">Status:</span> {{ props.order.status }}</h4>
                <div><span class="font-bold">Received:</span>

                    <div class="flex items-center justify-center mt-2">
                        <input type="date" disabled v-model="receivedDate" class="border p-2 w-full focus:outline-none" />
                        <input type="time" disabled v-model="receivedTime" class="border p-2 w-full focus:outline-none" />
                    </div>
                </div>
                <div><span class="font-bold">Expected Delivery:</span>

                    <div class="flex items-center justify-center mt-2">
                        <input type="date" v-model="selectedDeliveryDate" class="border p-2 w-full focus:outline-none" />
                        <input type="time" v-model="selectedDeliveryTime" class="border p-2 w-full focus:outline-none" />
                    </div>
                </div>
            </div>
            <button v-if="props.order?.status === 'PENDING'" @click="sendOrder"
                class="bg-blue-500 text-white px-4 py-2 rounded w-full">
                Send
            </button>
            <button v-if="props.order?.status === 'SENT'" @click="changeExpectedDelivery"
                class="bg-blue-500 text-white px-4 py-2 rounded w-full">
                Change
            </button>
        </div>

    </div>
</template>
  
<script lang="ts">
import { defineComponent, onMounted, ref, watch } from 'vue';
import type { UserOrder } from '@/types/order';

export default defineComponent({
    name: 'SendOrderAside',
    props: {
        order: {
            type: Object as () => UserOrder | null,
            required: true,
        },
        onSend: {
            type: Function,
            required: true,
        },

        onUpdate: {
            type: Function,
            required: true,
        },

        onClose: {
            type: Function,
            required: true,
        },
    },
    setup(props) {
        const receivedDate = ref('');
        const receivedTime = ref('');

        function parseReceived() {
            if (props.order?.received) {
                const received = props.order.received;
                receivedDate.value = received.split('T')[0];
                receivedTime.value = received.split('T')[1].slice(0, 5);
            }
        }

        const selectedDeliveryDate = ref('');
        const selectedDeliveryTime = ref('');

        function parseExpectedDelivery() {
            if (props.order?.expectedDelivery) {
                const expectedDelivery = props.order.expectedDelivery;
                selectedDeliveryDate.value = expectedDelivery.split('T')[0];
                selectedDeliveryTime.value = expectedDelivery.split('T')[1].slice(0, 5);
            } else {
                selectedDeliveryDate.value = '';
                selectedDeliveryTime.value = '';
            }
        }

        onMounted(() => {
            parseReceived();
            parseExpectedDelivery();
        });

        watch(() => props.order, () => {
            parseReceived();
            parseExpectedDelivery();
        });

        async function sendOrder() {
            props.onSend(props.order?.id, `${selectedDeliveryDate.value}T${selectedDeliveryTime.value}:00`)
        }
        async function changeExpectedDelivery() {
            props.onUpdate(props.order?.id, `${selectedDeliveryDate.value}T${selectedDeliveryTime.value}:00`)
        }

        return { receivedDate, receivedTime, selectedDeliveryDate, selectedDeliveryTime, sendOrder, changeExpectedDelivery, props };
    },
});
</script>
  