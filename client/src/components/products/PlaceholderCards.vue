<template>
    <div v-if="isLoading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 min-h-[15rem]">
        <div v-for="placeholder in placeholders" :key="placeholder.id"
            class="bg-white p-4 shadow-md rounded-lg flex justify-center items-center">
            <LoadingSpinner />
        </div>
    </div>
</template>
  
<script lang="ts">
import { computed, defineComponent } from 'vue';
import LoadingSpinner from '../LoadingSpinner.vue';
import { useLoadingStore } from '@/stores/network/loadingStore';

export default defineComponent({
    name: "ProductCards",
    setup() {
        const loadingStore = useLoadingStore();
        const isLoading = computed(() => loadingStore.states.isLoading);
        return {
            isLoading, placeholders: [{
                id: '1',
            }, {
                id: '2',
            }, {
                id: '3',
            }, {
                id: '4',
            }
            ]
        };
    },

    components: { LoadingSpinner }
});
</script>