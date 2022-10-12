<template>
  <v-card class="elevation-4 border-radius6">
    <v-toolbar card dense color="transparent">
      <v-toolbar-title><h4>My Orders</h4></v-toolbar-title>
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
          :items="items"
          hide-actions
          class="elevation-0 table-striped border-radius6 plain-order-table"
        >
          <template slot="items" slot-scope="props">
            <td>{{ props.item.id }}</td>
            <td class="text-xs-left">{{ props.item.product }}</td>
            <td class="text-xs-left">{{ props.item.price }}</td>
            <td class="text-xs-left"><v-chip label small :class="getColorByStatus(props.item.status)" text-color="white" >{{ props.item.status }}</v-chip></td>
          </template>
        </v-data-table>
      </template>
      <v-divider></v-divider>
    </v-card-text>
  </v-card>
</template>

<script>
import items from "@/data/widgets/order";

export default {
  data() {
    return {
      headers: [
        {
          text: "#",
          align: "left",
          sortable: false,
          value: "id"
        },
        { text: "Product", value: "product" },
        { text: "Price", value: "price" },
        { text: "Status", value: "status" }
      ],
      items: items,
      colors: {
        processing: "box-blue-500-glow",
        sent: "box-red-500-glow",
        "in-transit": "box-orange-500-glow",
        delivered: "box-green-500-glow"
      }
    };
  },
  methods: {
    getColorByStatus(status) {
      return this.colors[status];
    }
  }
};
</script>
<style>
.plain-order-table .v-table thead tr {
  height: 48px;
}
</style>
