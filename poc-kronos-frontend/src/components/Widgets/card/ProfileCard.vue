<template>
  <v-card>
    <v-img :src="setAvatar" height="390">
      <v-layout column class="media ma-0">
        <v-spacer></v-spacer>
        <v-card-title class="white--text pl-5 pt-5">
          <div class="display-1 pl-5 pt-5" v-text="setName"></div>
        </v-card-title>
      </v-layout>
    </v-img>
    <v-list two-line class="pa-0">
      <template v-for="(mobile, index) in setMobiles">
        <v-list-tile href="#" :key="`${index}-mobile`">
          <v-list-tile-action>
            <v-icon color="indigo" v-if="index === 0">phone</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title v-text="mobile.number"></v-list-tile-title>
            <v-list-tile-sub-title v-text="mobile.type"></v-list-tile-sub-title>
          </v-list-tile-content>
          <v-list-tile-action>
            <v-icon>chat</v-icon>
          </v-list-tile-action>
        </v-list-tile>
      </template>
      <v-divider inset></v-divider>
      <template v-for="(email, index) in setEmails">
        <v-list-tile href="#" :key="`${index}-email`">
          <v-list-tile-action>
            <v-icon color="indigo" v-if="index === 0">mail</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title v-text="email.name"></v-list-tile-title>
            <v-list-tile-sub-title v-text="email.type"></v-list-tile-sub-title>
          </v-list-tile-content>
        </v-list-tile>
      </template>
      <v-divider inset></v-divider>
      <v-list-tile href="#">
        <v-list-tile-action>
          <v-icon color="indigo">location_on</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title v-text="setAddressLine1"></v-list-tile-title>
          <v-list-tile-sub-title v-text="setAddressLine2"></v-list-tile-sub-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </v-card>
</template>
<script>
import { authUser } from "@/data/dummyData";

export default {
  props: {
    name: String,
    avatar: String,
    mobiles: Array,
    emails: Array,
    address: Object
  },
  data() {
    return {
      authUser
    };
  },
  computed: {
    setName() {
      return this.name;
    },
    setAvatar() {
      return this.avatar;
    },
    setMobiles() {
      return this.mobiles;
    },
    setEmails() {
      return this.emails;
    },
    setAddressLine1() {
      return this.address.line1;
    },
    setAddressLine2() {
      return this.address.line2;
    }
  }
};
</script>
