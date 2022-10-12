<template>
  <div class="home">
     <Calendario/>
  </div>
</template>

<script>
// @ is an alias to /src
import Calendario from '@/components/Calendario.vue';

export default {
  name: 'home',
  components: {
    Calendario,
  },
};
</script>
