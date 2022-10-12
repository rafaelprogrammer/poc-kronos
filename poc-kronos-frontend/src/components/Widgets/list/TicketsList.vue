<template>
  <v-card class="elevation-4 border-radius6">
    <v-toolbar card dense color="transparent">
      <v-toolbar-title>
        <h4>Tickets</h4>
      </v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text class="pa-0">
      <v-expansion-panel popout>
      <v-expansion-panel-content v-for="(ticket, i) in tickets" :key="i" hide-actions>
        <v-layout slot="header" align-center row spacer>
            <v-flex xs6 sm6>
                <v-chip
                v-if="ticket.new"
                :color="`${ticket.color} lighten-4`"
                class="ml-0"
                label
                small
                >{{ ticket.new }} new</v-chip>
                <strong v-html="ticket.title"></strong>
            </v-flex>
            <v-flex>
                <v-avatar slot="activator" size="36px">
                <img
                    v-if="ticket.avatar"
                    :src="ticket.avatar"
                    :alt="ticket.username"
                >
                <v-icon v-else :color="ticket.color" v-text="ticket.icon"></v-icon>
                </v-avatar>
            </v-flex>

            <v-flex sm4 md3 hidden-xs-only>
                <strong v-html="ticket.username"></strong>
                <div class="caption">Created on 12 days ago</div>
            </v-flex>           

            <v-flex v-if="ticket.status">
                <v-chip color="primary" small text-color="white">{{ ticket.status }}</v-chip>
            </v-flex>
        </v-layout>

        <v-card>
          <v-divider></v-divider>
          <v-card-text v-text="lorem"></v-card-text>
        </v-card>
      </v-expansion-panel-content>
    </v-expansion-panel>
    </v-card-text>
  </v-card>
</template>
<script>
import tickets from "@/data/widgets/Tickets";
export default {
  data: () => ({
    tickets,
    lorem:
      "Lorem ipsum dolor sit amet, at aliquam vivendum vel, everti delicatissimi cu eos. Dico iuvaret debitis mel an, et cum zril menandri. Eum in consul legimus accusam. Ea dico abhorreant duo, quo illum minimum incorrupte no, nostro voluptaria sea eu. Suas eligendi ius at, at nemore equidem est. Sed in error hendrerit, in consul constituam cum."
  }),
  methods: {
    handleClick: () => {}
  }
};
</script>
