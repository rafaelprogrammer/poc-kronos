<template>
  <v-container fluid grid-list-md>
    <v-layout row wrap>
      <v-flex
        v-bind="{ [`xs${flexSize}`]: true }"
        v-for="image in sidebarImages"
        :key="image"
        class="curson-pointer"
        @click.stop="$store.dispatch('setNavDrawerAlphaImg', image)"
      >
        <v-card flat tile>
          <v-img
            :src="image"
            :height="imgHeight"
            class="cursor-pointer"
          >
          </v-img>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import sidebarImages from "@/data/json/sidebarimgs.json";
import { mapGetters } from "vuex";

export default {
  props: ["imgHeight", "flexSize"],
  data() {
    return {
      sidebarImages
    };
  },
  computed: {
    ...mapGetters({
      navDrawerAlphaImg: "navDrawerAlphaImg"
    })
  }
};
</script>
