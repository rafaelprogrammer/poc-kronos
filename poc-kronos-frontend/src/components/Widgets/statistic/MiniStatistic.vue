<template>
  <v-card>
    <v-card-text class="pa-0">
      <v-container class="pa-0">
        <v-layout ma-0>
          <v-flex sm6 xs6>
            <v-layout ma-0 fill-height justify-start align-center>
              <v-icon size="56px" :color="color">{{icon}}</v-icon>
            </v-layout>
          </v-flex>
          <v-flex sm6 xs6 text-sm-center py-3 white--text :class="color">
            <div class="headline">{{ title }}</div>
            <span class="caption">{{ subTitle }}</span>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: {
    icon: String,
    title: String,
    subTitle: String,
    color: String
  }
};
</script>
