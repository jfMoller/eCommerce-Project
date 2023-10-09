<template>
    <RouterLink to="/">
        <div class="flex items-center">
            <div class="text-xl w-10 h-10 font-extrabold bg-orange-500 p-2 rounded-full flex justify-center items-center mr-4">
                <i class="fas fa-coffee text-white"></i>
            </div>
            <p class="text-white text-xl semi-bold custom-font">Java Enthusiast</p>
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
    font-family: 'CustomFont', Cantarell, sans-serif;
}
</style>
