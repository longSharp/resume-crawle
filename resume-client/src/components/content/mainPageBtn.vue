<!--
 * @Author: your name
 * @Date: 2021-06-08 16:00:17
 * @LastEditTime: 2021-06-17 18:21:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\crawler\src\components\content\mainPageBtn.vue
-->
<template>
  <page-btn>
    <div @click="upPage" slot="upPage">上一页</div>
    <div
      slot="pageNum"
      class="pageItem"
      v-for="(page, index1) in pageList"
      :key="index1"
      @click="switchPage(index1)"
      :class="{'selected': index ==index1}"
    >
      {{ page }}
    </div>
    <div @click="downPage" slot="downPage">下一页</div>
  </page-btn>
</template>

<script>
import PageBtn from "@/components/common/pageBtn/pageBtn.vue";
export default {
  name: "mainPageBtn",
  data() {
    return {
      pageList: [1, 2, 3, 4],
      index: 0,
      pageSize: 3,
    };
  },
  components: {
    PageBtn,
  },
  computed: {},
  methods: {
    switchPage(index) {
      this.index = index;
      if (index > this.pageSize) {
        this.index = this.pageSize;
        index = this.pageSize;
      }
      const limit = window.localStorage.getItem("limit");
      const page = index * limit;
      const typeId = window.localStorage.getItem("typeId");
      const res = this.$myRequest({
        url: "/resume/res/page/id",
        data: {
          typeId: typeId,
          page: page,
          limit: limit,
        },
      }).then((res) => {
        console.log(res);
        this.$store.commit("contentList", {
          list: res.data.list,
          activeState: typeId,
        });
      });
    },
    upPage() {
      const limit = window.localStorage.getItem("limit");
      let page = "";
      let num = this.index;
      if (this.index <= 0) {
        page = 0 * limit;
        this.index = num;
      } else {
        page = limit * (this.index - 1);
        this.index = num - 1;
      }
      const typeId = window.localStorage.getItem("typeId");
      console.log(limit);
      console.log(page);
      console.log(typeId);
      const res = this.$myRequest({
        url: "/resume/res/page/id",
        data: {
          typeId: typeId,
          page: page,
          limit: limit,
        },
      }).then((res) => {
        this.$store.commit("contentList", {
          list: res.data.list,
          activeState: typeId,
        });
      });
    },
    downPage() {
      const limit = window.localStorage.getItem("limit");
      let page = "";
      let num = this.index;
       if (this.index > this.pageSize) {
        page = this.pageSize * limit;
        this.index = num;
      } else {
        page = limit * (this.index + 1);
        this.index = num + 1;
      }
      this.index = num + 1;
      const typeId = window.localStorage.getItem("typeId");
      const res = this.$myRequest({
        url: "/resume/res/page/id",
        data: {
          typeId: typeId,
          page: page,
          limit: limit,
        },
      }).then((res) => {
        this.pageSize = res.data.pageSize;
        this.$store.commit("contentList", {
          list: res.data.list,
          activeState: typeId,
        });
      });
    },
  },
};
</script>

<style scoped>
.pageItem {
  width: 25%;
  padding: 10px;
  border-right: 1px solid rgb(209, 209, 209);
}
.pageItem:last-of-type {
  border: none;
}
.pageItem:hover {
  background-color: rgb(28, 142, 255);
  color: #fff;
}

.selected {
  background-color: rgb(28, 142, 255);
  color: #fff;
}
</style>
