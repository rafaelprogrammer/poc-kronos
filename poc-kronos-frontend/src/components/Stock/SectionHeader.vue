<template lang="pug">
  h2.headline.primary--text
    markdown(:source="value" v-if="value")
</template>

<script>
export default {
  props: {
    value: {
      type: String,
      default: ""
    }
  }
};
</script>
