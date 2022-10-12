<template>
  <v-list two-line>
    <theme-builder icon="color_lens" action="setNavDrawerScheme" :btnClass="navDrawerScheme">Navigation</theme-builder>
    <theme-builder icon="color_lens" action="setNavToolbarScheme" :btnClass="navToolbarScheme">Toolbar</theme-builder>
    <theme-builder icon="color_lens" action="setFooterScheme" :btnClass="navFooterScheme">Footer</theme-builder>
  </v-list>
</template>

<script>
import { mapGetters } from "vuex";
import ThemeBuilder from "@/components/ThemeBuilder/Builder";

export default {
  components: {
    ThemeBuilder
  },
  computed: {
    ...mapGetters({
      navDrawerScheme: "navDrawerScheme",
      navToolbarScheme: "navToolbarScheme",
      navFooterScheme: "navFooterScheme"
    })
  }
};
</script>
