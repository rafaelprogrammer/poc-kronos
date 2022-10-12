<template>
  <div contenteditable="true" @input="update" ref="chateditable"></div>
</template>
<script>
import striptags from "striptags";
import { replaceInvalidCharacters, cleanHTML } from "@/utils/EditorHelper";
export default {
  props: ["content", "type"],
  mounted() {
    this.$eventBus.$on("resetChatEditor", () => {
      if (this.$refs.chateditable) this.$refs.chateditable.innerHTML = "";
    });
    if (this.$refs["chateditable"]) {
      this.$refs["chateditable"].addEventListener(
        "paste",
        this.chatEditablePastEventHandler
      );
    }
    this.$refs.chateditable.addEventListener(
      "keypress",
      this.chatEditableKeypressEventHandler
    );
  },
  beforeCreate() {
    this.$eventBus.$off("resetChatEditor");
  },
  beforeDestroy() {
    this.$refs["chateditable"].removeEventListener(
      "paste",
      this.chatEditablePastEventHandler
    );
    this.$refs["chateditable"].removeEventListener(
      "keypress",
      this.chatEditableKeypressEventHandler
    );
    this.$eventBus.$off("resetChatEditor");
  },
  methods: {
    update(event) {
      this.updateHeight();
      this.$emit("update", event.target[this.type]);
    },
    updateHeight() {
      const inlineTaskTextField = this.$refs.chateditable;
      const variableHeightOfEditor = inlineTaskTextField.clientHeight;
      const height = variableHeightOfEditor - 24;
      this.$emit("onResize", height);
    },
    chatEditablePastEventHandler(event) {
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
    },
    chatEditableKeypressEventHandler(event) {
      const vm = this;
      const keycode = event.charCode || event.keyCode;
      if (keycode === 13) {
        if (!event.shiftKey) {
          event.returnValue = false;
          vm.$emit("onEnter");
        }
      }
    }
  }
};
</script>
