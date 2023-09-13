<template>
    <h2 :class="['text-3xl', 'font-semibold', 'mb-4', additionalClass]">{{ text }}</h2>
</template>

<script lang="ts">

export default {
    name: "ViewTitle",
    props: {
        text: {
            type: String,
            required: true,
        },
        additionalClass: {
            type: String,
            required: false
        }
    }
}

</script>