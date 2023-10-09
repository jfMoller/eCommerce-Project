<template>
    <i @click="toggleAside" class="fas fa-bars text-black text-xl"></i>
</template>
  
<script lang="ts">

import { defineComponent, type PropType } from 'vue';

export default defineComponent({
    name: "HamburgerIcon",
    props: {
        handleOnClick: {
            type: Function as PropType<(event: MouseEvent) => void>,
            required: true
        }
    },
    setup(props) {
        const toggleAside = props.handleOnClick;
        return { toggleAside }
    },
})
</script>