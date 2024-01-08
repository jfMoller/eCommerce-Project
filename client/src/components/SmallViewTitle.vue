<template>
    <div>
        <h2 :class="['text-xl font-semibold', additionalClass]"> {{ text }}</h2>
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
        },
        additionalClass: {
            tupe: String,
            required: false,
        }
    }
}
</script>