<template>
    <div class="mb-4">
        <h2 class="text-xl font-semibold"> {{ text }}</h2>
        <div v-if="hasUnderline" class="border border-t-1 w-full mt-1"></div>
    </div>
</template>

<script lang ="ts">
export default {
    props: {
        text: {
            type: String,
            required: true
        },
        hasUnderline: {
            type: Boolean,
            required: false,
            default: true,
        }
    }
}
</script>