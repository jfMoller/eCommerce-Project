<template>
    <RouterLink :to="path" :class="[
        'text-white',
        'hover:text-gray-400',
        'transition', 'duration-300',
        'ease-in-out',
        additionalClass]">
        <i :class="iconClass"></i>
    </RouterLink>
</template>
    
<script lang="ts">
import { RouterLink } from 'vue-router'

export default {
    name: "IconRouterLink",
    props: {
        iconClass: {
            type: String,
            required: true
        },
        path: {
            type: String,
            required: true
        },
        additionalClass: {
            type: String,
            required: false
        }
    },
    components: {
        RouterLink,
    },
}
</script>