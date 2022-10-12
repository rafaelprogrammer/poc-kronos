<script>
import { Bubble } from "vue-chartjs";

export default {
  extends: Bubble,
  props: {
    data: Object,
    options: Object
  },
  mounted() {
    this.renderChart(this.data, this.options);
  }
};
</script>
