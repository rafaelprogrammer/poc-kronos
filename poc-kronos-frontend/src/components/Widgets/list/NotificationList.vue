<template>
  <v-card class="elevation-4 border-radius6">
    <v-toolbar card dense color="transparent">
      <v-toolbar-title>
        <h4>Notification</h4>
      </v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text class="pa-0">
      <v-list two-line class="pa-0 border-radius6">
        <template v-for="(item, index) in items">
          <v-subheader v-if="item.header" :key="item.header">{{ item.header }}</v-subheader>
          <v-divider v-else-if="item.divider" :key="index"></v-divider>
          <v-list-tile avatar v-else :key="item.title" @click="handleClick">
            <v-list-tile-avatar color="grey lighten-3">
              <v-icon :color="item.color" class="text-glow">{{item.icon}}</v-icon>
            </v-list-tile-avatar>
            <v-list-tile-content>
              <v-list-tile-sub-title v-html="item.title"></v-list-tile-sub-title>
              <v-list-tile-sub-title class="caption" v-html="item.timeLabel"></v-list-tile-sub-title>
            </v-list-tile-content>
            <v-list-tile-action>
              <v-icon>navigate_next</v-icon>
            </v-list-tile-action>
          </v-list-tile>
        </template>
      </v-list>
      <v-divider></v-divider>
      <v-btn block flat class="ma-0">
        <v-icon>more_horiz</v-icon>
      </v-btn>
      <v-divider></v-divider>
    </v-card-text>
  </v-card>
</template>

<script>
import notes from "@/data/widgets/notification";
export default {
  data: () => ({
    items: notes
  }),
  methods: {
    handleClick: () => {}
  }
};
</script>
