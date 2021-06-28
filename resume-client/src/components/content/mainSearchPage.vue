<!--
 * @Author: your name
 * @Date: 2021-06-12 18:34:08
 * @LastEditTime: 2021-06-28 08:16:51
 * @LastEditors: 龙朝敏
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\components\content\mainSearchPage.vue
-->
<template>
  <div class="mainSearchPage">
    <div class="top">
      <span class="returnHome" @click="returnHome()">返回主页</span>
      <p class="title">&nbsp;&nbsp;&nbsp;&nbsp;您搜索的结果如下：</p>
    </div>
    <div class="divider"></div>
    <div id="bottom">
      <div
        @click="goDetailed(content.id)"
        class="item"
        v-for="(content, index) in searchList"
        :key="content.id"
      >
        <div
          v-show="index < 16"
          class="item-img"
          slot="resumeListItemImg"
          @click="goDetailed(content.id)"
        >
          <img v-lazy="content.url" />
        </div>
        <div v-if="index < 16" slot="resumeListItemTilt">
          <p class="item-title" v-html="content.title"></p>
        </div>
      </div>
    </div>
    <div class="btn">
      <div class="hover switchPage" @click="upPage" slot="upPage">上一页</div>
      <div class="btnItemBox">
        <div
          class="btnItem hover"
          v-for="(page, index) in pageList"
          :key="index"
          @click="switchPage(index)"
          :class="{ selected: index == btnIndex }"
        >
          {{ page }}
        </div>
      </div>
      <div class="hover switchPage" @click="downPage" slot="downPage">
        下一页
      </div>
    </div>
  </div>
</template>

<script>
import ResumeList from "@/components/common/resumeList/resumeList";
import ResumeListItem from "@/components/common/resumeList/resumeListItem";
import PageBtn from "@/components/common/pageBtn/pageBtn.vue";
export default {
  components: {
    ResumeList,
    ResumeListItem,
    PageBtn,
  },
  data() {
    return {
      searchList: [],
      pageList: [1, 2, 3, 4],
      searchPage: 0,
      btnIndex: 0,
    };
  },
  created() {
    this.getSearchList();
  },
  methods: {
    returnHome() {
      this.$router.push("home");
    },
    getSearchList() {
      this.searchList = JSON.parse(window.localStorage.getItem("dataShowList"));
    },
    goDetailed(id) {
      console.log(id);
      window.localStorage.setItem("tid", id);
      this.$router.push("/detailedPage");
    },
    upPage() {
      const searchLimit = window.localStorage.getItem("searchLimit");
      const message = window.localStorage.getItem("message");
      const pageSize = window.localStorage.getItem("pageSize");
      const num = this.searchPage;
      let searchPage = "";
      if (this.searchPage >= 1) {
        searchPage = (this.searchPage - 1) * searchLimit;
      } else {
        searchPage = 0 * searchLimit;
      }
      this.searchPage = num - 1;
      const res = this.$myRequest({
        url: "/resume/res/title",
        data: {
          title: message,
          page: searchPage,
          limit: searchLimit,
        },
      }).then((res) => {
        console.log(res);
        this.searchList = res.data.list;
        if (this.searchPage <= 0) {
          this.btnIndex = 0;
        }else {
           this.btnIndex = this.searchPage
        }
      });
    },
    downPage() {
      const searchLimit = window.localStorage.getItem("searchLimit");
      const message = window.localStorage.getItem("message");
      const pageSize = window.localStorage.getItem("pageSize");
      const num = this.searchPage;
      let searchPage = "";
      if (this.searchPage <= pageSize) {
        searchPage = (this.searchPage + 1) * searchLimit;
      } else {
        searchPage = pageSize * searchLimit;
      }
      this.searchPage = num + 1;
      const res = this.$myRequest({
        url: "/resume/res/title",
        data: {
          title: message,
          page: searchPage,
          limit: searchLimit,
        },
      }).then((res) => {
        this.searchList = res.data.list;
        if (this.searchPage > 3) {
          this.btnIndex = 3;
        }else {
           this.btnIndex = this.searchPage
        }
      });
    },
    switchPage(index) {
      const searchLimit = window.localStorage.getItem("searchLimit");
      const message = window.localStorage.getItem("message");
      this.searchPage = index;
      const searchPage = index * searchLimit;
      const res = this.$myRequest({
        url: "/resume/res/title",
        data: {
          title: message,
          page: searchPage,
          limit: searchLimit,
        },
      }).then((res) => {
        this.searchList = res.data.list;
        this.btnIndex = index;
      });
    },
  },
};
</script>

<style scoped>
.mainSearchPage {
  width: 90%;
  margin: 0 auto;
}

.divider {
  width: 100%;
  height: 3px;
  background: linear-gradient(to left,rgb(123, 234, 241) 0%,rgb(54, 199, 243) 50%,rgb(227, 109, 238) 100%);
  margin-top: 10px;
}

#bottom {
  width: 90%;
  margin: 0 auto;
  display: flex;
  margin-top: 5%;
  flex-direction: row;
  flex-wrap: wrap;
  background: #fff;
  margin-bottom: 30px;
  overflow: hidden;
}
#bottom div {
  padding-top: 0;
}

.item {
  width: 20%;
  margin-left: 5%;
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
}

.item:hover {
  box-shadow: -1px 1px 3px 0px #000;
}
.item-img {
  width: 100%;
}
.item-img img{
  width: 100%;
}
.item-title {
  height: 30px;
  line-height: 30px;
}

.returnHome {
  width: 30%;
  height: 30px;
  line-height: 30px;
  font-size: 25px;
  margin-top: 10px;
}
.title {
  vertical-align: middle;
  height: 30px;
  line-height: 30px;
  margin-top: 20px;
  font-size: 30px;
}
.hover:hover {
  background-color: rgb(28, 142, 255);
  color: #fff;
}

.btn {
  width: 40%;
  text-align: center;
  display: flex;
  flex-direction: row;
  border: 1px solid rgb(209, 209, 209);
  margin-left: 50%;
}

.switchPage {
  width: 25%;
  height: 40px;
  line-height: 40px;
}

.btnItemBox {
  width: 50%;
  height: 40px;
  line-height: 40px;
  display: flex;
  flex: 1;
}
.btnItem {
  overflow: hidden;
  width: 25%;
  border-right: 1px solid rgb(209, 209, 209);
  border-left: 1px solid rgb(209, 209, 209);
}

.selected {
  background-color: rgb(28, 142, 255);
  color: #fff;
}
</style>
