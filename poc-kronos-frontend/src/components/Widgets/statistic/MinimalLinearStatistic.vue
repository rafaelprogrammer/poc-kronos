<template>
  <v-card>
    <v-card-text class="pa-0">
      <v-layout ma-0>
        <v-flex sm6 xs6>
          <v-layout ma-0 fill-height justify-start align-center>
            <v-icon size="56px" :color="color">{{icon}}</v-icon>
          </v-layout>
        </v-flex>
        <v-flex sm6 xs6 text-xs-right py-3>
          <div class="headline font-weight-medium" :class="titleColor">{{ title }}</div>
          <span class="body-1">{{ subTitle }}</span>
          <v-progress-linear :value="value" height="5" :color="color"></v-progress-linear>
        </v-flex>
      </v-layout>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: {
    icon: String,
    title: String,
    subTitle: String,
    color: String,
    applyTitleColor: Boolean,
    value: Number
  },
  computed: {
    titleColor() {
      return this.applyTitleColor ? `${this.color}--text` : null;
    }
  }
};
</script>
