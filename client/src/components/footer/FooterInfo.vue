<template>
  <footer class="bg-white text-black py-12 custom-box-shadow">
    <div class="container mx-auto px-4">

      <div class="flex justify-between items-center mb-6">

        <h4>eCommerce logo</h4>
        <FooterNavItems />
        <SocialsIcons />

      </div>

      <div class="text-center text-gray-400">
        <p>&copy; 2023 eCommerce Company. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>

<script lang="ts">
import FooterNavItems from './FooterNavItems.vue'
import SocialsIcons from './SocialsIcons.vue'

export default {
  name: "FooterInfo",
  components: { FooterNavItems, SocialsIcons }
}
</script>

<style scoped>
.custom-box-shadow {
  box-shadow: rgba(0, 0, 0, 0.05) 0px 6px 24px 0px, rgba(0, 0, 0, 0.08) 0px 0px 0px 1px;
}
</style>