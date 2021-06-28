const path =require('path');
function resolve(dir) {
  return path.join(__dirname,dir)
}

module.exports = {
  chainWebpack: (config) => {
    config.resolve.alias
    .set("assets",resolve("@/assets"))
    .set("components",resolve("@/components"))
    .set("views",resolve("@/views"))
    .set("network",resolve("@/network"))
  }
}
