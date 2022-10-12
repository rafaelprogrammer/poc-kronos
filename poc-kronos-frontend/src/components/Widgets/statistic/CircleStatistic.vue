<template>
    <v-card>
      <v-card-title>
        <v-layout row ma-0 justify-space-between pb-1>
          <div class="subheading">{{title}}</div>
          <div class="icon">
            <v-icon :color="color">{{icon}}</v-icon>
          </div>
        </v-layout>
      </v-card-title>
      <v-card-text>
        <v-layout justify-center row ma-0>
          <v-progress-circular
            :size="150"
            :width="15"
            :rotate="-90"
            :value="value"
            :color="color"
          >
            {{ value }}
          </v-progress-circular>
        </v-layout>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <div class="headline">{{subTitle}}</div>
        <div class="caption ml-1">{{caption}}</div>
      </v-card-actions>
    </v-card>
</template>

<script>
export default {
  props: {
    icon: String,
    title: String,
    subTitle: String,
    caption: String,
    value: Number,
    color: String
  }
};
</script>
