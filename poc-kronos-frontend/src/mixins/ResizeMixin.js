import { mapGetters } from "vuex";
export default {
  data() {
    return {
      height: document.documentElement.clientHeight,
      width: document.documentElement.clientWidth
    };
  },
  mounted() {
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.handleResize, true);
  },
  methods: {
    handleResize() {
      this.height = document.documentElement.clientHeight;
      this.width = document.documentElement.clientWidth;
    }
  },
  computed: {
    ...mapGetters({
      showFooter: "showFooter",
      toolbarVisibility: "toolbarVisibility"
    }),
    netHeight() {
      let height = this.height;
      if (this.showFooter) {
        // Deducting height of footer
        height -= 36;
      }
      if (this.toolbarVisibility) {
        // Deducting height of toolbar
        height -= 64;
      }
      return height;
    }
  }
};
