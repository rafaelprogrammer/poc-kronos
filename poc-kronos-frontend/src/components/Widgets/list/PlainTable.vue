<template>
  <v-card class="elevation-4 border-radius6">
    <v-toolbar card dense color="transparent">
      <v-toolbar-title><h4>Project</h4></v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text class="pa-0">
      <template>
        <v-data-table
          :headers="headers"
          :items="projects"
          hide-actions
          class="elevation-0 border-radius6"
        >
          <template slot="items" slot-scope="props">
            <td>
              <v-avatar size="36px">
                <img :src="props.item.avatar" :alt="props.item.username" />
              </v-avatar>
            </td>
            <td>{{ props.item.name }}</td>
            <td class="text-xs-left">{{ props.item.deadline }}</td>
            <td class="text-xs-left">
              <v-progress-linear 
                :value="props.item.progress" 
                height="5" 
                :color="props.item.color"
                >
              </v-progress-linear> 
            </td>
            <td class="text-xs-left">
              <template v-for="(member, index) in props.item.members" >
                <v-avatar size="25" :key="`member-${index}`">
                  <img
                    :src="member.avatar"
                    :alt="member.firstname"
                  >
                </v-avatar>
              </template>
              <v-avatar color="grey" size="25" v-if="props.item.membesCount > 3">
                <span class="white--text">+{{props.item.membesCount - 3}}</span>
              </v-avatar>
            </td>
            <td class="text-xs-right">
              <v-btn flat icon color="grey">
                <v-icon>more_vert</v-icon>
              </v-btn>
            </td>
          </template>
        </v-data-table>
      </template>
      <v-divider></v-divider>
    </v-card-text>
  </v-card>
</template>

<script>
import { Projects } from "@/data/widgets/project";
export default {
  data() {
    return {
      headers: [
        {
          text: "",
          align: "center",
          sortable: false,
          value: "avatar"
        },
        {
          text: "Name",
          align: "left",
          value: "name"
        },
        { text: "Deadline", value: "deadline" },
        { text: "Progress", value: "progress" },
        { text: "Members", value: "members", sortable: false },
        { text: "Action", value: "action", align: "right" }
      ]
    };
  },
  computed: {
    projects() {
      return Projects;
    }
  }
};
</script>
