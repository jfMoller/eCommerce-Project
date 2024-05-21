<template>
    <nav class="space-x-6">
        <StyledRouterLink text="Home" path="/"
            additionalClass="text-gray-800 hover:text-gray-300 transition duration-300 ease-in-out" />
        <StyledRouterLink text="Shop" path="/shop"
            additionalClass="text-gray-800 hover:text-gray-300 transition duration-300 ease-in-out" />
    </nav>
</template>
  
<script lang="ts">
import StyledRouterLink from '../StyledRouterLink.vue';

export default {
    name: "FooterNavItems",
    components: {
        StyledRouterLink
    },
}
</script>