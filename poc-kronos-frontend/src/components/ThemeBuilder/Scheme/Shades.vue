<template>
  <v-container fluid pa-0>
    <v-layout wrap row ma-0>
      <v-flex xs2
        v-for="(shade, property, index) in baseShades"
        :key="index"
        @click.stop="$emit('next', shade)"
        class="flex-fix"
      >
        <!-- <v-card flat tile :class="shade.classname" class="text-xs-center" height="40">
          <v-layout align-center justify-center row fill-height>
            <v-flex text-xs-center class="flex-fix">
              {{ property }}
            </v-flex>
          </v-layout>
        </v-card> -->
        <v-avatar
          :class="[shade.classname, 'cursor-pointer']"
          size="40"
        >
            <v-icon large v-if="active.classname &&  active.classname === shade.classname" >panorama_fish_eye</v-icon>
            <span v-else>{{ property }}</span>
        </v-avatar>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
export default {
  props: ["base", "selected"],
  computed: {
    baseShades() {
      return this.base;
    },
    active() {
      if (typeof this.selected === "string") {
        return {
          classname: this.selected
        };
      }
      return this.selected;
    }
  }
};
</script>
<style scoped>
.card {
  min-width: 40px;
}
</style>
