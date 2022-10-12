<template>
  <v-card class="elevation-4 border-radius6">
    <v-toolbar card dense color="transparent">
      <v-toolbar-title>
        <h4>New Users</h4>
      </v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text class="pa-0">
      <v-list two-line class="pa-0 border-radius6">
        <template v-for="(item, index) in items">

            <v-divider
              v-if="item.divider"
              :key="index"
              :inset="item.inset"
            ></v-divider>

            <v-list-tile
              v-else
              :key="item.title"
              avatar
              @click="handleClick"
            >
              <v-list-tile-avatar>
                <img :src="item.avatar">
              </v-list-tile-avatar>

              <v-list-tile-content>
                <v-list-tile-title v-html="item.name"></v-list-tile-title>
                <v-list-tile-sub-title v-html="item.designation"></v-list-tile-sub-title>
              </v-list-tile-content>
              <v-list-tile-action class="caption">
                  <v-btn icon color="grey lighten-3">
                      <v-icon color="red">add</v-icon>
                  </v-btn>
              </v-list-tile-action>
            </v-list-tile>
        </template>
      </v-list>
    </v-card-text>
  </v-card>
</template>

<script>
import NewUsers from "@/data/widgets/NewUsers";
export default {
  data: () => ({
    items: NewUsers
  }),
  methods: {
    handleClick: () => {}
  }
};
</script>
