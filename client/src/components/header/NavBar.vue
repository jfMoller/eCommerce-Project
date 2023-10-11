<template>
  <header class="bg-white py-8 px-10 sticky top-0 z-999 border-b-4 border-white custom-box-shadow">

    <div class="flex justify-between items-center">
      <HeaderLogo />
      <!--     Desktop view -->
      <NavBarItems additionalClass="hidden sm:flex space-x-6 flex items-center" />

      <!-- Phone view -->
      <div class="sm:hidden cursor-pointer space-x-6 text-l px-4 flex justify-center items-center">
        <HamburgerIcon :handleOnClick="toggleAsideVisibility" />
        <ShoppingCartItem />
      </div>

    </div>
  </header>
  <HamburgerDropdown :isOpen="isAsideOpen" :onClose="closeAside" :onClickOutside="handleClickOutsideAside" />
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import HeaderLogo from './HeaderLogo.vue'
import HamburgerIcon from './HamburgerIcon.vue'
import NavBarItems from './NavBarItems.vue';
import ShoppingCartItem from './ShoppingCartItem.vue';
import HamburgerDropdown from './HamburgerDropdown.vue';

export default defineComponent({
  name: "NavBar",

  setup() {
    const isAsideOpen = ref<boolean>(false);

    function toggleAsideVisibility() {
      isAsideOpen.value = !isAsideOpen.value
    }

    function closeAside() {
      isAsideOpen.value = false
    }

    function handleClickOutsideAside(event: any) {
      if (!event.target.closest('bg-gray-800')) { // The aside background color
        closeAside()
      }
    }


    return { isAsideOpen, toggleAsideVisibility, closeAside, handleClickOutsideAside }

  },
  components: {
    HeaderLogo, HamburgerIcon, NavBarItems, ShoppingCartItem, HamburgerDropdown
  },
})
</script>

<style scoped>
.custom-box-shadow {
  box-shadow: rgba(0, 0, 0, 0.05) 0px 6px 24px 0px, rgba(0, 0, 0, 0.08) 0px 0px 0px 1px;
}
</style>