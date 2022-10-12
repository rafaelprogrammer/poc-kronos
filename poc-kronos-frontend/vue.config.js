const path = require("path");
module.exports = {
  devServer: {
    disableHostCheck: true
  },
  outputDir: path.resolve(__dirname, "../src/main/resources/public"),
  assetsDir: "static"
};
