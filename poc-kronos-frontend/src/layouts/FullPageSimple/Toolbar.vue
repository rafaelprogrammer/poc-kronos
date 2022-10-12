<template>
  <v-toolbar flat color="transparent" absolute dark>
    <v-toolbar-title>
      <img src="/static/vuse-white-txt.svg" alt="Kronos" class="text-xs-left" height="30">
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items>
      <v-btn flat :to="{ name: 'dashboard/Dashboardv1' }">Home</v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>
