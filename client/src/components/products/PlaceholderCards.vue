<template>
    <div v-if="isLoading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 min-h-[15rem]">
        <div v-for="card in placeholderCards" :key="card.id"
            class="bg-white p-4 shadow-md rounded-lg flex justify-center items-center">
            <LoadingSpinner />
        </div>
    </div>
</template>
  
<script lang="ts">
import { computed, defineComponent, onMounted, ref } from 'vue';
import LoadingSpinner from '../LoadingSpinner.vue';
import { useLoadingStore } from '@/stores/network/loadingStore';

interface PlaceHolderCard {
    id: string;
}

export default defineComponent({
    name: "ProductCards",

    props: {
        placeholderAmount: {
            type: Number,
            required: true
        }
    },

    setup(props) {
        const loadingStore = useLoadingStore();
        const isLoading = computed(() => loadingStore.states.isLoading);
        const placeholderCards = ref<PlaceHolderCard[]>([]);

        function generatePlaceHolderCards() {
            for (let amount = 0; amount < props.placeholderAmount; amount++) {
                placeholderCards.value.push({ id: `${amount}` })
            }
        };

        onMounted(() => {
            generatePlaceHolderCards()

        });

        return {
            isLoading, placeholderCards
        };
    },

    components: { LoadingSpinner }
});
</script>