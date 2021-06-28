<!--
 * @Author: your name
 * @Date: 2021-06-08 15:16:09
 * @LastEditTime: 2021-06-13 17:42:33
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit00px
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\common\resumeList\resumeList.vue
-->
<template>
  <div class="resumeList">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "ResumeList",
};
</script>

<style scoped>
.resumeList {
  width: 90%;
  height: 100%;
  background: rgb(247,247,247);
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin: 0 auto;
  padding-top: 15%;
}



</style>
