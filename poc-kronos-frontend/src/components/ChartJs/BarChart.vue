<script>
import { has } from "lodash";
import "chartjs-plugin-style";
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  props: {
    data: Object,
    options: Object,
    gradient: {
      type: Boolean,
      default: false
    },
    gradientSet: {
      type: Array
    },
    linearStroke: {
      type: Array,
      default: () => [500, 0, 100, 0]
    }
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.rerender);
  },
  mounted() {
    window.addEventListener("resize", this.rerender);
    if (this.gradient) {
      const linearStroke = this.linearStroke;
      let gradientStroke = this.$refs.canvas
        .getContext("2d")
        .createLinearGradient(
          linearStroke[0],
          linearStroke[1],
          linearStroke[2],
          linearStroke[3]
        );
      if (this.gradientSet.length) {
        this.gradientSet.forEach((hex, index) => {
          if (
            typeof hex === "object" &&
            has(hex, "stop") &&
            has(hex, "color")
          ) {
            let { stop, color } = hex;
            gradientStroke.addColorStop(stop, color);
          } else {
            let stop = index > 0 ? 1 / index : 0;
            stop = Number(stop.toFixed(1));
            gradientStroke.addColorStop(stop, hex);
          }
        });
        this.data.datasets.map(obj => {
          if (has(obj, "backgroundColor") === false) {
            obj.backgroundColor = gradientStroke;
          }
        });
      }
    }
    this.renderChart(this.data, this.options);
  },
  methods: {
    rerender() {
      this.renderChart(this.data, this.options);
    }
  }
};
</script>
