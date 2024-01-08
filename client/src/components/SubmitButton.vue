<template>
    <div class="flex justify-between">
        <div />
        <button type="submit" :class="['transition duration-900 text-white py-2 px-4 rounded font-semibold', additionalClass]">
            {{ text }}</button>
    </div>
</template>

<script lang ="ts">
export default {
    props: {
        text: {
            type: String,
            required: true
        },
        additionalClass: {
            type: String,
            required: false,
            default: 'bg-blue-500 hover:bg-blue-600'
        }
    },

}
</script>