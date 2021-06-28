<!--
 * @Author: your name
 * @Date: 2021-06-08 16:34:47
 * @LastEditTime: 2021-06-27 13:16:46
 * @LastEditors: 龙朝敏
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\content\mainDetailedPage.vue
-->
<template>
  <div class="mainDetailedPage">
    <div class="title">
      <!-- <button>返回主页</button> -->
      <span class="returnHome" @click="returnHome()">返回主页</span>
      <span>{{ this.$store.state.detailedList.title }}</span>
      <a :href="this.$store.state.detailedList.downloadUrl" class="btn"
        >
        <span>点击下载</span>
      </a>
    </div>
    <div class="divider"></div>
    <img
      class="content"
      v-for="(item, index) in this.$store.state.detailedList.imgUrl"
      :key="index"
      v-lazy="item"
      alt=""
    />
  </div>
</template>

<script>
export default {
  name: "MainDetailedPage",
  data() {
    return {};
  },
  created() {
    this.getData();
  },
  methods: {
    returnHome() {
      this.$router.push("home");
    },
    getData() {
      const id = window.localStorage.getItem("tid");
      const res = this.$myRequest({
        url: "/con/find/tid",
        data: {
          tid: id,
        },
      }).then((res) => {
        this.$store.commit("getDetailed", res.data.list);
      });
    },
  },
  mounted() {},
};
</script>

<style scoped>
.mainDetailedPage {
  width: 90%;
  margin: 0 auto;
}

.title {
  width: 100%;
  text-align: center;
  font-size: 30px;
  margin: 10px;
  display: flex;
  flex: 1;
}
.returnHome {
  width: 30%;
  height: 30px;
  line-height: 30px;
  font-size: 25px;
  margin-top: 10px;
}
.title p {
  vertical-align: middle;
  height: 30px;
  line-height: 30px;
}
.divider {
  width: 100%;
  height: 3px;
  background: linear-gradient(to left,rgb(123, 234, 241) 0%,rgb(54, 199, 243) 50%,rgb(227, 109, 238) 100%);
}

.btn {
  cursor: pointer;
  margin-left: 20px;
}

.btn span{
  display: inline-block;
  font-size: 12px;
  text-align: center;
  line-height: 24px;
  color: aliceblue;
  width: 92px;
  height: 24px;
  border-radius: 15px;
  background: linear-gradient(to left,rgb(71, 192, 240) 0%,rgb(29, 236, 236) 100%);
}

.content {
  display: block;
  margin: 20px auto;
  overflow: auto;
}
</style>
