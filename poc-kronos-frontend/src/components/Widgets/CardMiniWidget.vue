<template>
  <v-card :class="className"
    :dark="dark"
    class="cursor-pointer"
    @click.native="$emit('click')"
  >
    <v-img
      :color="background"
      :gradient="gradient"
      :height="height"
    >
      <v-container fluid grid-list-xl fill-height>
        <v-layout row align-center spacer v-if="rightIcon">
          <v-flex xs7>
            <div>
              <div class="headline" v-html="headline"></div>
              <div v-html="subheader"></div>
            </div>
          </v-flex>
          <v-flex xs5 text-xs-right>
            <v-avatar
              :tile="tile"
              slot="activator"
              :size="size"
            >
              <img
                v-if="avatar"
                :src="avatar"
                :alt="headline"
              >
              <v-icon v-else
                :dark="dark"
                :size="size"
                :color="iconColor"
                v-text="icon"
              ></v-icon>
            </v-avatar>
          </v-flex>
        </v-layout>
        <v-layout row align-center spacer v-else>
          <v-flex xs5>
            <v-avatar
              :tile="tile"
              slot="activator"
              :size="size"
            >
              <img
                v-if="avatar"
                :src="avatar"
                :alt="headline"
              >
              <v-icon v-else
                :dark="dark"
                :size="size"
                :color="iconColor"
                v-text="icon"
              ></v-icon>
            </v-avatar>
          </v-flex>
          <v-flex xs7 text-xs-right>
            <div>
              <div class="headline" v-html="headline"></div>
              <div v-html="subheader"></div>
            </div>
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
      default: "100px"
    },
    headline: {
      type: String,
      default: ""
    },
    subheader: {
      type: String,
      default: ""
    },
    avatar: {
      type: String,
      default: ""
    },
    icon: {
      type: String,
      default: ""
    },
    iconColor: {
      type: String,
      default: ""
    },
    background: {
      type: String,
      default: ""
    },
    size: {
      type: String,
      default: "30px"
    },
    className: {
      type: Array,
      default: () => []
    },
    dark: {
      type: Boolean,
      default: false
    },
    rightIcon: {
      type: Boolean,
      default: false
    },
    tile: {
      type: Boolean,
      default: false
    }
  }
};
</script>
