<template>
    <div :class="['min-w', 'bg-white', 'p-4', 'rounded-lg', 'shadow-md', additionalClass]">
        <img v-if="imageLink" v-bind:src="imageLink" class="max-h-[300px] mb-4">
        <h2 v-if="header" class="text-2xl font-bold mb-4"> {{ header }}</h2>
        <p class="text-gray-700 leading-relaxed">
            {{ text }}
        </p>
    </div>
</template>

<script lang="ts">

export default {
    name: "TextFrame",
    props: {
        imageLink: {
            type: String,
            required: false
        },
        header: {
            type: String,
            required: false
        },
        text: {
            type: String,
            required: true,
        },
        additionalClass: {
            type: String,
            required: false
        },
    }
}

</script>