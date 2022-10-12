<template>
  <div contenteditable="true" @input="update" ref="baseeditable" :placeholder="placeholderText"></div>
</template>
<script>
import striptags from "striptags";
import { replaceInvalidCharacters, cleanHTML } from "@/utils/EditorHelper";

export default {
  props: ["content", "type", "placeholder"],
  mounted() {
    if (this.$refs["baseeditable"]) {
      this.$refs["baseeditable"].addEventListener(
        "paste",
        this.baseEditablePastEventHandler
      );
    }
  },
  beforeDestroy() {
    this.$refs["baseeditable"].removeEventListener(
      "paste",
      this.baseEditablePastEventHandler
    );
  },
  methods: {
    update(event) {
      this.$emit("update", event.target[this.type]);
    },
    baseEditablePastEventHandler(event) {
      event.preventDefault();
      const regex = /\s(id|class|style|name|src|alt|data)="[^"]+"/gi;

      let text = event.clipboardData.getData("text/plain");
      const cleanText = replaceInvalidCharacters(text);

      const copyText = striptags(
        cleanText,
        [
          "strong",
          "div",
          "b",
          "i",
          "u",
          "a",
          "br",
          "p",
          "ul",
          "li",
          "ol",
          "dd",
          "dt"
        ],
        ""
      );
      const filteText = copyText.replace(regex, "");
      // const filterText = filteText
      const filterText = cleanHTML(filteText);
      document.execCommand("insertText", false, filterText);
    }
  },
  computed: {
    placeholderText() {
      return this.placeholder ? this.placeholder : "";
    }
  }
};
</script>
