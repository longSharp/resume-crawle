<!--
 * @Author: your name
 * @Date: 2021-06-07 17:18:25
 * @LastEditTime: 2021-06-11 11:27:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\common\tabbar\tabbar.vue
-->
<template>
  <div id="tab-bar">
    <slot name="tab-bar-top"></slot>
    <slot name="tab-bar-nav"></slot>
  </div>
</template>

<script>
export default {
  name: "tabbar",

  methods: {},
};
</script>

<style  scoped>
#tab-bar {
  width: 100%;
  position: fixed;
  top: 0;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  flex: 1;
  background-color: #fff;
}
</style>
