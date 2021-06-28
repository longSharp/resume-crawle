<!--
 * @Author: your name
 * @Date: 2021-06-08 15:12:22
 * @LastEditTime: 2021-06-11 14:58:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\views\home.vue
-->
<template>
  <div class="content">
    <main-tab-bar></main-tab-bar>
    <main-resume-list></main-resume-list>
    <main-page-btn></main-page-btn>
  </div>
</template>

<script>
import MainResumeList from "@/components/content/mainResumeList.vue";
import MainPageBtn from "@/components/content/mainPageBtn.vue";
import MainTabBar from "@/components/content/maintabbar.vue";

export default {
  name: "Home",
  components: {
    MainResumeList,
    MainPageBtn,
    MainTabBar,
  },
};
</script>

<style scoped>
</style>
