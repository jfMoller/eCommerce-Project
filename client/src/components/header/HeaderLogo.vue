<template>
    <RouterLink to="/">
        <div class="flex items-center">
            <img src="../../resources/coffee-cup-logo.jpg" class="max-h-[2rem] mt-[-0.82rem] mr-4" alt="logo">
            <p class="text-gray-600 text-l semi-bold custom-font bold">JAVA ENTHUSIAST</p>
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

<style scoped>
.custom-font {
    font-family: Verdana, Geneva, Tahoma, sans-serif;
}
</style>
