<template>
    <v-autocomplete
      :id="id"
      :loading="loading"
      :items="items"
      :search-input.sync="search"
      v-model="select"
      item-value="id"
      :item-text="itemText"
      cache-items
      :label="label"
      return-object
      :error-messages="errorMessages"
      no-data-text="Digite um valor válido"
    ></v-autocomplete>
</template>
<script>
import EventBus from "@/utils/EventBus";
export default {
  props: {
    id: String,
    dados: Array,
    label: String,
    itemText: String,
    errorMessages: Array,
    vModel: Object,
    service: Object
  },
  data() {
    return {
      loading: false,
      items: [],
      search: null,
      select: {},
      data: []
    };
  },
  created() {
    EventBus.$on("resetCustom", () => {
      this.select = null;
      this.loading = false;
    });
  },
  watch: {
    vModel() {
      EventBus.$on("searchOK" + this.id, () => {
        if (this.items[0]) {
          this.select = this.items[0];
        }
      });
      if (this.vModel && this.vModel.nome) {
        this.querySelections(this.vModel.nome, true);
      }
    },
    search(val) {
      val && val !== this.select && this.querySelections(val, false);
    },
    select(value) {
      this.$emit("select", value);
    }
  },
  methods: {
    querySelections(v, edit) {
      this.loading = true;
      // Simulated ajax query
      setTimeout(async () => {
        if (this.service && v.length > 3) {
          const resultado = await this.service.oncomplete(v);
          this.items = resultado.dados;
        } else {
          this.items = this.dados.filter(e => {
            return e.nome.toLowerCase().indexOf(v.toLowerCase()) > -1;
          });
        }
        this.loading = false;
        if (edit) {
          EventBus.$emit("searchOK" + this.id);
        }
      }, 500);
    }
  }
};
</script>
