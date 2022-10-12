<template>
  <v-card :color="cardColorSet" :dark="computeCardDark">
    <v-card-text>
      <p class="subheading my-0 grey--text">{{ title }}</p>
      <span class="display-1">{{ subtitleLarge }}</span> <v-icon v-text="icon" :color="iconColor"></v-icon><span :class="`${iconColor}--text`">{{ preSubtitle }}</span> {{ subTitle }}
    </v-card-text>
    <component
      :is="type"
      :data="setData"
      :options="setOptions"
      :height="setHeight"
      :gradient="setGradient"
      :gradientSet="setGradientData"
      :linearStroke="linearStrokeData"
    >
    </component>
  </v-card>
</template>
<script>
import { mapGetters } from "vuex";
import ChartJs from "@/components/ChartJs";

export default {
  props: {
    type: String,
    title: String,
    subtitleLarge: String,
    subTitle: String,
    preSubtitle: String,
    icon: String,
    cardColor: {
      type: String,
      default: "white"
    },
    iconColor: {
      type: String,
      default: "primary"
    },
    data: Object,
    option: Object,
    height: {
      type: Number,
      default: 100
    },
    gradient: {
      type: Boolean,
      default: false
    },
    gradientSet: Array,
    linearStroke: {
      type: Array,
      default: () => [500, 0, 100, 0]
    }
  },
  components: {
    ...ChartJs
  },
  computed: {
    ...mapGetters({
      colorScheme: "colorScheme"
    }),
    cardColorSet() {
      return this.cardColor === "white" && this.colorScheme.dark
        ? "dark"
        : this.cardColor;
    },
    computeCardDark() {
      return this.cardColor !== "white";
    },
    setData() {
      return this.data;
    },
    setOptions() {
      return this.option;
    },
    setHeight() {
      return this.height;
    },
    setGradient() {
      return this.gradient;
    },
    setGradientData() {
      return this.gradientSet;
    },
    linearStrokeData() {
      return this.linearStroke;
    }
  }
};
</script>
