<!--
 * @Author: your name
 * @Date: 2021-06-08 15:20:42
 * @LastEditTime: 2021-06-27 22:19:54
 * @LastEditors: 龙朝敏
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\common\resumeList\resumeListItem.vue
-->
<template>
  <div class="resumeListItem">
    <div class="resumeListItemImg">
      <slot name="resumeListItemImg"></slot>
    </div>
    <div class="resumeListItemTitle">
      <slot name="resumeListItemTilt"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "resumeListItem",
};
</script>

<style scoped>
.resumeListItem {
  cursor: pointer;
  box-sizing: border-box;
  width: 18%;
  height: 350px;
  text-align: center;
  font-size: 14px;
  margin: 10px;
  background: rgb(255, 255, 255);
  display: flex;
  flex-direction: column;
}

.resumeListItem:hover {
  box-shadow: -1px 1px 3px 0px #000;
}

/* .resumeListItemImg {
  height: 60%;
} */

.resumeListItemTitle {
height: 20%;
}
</style>
