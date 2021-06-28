<!--
 * @Author: your name
 * @Date: 2021-06-08 15:16:36
 * @LastEditTime: 2021-06-27 22:17:10
 * @LastEditors: 龙朝敏
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\content\mainResumeList.vue
-->
<template>
  <resume-list>
    <resume-list-item
      class="item"
      v-for="content in this.$store.state.contentList"
      :key="content.id"
    >
      <div
        class="item-img"
        slot="resumeListItemImg"
        @click="goDetailed(content.id)"
      >
        <img v-lazy="content.url" :key="content.id"/>
      </div>
      <div class="item-title" slot="resumeListItemTilt">
        <p>{{ content.title }}</p>
      </div>
    </resume-list-item>
  </resume-list>
</template>

<script>
import ResumeList from "@/components/common/resumeList/resumeList";
import ResumeListItem from "@/components/common/resumeList/resumeListItem";

export default {
  name: "mainResumeList",
  data() {
    return {};
  },
  components: {
    ResumeList,
    ResumeListItem,
  },
  computed: {},
  created() {},
  methods: {
    goDetailed(id) {
      window.localStorage.setItem("tid", id);
      this.$router.push("detailedPage");
    },
  },
  mounted() {},
};
</script>

<style scoped>

.resumeListItemImg{
  width: 100%;
  height: 86%;
}

.item-img{
  width: 100%;
  height: 100%
}

.item-img img{
  display: block;
  width: 90%;
  height: 90%;
}
</style>
