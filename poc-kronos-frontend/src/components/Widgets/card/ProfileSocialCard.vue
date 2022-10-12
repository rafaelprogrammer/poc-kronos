<template>
  <div>
    <v-card>
    <v-img
      src="/static/doc-images/HexesisMaterial01.png"
      height="150"
      class="pos-relative overflow-initial"
    >
      <v-layout row wrap class="mx-3 py-2">
        <v-flex xs12 class="text-xs-center mt--85">
          <v-avatar size="100">
            <img :src="avatar" :alt="name">
          </v-avatar>
        </v-flex>
      </v-layout>
    </v-img>
    <v-container fluid grid-list-xl  mt-2>
      <v-layout row wrap ma-0>
        <v-flex xs12 class="mid-section mt-4">
          <div class="text-xs-center">
            <h4 class="title mb-0" v-html="name"></h4>
            <slot name="details"></slot>
          </div>
        </v-flex>
        <v-flex xs12 class="text-xs-center">
          <slot name="actions"></slot>
        </v-flex>
      </v-layout>
    </v-container>
    </v-card>
  </div>
</template>
<script>
export default {
  props: {
    name: {
      type: String,
      default: ""
    },
    avatar: {
      type: String,
      default: null
    }
  }
};
</script>
