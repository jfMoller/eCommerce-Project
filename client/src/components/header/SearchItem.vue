<template>
    <div class="cursor-pointer">
        <i v-if="!isShowingSearchInput" class="fas fa-search" @click="toggleSearchInput" />
        <i v-if="isShowingSearchInput" class="fas fa-close" @click="toggleSearchInput" />
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