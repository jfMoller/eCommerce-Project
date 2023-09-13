<template>
  <section>
    <ViewTitle text="About us" />
    <div class="flex">
      <TextFrame 
        additionalClass="mr-6 mb-6"
        imageLink="https://blog-frontend.envato.com/cdn-cgi/image/width=2560,quality=75,format=auto/uploads/sites/2/2022/04/E-commerce-App-JPG-File-scaled.jpg"
        text=" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum harum velit ab quasi odit adipisci et, repellat quam? Illum corporis itaque ipsum. Consectetur atque nobis sint reiciendis corrupti officia aliquid.
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem unde accusantium eos iste ipsum cumque vel alias placeat id pariatur beatae quas nulla, cum, dicta aut obcaecati! Hic, dicta fuga." />

      <div class="flex flex-col">
        <TextFrame 
        header="Our story" 
        additionalClass="mb-6"
        text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum harum velit ab quasi odit adipisci et, repellat quam? Illum corporis itaque ipsum. Consectetur atque nobis sint reiciendis corrupti officia aliquid.
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem unde accusantium eos iste ipsum cumque vel alias placeat id pariatur beatae quas nulla, cum, dicta aut obcaecati! Hic, dicta fuga.
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum harum velit ab quasi odit adipisci et, repellat quam? Illum corporis itaque ipsum. Consectetur atque nobis sint reiciendis corrupti officia aliquid.
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem unde accusantium eos iste ipsum cumque vel alias placeat id pariatur beatae quas nulla, cum, dicta aut obcaecati! Hic, dicta fuga." />

        <TextFrame 
          header="Our mission" 
          additionalClass="mb-6"
          text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum harum velit ab quasi odit adipisci et, repellat quam? Illum corporis itaque ipsum. Consectetur atque nobis sint reiciendis corrupti officia aliquid.
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem unde accusantium eos iste ipsum cumque vel alias placeat id pariatur beatae quas nulla, cum, dicta aut obcaecati! Hic, dicta fuga." />
      </div>
    </div>
    <div class="flex">
      <ImageFrame 
        additionalClass="mr-6" 
        additionalImageClass="w-full h-full object-fill"
        imageLink="https://www.digi.com/getattachment/blog/post/sustainable-city/gettyimages-544342844-1280x720.jpg?lang=en-us&width=1280&height=720&ext=.jpg" />
      
      <TextFrame header="Our sustainable approach"
        text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum harum velit ab quasi odit adipisci et, repellat quam? Illum corporis itaque ipsum. Consectetur atque nobis sint reiciendis corrupti officia aliquid.
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem unde accusantium eos iste ipsum cumque vel alias placeat id pariatur beatae quas nulla, cum, dicta aut obcaecati! Hic, dicta fuga.
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsum harum velit ab quasi odit adipisci et, repellat quam? Illum corporis itaque ipsum. Consectetur atque nobis sint reiciendis corrupti officia aliquid.
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem unde accusantium eos iste ipsum cumque vel alias placeat id pariatur beatae quas nulla, cum, dicta aut obcaecati! Hic, dicta fuga." />
    </div>
  </section>
</template>

<script lang="ts">
import ViewTitle from '@/components/ViewTitle.vue';
import TextFrame from '@/components/TextFrame.vue';
import ImageFrame from '@/components/ImageFrame.vue';

export default {
  name: 'AboutView',
  components: { ViewTitle, TextFrame, ImageFrame }
}
</script>
