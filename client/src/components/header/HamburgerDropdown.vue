<template>
    <div v-if="props.isOpen" @click="props.onClickOutside" class="fixed inset-0 bg-transparent z-98"></div>
    <aside v-if="props.isOpen" class="sm:hidden fixed right-0 w-[20rem] h-screen bg-gray-800 z-99 py-20 text-left">
        <nav class="space-y-6 flex flex-col items-center justify-center text-xl">
            <NavBarItems @click="props.onClose" :isShoppingCartIncluded="false"
                class="flex flex-col space-y-6 items-center text-xl w-full" />
        </nav>
    </aside>
</template>
  
<script lang="ts">

import { defineComponent, type PropType } from 'vue';
import NavBarItems from './NavBarItems.vue';

export default defineComponent({
    name: "HamburgerDropdown",
    props: {
        isOpen: {
            type: Boolean,
            required: true
        },
        onClose: {
            type: Function,
            required: true
        },
        onClickOutside: {
            type: Function as PropType<(event: MouseEvent) => void>,
            required: true
        },

    },
    setup(props) {
        return { props }
    },

    components: { NavBarItems }
})
</script>