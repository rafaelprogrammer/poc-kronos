<script>
import { Doughnut } from "vue-chartjs";

export default {
  extends: Doughnut,
  props: {
    data: Object,
    options: Object
  },
  mounted() {
    this.renderChart(this.data, this.options);
  }
};
</script>
