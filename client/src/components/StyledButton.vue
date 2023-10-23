<template>
    <button @click="handleClick" :class="[
        'text-black',
        'bg-white',
        'hover:bg-black',
        'hover:text-white',
        'border border-black',
        'py-1 px-2',
        'rounded-md',
        'font-semibold',
        'transition duration-300',
        'ease-in-out',
        additionalClass
    ]">
        {{ text }}</button>
</template>

<script lang="ts">
import type { PropType } from 'vue';
import {defineComponent} from "vue";

export default defineComponent({
    name: "StyledButton",
    props: {
        handleClick: {
            type: Function as PropType<(event: MouseEvent) => void>,
            required: true
        },
        text: {
            type: String,
            required: true,
        },
        additionalClass: {
            type: String,
            required: false,
        }
    }
})
</script>