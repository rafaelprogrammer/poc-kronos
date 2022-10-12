<template>
  <v-card>
    <v-img :src="cardMedia" :height="mediaHeight">
      <v-container fill-height fluid>
        <v-layout fill-height>
          <v-flex xs12 align-end flexbox>
            <span class="headline white--text" v-html="mediaHeadline"></span>
          </v-flex>
        </v-layout>
      </v-container>
    </v-img>
    <v-card-title primary-title v-if="cardTitle">
      <div>
        <div class="headline" v-html="cardTitle.headline" v-if="cardTitle.headline"></div>
        <span class="grey--text" v-html="cardTitle.detail" v-if="cardTitle.detail"></span>
      </div>
    </v-card-title>
    <v-card-actions>
      <template v-for="(action, i) in leftActions">
        <v-btn
          v-if="action.event"
          :key="`leftaction-${i}`"
          :icon="!action.btn"
          :color="action.color"
          :flat="action.flat"
          :class="action.className"
          :round="action.round"
          :outline="action.outline"
          :dark="action.dark"
          :small="action.small"
          @click="$emit(action.event, data)"
        >
          <v-icon v-if="action.icon" :color="action.iconColor">{{ action.icon }}</v-icon>
          <span v-if="action.btn && action.text">{{action.text}}</span>
        </v-btn>
        <v-btn
          v-else
          :key="`leftaction-${i}`"
          :icon="!action.btn"
          :color="action.color"
          :flat="action.flat"
          :class="action.className"
          :round="action.round"
          :outline="action.outline"
          :dark="action.dark"
          :small="action.small"
        >
          <v-icon v-if="action.icon" :color="action.iconColor">{{ action.icon }}</v-icon>
          <span v-if="action.btn && action.text">{{action.text}}</span>
        </v-btn>
      </template>
      <v-spacer></v-spacer>
      <template v-for="(action, i) in rightActions">
        <v-btn
          :key="`rightaction-${i}`"
          v-if="action.event"
          :icon="!action.btn"
          :color="action.color"
          :flat="action.flat"
          :class="action.className"
          :round="action.round"
          :outline="action.outline"
          :dark="action.dark"
          :small="action.small"
          @click="$emit(action.event, data)"
        >
          <v-icon v-if="action.icon" :color="action.iconColor">{{ action.icon }}</v-icon>
          <span v-if="action.btn && action.text">{{action.text}}</span>
        </v-btn>
        <v-btn
          v-else
          :key="`rightaction-${i}`"
          :icon="!action.btn"
          :color="action.color"
          :flat="action.flat"
          :class="action.className"
          :round="action.round"
          :outline="action.outline"
          :dark="action.dark"
          :small="action.small"
        >
          <v-icon v-if="action.icon" :color="action.iconColor">{{ action.icon }}</v-icon>
          <span v-if="action.btn && action.text">{{action.text}}</span>
        </v-btn>
      </template>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: {
    data: {},
    cardMedia: {
      type: String,
      default: ""
    },
    mediaHeight: {
      type: String,
      default: "200px"
    },
    mediaHeadline: {
      type: String,
      default: ""
    },
    actions: {
      type: Array,
      default: () => []
    },
    cardTitle: {
      type: Object,
      default: null
    }
  },
  computed: {
    leftActions() {
      return this.actions.filter(action => action.position === "left");
    },
    rightActions() {
      return this.actions.filter(action => action.position === "right");
    }
  }
};
</script>
