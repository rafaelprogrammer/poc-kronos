<script>
import { Scatter } from "vue-chartjs";

export default {
  extends: Scatter,
  props: {
    data: Object,
    options: Object
  },
  mounted() {
    this.renderChart(this.data, this.options);
  }
};
</script>
