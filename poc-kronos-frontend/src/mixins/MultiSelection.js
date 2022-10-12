export default {
  data() {
    return {
      selected: []
    };
  },
  computed: {
    isIndeterminate() {
      return (
        this.selected.length > 0 && this.selected.length !== this.items.length
      );
    },
    selectAll: {
      get() {
        return this.selected.length === this.items.length;
      },
      set() {
        this.selected.length === this.items.length;
      }
    }
  },
  methods: {
    toggleAll(value) {
      if (!value) {
        this.selected = [];
        this.items.forEach(element => {
          element.selected = false;
        });
      } else {
        this.selected = this.items.slice();
        this.items.forEach(element => {
          element.selected = true;
        });
      }
    },
    toggleSelect(item) {
      if (item.selected) {
        this.selected.push(item);
      } else {
        this.selected.splice(this.selected.indexOf(item), 1);
      }
    }
  }
};
