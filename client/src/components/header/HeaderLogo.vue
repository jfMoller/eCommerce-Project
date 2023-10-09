<template>
    <RouterLink to="/">
        <div class="text-4xl font-extrabold text-white">eCommerce</div>
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