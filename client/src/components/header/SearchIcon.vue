<template>
    <div class="cursor-pointer relative">
        <i class="fas fa-search" @click="handleOnClick" />
    </div>
</template>
  
<script lang="ts">
import { defineComponent, type PropType } from 'vue';

export default defineComponent({
    name: "SearchIcon",

    props: {
        handleOnClick: {
            type: Function as PropType<(event: MouseEvent) => void>,
            required: true
        },
    },

    setup() {
        return {}
    },
    components: {},
})
</script>