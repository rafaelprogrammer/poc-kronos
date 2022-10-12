<template>
  <div>
    <section-definition :title="page.title"></section-definition>
    <v-toolbar dark color="primary">
      <v-toolbar-title>Gestão Educacional</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
    </v-toolbar>
    <v-container fluid>
      <v-layout align-center>
        <v-flex xs12>
          <v-card
          >
            <v-layout align-center justify-center row fill-height wrap>
              <v-flex xs12 class="text-xs-center mt-3">
                <img src="/static/logo/logo_kronos_ct_ft.png" alt="Vuse" class="text-xs-center">
              </v-flex>
            </v-layout>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      page: {
        title: "Home",
        headline: "Home Page",
      }
    };
  },
};
</script>
