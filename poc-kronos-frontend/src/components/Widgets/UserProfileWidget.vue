<template>
  <v-card class="text-xs-center" :class="className" :dark="dark">
    <v-img :color="background" :gradient="gradient" :height="height">
      <v-container fluid grid-list-xl fill-height>
        <v-layout row align-center spacer>
          <v-flex xs12>
            <v-card-text>
              <v-avatar slot="activator" :size="size" :color="avatarColor">
                <img v-if="avatar" :src="avatar" :alt="name">
                <v-icon v-else :color="iconColor" :size="iconSize" v-text="icon"></v-icon>
              </v-avatar>
              <div class="mt-2">
                <h3 class="headline mb-0" v-html="name"></h3>
                <slot name="details"></slot>
              </div>
            </v-card-text>
            <v-card-actions>
              <slot name="actions"></slot>
            </v-card-actions>
          </v-flex>
        </v-layout>
      </v-container>
    </v-img>
  </v-card>
</template>
<script>
export default {
  props: {
    gradient: {
      type: String,
      default: ""
    },
    height: {
      type: String,
      default: "400px"
    },
    background: {
      type: String,
      default: ""
    },
    className: {
      type: Array,
      default: () => []
    },
    avatar: {
      type: String,
      default: ""
    },
    name: {
      type: String,
      default: ""
    },
    size: {
      type: String,
      default: "100px"
    },
    iconSize: {
      type: String,
      default: "75px" // 75% of size will look good with iconSize
    },
    dark: {
      type: Boolean,
      default: false
    },
    avatarColor: {
      type: String,
      default: ""
    },
    iconColor: {
      type: String,
      default: "black"
    },
    icon: {
      type: String,
      default: ""
    }
  }
};
</script>
