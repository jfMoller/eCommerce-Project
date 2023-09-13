<template>
  <section>
      <h2 class="text-3xl font-semibold mb-6">Contact Us</h2>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div class="bg-white p-6 rounded-lg shadow-lg">
          <h3 class="text-2xl font-semibold mb-4">Get in Touch</h3>
          <form @submit.prevent="submitForm">
            <div class="mb-4">
              <label for="name" class="block text-gray-700">Name:</label>
              <input v-model="formData.name" type="text" id="name" name="name" class="w-full px-4 py-2 rounded-lg border border-gray-300 focus:border-blue-500 focus:outline-none">
            </div>
            <div class="mb-4">
              <label for="email" class="block text-gray-700">Email:</label>
              <input v-model="formData.email" type="email" id="email" name="email" class="w-full px-4 py-2 rounded-lg border border-gray-300 focus:border-blue-500 focus:outline-none">
            </div>
            <div class="mb-4">
              <label for="message" class="block text-gray-700">Message:</label>
              <textarea v-model="formData.message" id="message" name="message" rows="4" class="w-full px-4 py-2 rounded-lg border border-gray-300 focus:border-blue-500 focus:outline-none"></textarea>
            </div>
            <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-full hover:bg-blue-600 transition duration-300 ease-in-out">Submit</button>
          </form>
        </div>
        <div class="bg-white p-6 rounded-lg shadow-lg">
          <h3 class="text-2xl font-semibold mb-4">Contact Information</h3>
          <p class="text-gray-700">Feel free to contact us with any questions or inquiries. We are available by phone or email.</p>
          <div class="mt-6">
            <h4 class="text-lg font-semibold mb-2">Phone:</h4>
            <p class="text-gray-700">(123) 456-7890</p>
          </div>
          <div class="mt-4">
            <h4 class="text-lg font-semibold mb-2">Email:</h4>
            <p class="text-gray-700">info@example.com</p>
          </div>
        </div>
      </div>
  </section>
</template>

<script lang="ts">
import { ref } from 'vue';

export default {
  name: 'ContactView',
  setup() {
    const formData = ref({
      name: '',
      email: '',
      message: ''
    });

    const submitForm = () => {
      console.log('Form data submitted:', formData.value);
    };

    return {
      formData,
      submitForm
    };
  }
}
</script>
