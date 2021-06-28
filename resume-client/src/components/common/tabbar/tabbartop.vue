<!--
 * @Author: your name
 * @Date: 2021-06-07 17:42:44
 * @LastEditTime: 2021-06-11 10:39:25
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\common\tabbar\tabbartop.vue
-->
<template>
  <div class="tab-bar-top">
    <div class="top-left"><slot name="top-left"></slot></div>
    <div class="top-center"><slot name="top-center"></slot></div>
    <div class="top-right"><slot name="top-right"></slot></div>
  </div>
</template>

<script>
export default {
  name: "tabbartop",
};
</script>

<style scoped>
.tab-bar-top {
  width: 100%;
  display: flex;
  flex: 1;
}

.tab-bar-top > div {
  height: 100px;
}
.top-center {
  width: 50%;
  position: relative;
}
.top-left,
.top-right {
  width: 25%;
  position: relative;
}
</style>
