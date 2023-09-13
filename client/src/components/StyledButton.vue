<template>
    <button @click="handleClick" :class="[
        'text-white',
        'bg-blue-600',
        'hover:bg-blue-700',
        'py-2 px-4',
        'rounded-full',
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