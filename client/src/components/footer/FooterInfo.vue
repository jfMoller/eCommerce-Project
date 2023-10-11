<template>
  <footer class="bg-white text-black py-12 custom-box-shadow border-t">
    <div class="container mx-auto px-4">

      <div class="flex justify-between items-center mb-6">
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