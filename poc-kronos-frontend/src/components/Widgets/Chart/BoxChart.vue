<template>
  <v-card :color="cardColor" :dark="computeCardDark">
    <v-card-title>
      <div class="layout row ma-0">
        <div class="subheading">{{ title }}</div>
        <v-spacer></v-spacer>
        <div class="caption"> <v-icon v-text="icon" :color="iconColor"></v-icon>  {{ subTitle }}</div>
      </div>
    </v-card-title>
    <v-responsive class="white--text">
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
    </v-responsive>
  </v-card>
</template>

<script>
import ChartJs from "@/components/ChartJs";

export default {
  props: {
    type: String,
    title: String,
    subTitle: String,
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
    // LineChart
  },
  data() {
    return {};
  },
  computed: {
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
