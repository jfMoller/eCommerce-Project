<template>
    <div class="hidden sm:block absolute pt-2">
        <div class="flex flex-col justify-center items-center bg-white p-4 shadow-md rounded-md">
            <div class="text-left space-y-2 flex flex-col">
                <router-link :to="{ name: 'HandleProductsView' }" class="text-blue-700 hover:text-blue-500">
                    Handle products
                </router-link>
            </div>
        </div>
    </div>
</template>
    
<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: "AdminToolsPopup",
});
</script>