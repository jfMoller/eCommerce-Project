<template>
    <RouterLink to="/">
        <div class="flex items-center justify-center min-w-max">
            <img src="../../resources/coffee-cup-logo.jpg" class="max-h-[1.7rem] mt-[-0.75rem] mr-4" alt="logo">
            <p class="text-gray-800 text-l semi-bold custom-font font-bold">JAVA ENTHUSIAST</p>
        </div>
    </RouterLink>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { RouterLink } from 'vue-router';

export default defineComponent({
    name: "HeaderLogo",
    setup() {

    },
    components: {
        RouterLink
    },
})
</script>
