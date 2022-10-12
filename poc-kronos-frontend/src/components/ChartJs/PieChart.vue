<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  props: {
    data: Object,
    options: Object
  },
  mounted() {
    this.renderChart(this.data, this.options);
  }
};
</script>
