<template>
  <ul :class="[dark ? 'dark' : 'light', 'vue-countdown']">
    <li>
      <p :class="[dark ? 'white--text' : 'black--text', 'digit']">{{ days | twoDigits }}</p>
      <p :class="[dark ? 'white--text' : 'black--text', 'text']">days</p>
    </li>
    <li>
      <p :class="[dark ? 'white--text' : 'black--text', 'digit']">{{ hours | twoDigits }}</p>
      <p :class="[dark ? 'white--text' : 'black--text', 'text']">hours</p>
    </li>
    <li>
      <p :class="[dark ? 'white--text' : 'black--text', 'digit']">{{ minutes | twoDigits }}</p>
      <p :class="[dark ? 'white--text' : 'black--text', 'text']">Min</p>
    </li>
    <li>
      <p :class="[dark ? 'white--text' : 'black--text', 'digit']">{{ seconds | twoDigits }}</p>
      <p :class="[dark ? 'white--text' : 'black--text', 'text']">Sec</p>
    </li>
  </ul>
</template>

<script>
let interval = null;
export default {
  props: ["deadline", "stop", "dark"],
  data() {
    return {
      now: Math.trunc(new Date().getTime() / 1000),
      date: null,
      diff: 0
    };
  },
  mounted() {
    this.date = Math.trunc(Date.parse(this.deadline.replace(/-/g, "/")) / 1000);

    interval = setInterval(() => {
      this.now = Math.trunc(new Date().getTime() / 1000);
    }, 1000);
    // console.log(interval)
  },
  computed: {
    seconds() {
      return Math.trunc(this.diff) % 60;
    },

    minutes() {
      return Math.trunc(this.diff / 60) % 60;
    },

    hours() {
      return Math.trunc(this.diff / 60 / 60) % 24;
    },

    days() {
      return Math.trunc(this.diff / 60 / 60 / 24);
    }
  },
  watch: {
    now() {
      this.diff = this.date - this.now;
      if (this.diff <= 0 || this.stop) {
        this.diff = 0;
        // Remove interval
        clearInterval(interval);
      }
    }
  }
};
</script>
<style scoped>
.vue-countdown {
  padding: 0;
  margin: 0;
}
.vue-countdown li {
  display: inline-block;
  margin: 0 8px;
  text-align: center;
  position: relative;
}
.vue-countdown li p {
  margin: 0;
}
.vue-countdown li:after {
  content: ":";
  position: absolute;
  top: 0;
  right: -13px;
  font-size: 32px;
}
.vue-countdown.dark li:after {
  color: white;
}
.vue-countdown li:first-of-type {
  margin-left: 0;
}
.vue-countdown li:last-of-type {
  margin-right: 0;
}
.vue-countdown li:last-of-type:after {
  content: "";
}
.vue-countdown .digit {
  font-size: 32px;
  font-weight: 600;
  line-height: 1.4;
  margin-bottom: 0;
}
.vue-countdown .text {
  text-transform: uppercase;
  margin-bottom: 0;
  font-size: 10px;
}
</style>
