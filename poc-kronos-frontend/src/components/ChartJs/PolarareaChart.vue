<script>
import { PolarArea } from "vue-chartjs";

export default {
  extends: PolarArea,
  props: {
    data: Object,
    options: Object
  },
  mounted() {
    this.renderChart(this.data, this.options);
  }
};
</script>
