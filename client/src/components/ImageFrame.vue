<template>
    <div :class="['min-w', 'bg-white', 'p-4', 'rounded-lg', 'shadow-md', additionalClass]">
        <h2 v-if="header" class="text-2xl font-bold mb-4"> {{ header }}</h2>
        <img v-if="imageLink" v-bind:src="imageLink" :class="[additionalImageClass]">
    </div>
</template>

<script lang="ts">

export default {
    name: "ImageFrame",
    props: {
        imageLink: {
            type: String,
            required: true
        },
        header: {
            type: String,
            required: false
        },
        additionalClass: {
            type: String,
            required: false
        },
        additionalImageClass: {
            type: String,
            required: false
        },
    }
}

</script>