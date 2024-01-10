<template>
    <div class="cursor-pointer relative">
        <i class="fas fa-search" @click="toggleSearchInput" />
    </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
    name: "SearchItem",
    setup(props, { emit }) {
        const isShowingSearchInput = ref(false);

        function toggleSearchInput() {
            isShowingSearchInput.value = !isShowingSearchInput.value
            emit('toggleSearchInput', isShowingSearchInput.value)
        }

        return { isShowingSearchInput, toggleSearchInput }

    },
    components: {},
})
</script>