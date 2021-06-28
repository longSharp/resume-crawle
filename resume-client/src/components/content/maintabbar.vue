<template>
  <tab-bar>
    <tab-bar-top slot="tab-bar-top">
      <div slot="top-center">
        <input
          @keyup.enter="goSearch"
          @focus="show"
          @blur.prevent="hidden"
          @input="getInputeData"
          placeholder="请输入搜索内容..."
          v-model="message"
          type="text"
        />
        <span class="searchSpan" @click="goSearch"
          ><img src="~@/assets/img/icon/search.svg"
        /></span>
        <div v-show="dataShow" class="dataShow">
          <p
            class="hover"
            v-for="(item, index) in dataShowList"
            :key="index"
            @mousedown="goDetailed(item.id)"
            v-html="item.title"
          >
          </p>
          <div v-if="!showList" class="hidden">
            暂时没有此简历，请换个关键词
          </div>
        </div>
      </div>
      <div slot="top-right"></div>
    </tab-bar-top>
    <tab-bar-nav slot="tab-bar-nav">
      <div slot="nav">
        <ul class="titleItemUl">
          <li
            class="titleItemLi"
            v-for="title in titleList"
            :key="title.id"
            @click="contentList(title.id)"
            :class="{ active: title.id == $store.state.activeState }"
          >
            {{ title.typeName }}
          </li>
        </ul>
      </div>
    </tab-bar-nav>
  </tab-bar>
</template>

<script>
import TabBar from "@/components/common/tabbar/tabbar.vue";
import TabBarTop from "@/components/common/tabbar/tabbartop.vue";
import TabBarNav from "@/components/common/tabbar/tabbarnav.vue";
export default {
  data() {
    return {
      titleList: [],
      page: 0,
      limit: 20,
      typeId: "728b2310993a4a69a866abacfad35442",
      title: "",
      message: "",
      dataShow: false,
      dataShowList: [],
      showList: false,
      pageSize: 3,
      searchPage: 0,
      searchLimit: 16,
    };
  },
  created() {
    this.getTitleList();
    this.getDefaultList();
  },
  methods: {
    show() {
      this.dataShow = true;
    },
    hidden() {
      this.dataShow = false;
    },
    goSearch() {
      if (this.dataShowList.length !== 0) {
        window.localStorage.setItem(
          "dataShowList",
          JSON.stringify(this.dataShowList)
        );
        this.$router.push("searchPage");
      }
    },
    getTitleList() {
      const res = this.$myRequest({
        url: "/type/find/all",
      }).then((res) => {
        this.titleList = res.data.list;
      });
    },
    getInputeData() {
      if (this.message.trim() !== "") {
        const res = this.$myRequest({
          url: "/resume/res/title",
          data: {
            title: this.message,
            page: 0,
            limit: 16,
          },
        }).then((res) => {
          console.log(res)
          for (const list in res.data.list) {
            if (Object.hasOwnProperty.call(res.data.list, list)) {
              const element = res.data.list;
              this.dataShowList = element;
            }
          }
          window.localStorage.setItem("searchPage",this.searchPage)
          window.localStorage.setItem("searchLimit",this.searchLimit)
          window.localStorage.setItem("message",this.message)
          window.localStorage.setItem("pageSize",res.data.pageSize)
        });
        this.dataShow = true;
      } else {
        this.dataShowList = [];
        this.showList = true;
      }
    },
    getDownloadUrl(id) {
      const res = this.$myRequest({
        url: "/con/find/tid",
        data: {
          tid: id,
        },
      }).then((res) => {
        this.downloadUrl = res.data.list.downloadUrl;
      });
    },
    getDefaultList() {
      const res = this.$myRequest({
        url: "/resume/res/page/id",
        data: {
          typeId: this.typeId,
          page: this.page,
          limit: this.limit,
        },
      }).then((res) => {
        this.$store.commit("contentList", {
          list: res.data.list,
          activeState: this.typeId,
        });
      });
    },
    contentList(typeId) {
      const res = this.$myRequest({
        url: "/resume/res/page/id",
        data: {
          typeId: typeId,
          page: this.page,
          limit: this.limit,
        },
      }).then((res) => {
        this.$store.commit("contentList", {
          list: res.data.list,
          activeState: typeId,
        });
        window.localStorage.setItem("page", this.page);
        window.localStorage.setItem("pageSize", this.pageSize);
        window.localStorage.setItem("limit", this.limit);
        if (typeId == null) {
          window.localStorage.setItem("typeId", this.typeId);
        } else {
          window.localStorage.setItem("typeId", typeId);
        }
      });
    },
    goDetailed(id) {
      console.log(id);
      window.localStorage.setItem("tid", id);
      this.$router.push("detailedPage");
    },
  },
  components: {
    TabBar,
    TabBarNav,
    TabBarTop,
  },
};
</script>

<style scoped>
input {
  text-indent: 1em;
  width: 99%;
  height: 50px;
  font-size: 16px;
  border-radius: 20px;
  line-height: 25px;
  outline: none;
  position: absolute;
  top: 25px;
  border: 1px solid rgb(204, 204, 204);
}

span {
  display: inline-block;
  width: 20%;
  height: 51px;
  text-align: center;
  background-color: rgb(28, 142, 255);
  position: absolute;
  border: 1px solid rgb(204, 204, 204);
  top: 25px;
  right: 0;
  border-radius: 0 20px 20px 0;
}

.searchSpan{
  display: flex;
  justify-content: center;
  align-items: center
}

span img {
  width: 25%;
}

.dataShow {
  color: #fff;
  width: 78%;
  max-height: 275px;
  /* padding: 10px; */
  text-align: center;
  background-color: rgb(28, 142, 255);
  position: absolute;
  top: 75px;
  left: 2%;
  overflow: auto;
  border-radius: 0 0 10px 10px;
  padding-bottom: 20px;
}
.dataShow::-webkit-scrollbar {
  display: none;
}
.dataShow > p {
  display: block;
  width: 100%;
  height: 35px;
  line-height: 35px;
  margin-top: 5px;
}
.hover:hover {
  background-color: #fff;
  color: rgb(28, 142, 255);
}
.hidden {
  width: 100%;
  height: 30px;
  line-height: 30px;
}
.titleItemUl {
  width: 100%;
  display: flex;
  border-bottom: 2px solid rgb(28, 142, 255);
  padding-inline-start: 0;
  flex-direction: row;
  flex-wrap: wrap;
  list-style: none;
}
.titleItemLi {
  font: 12px/1.8 "Segoe UI", Tahoma, Arial, Microsoft YaHei;
  width: 14%;
  line-height: 30px;
  text-align: center;
  cursor: pointer;
  list-style: none;
  border-right: 1px solid rgb(244, 244, 244);
}
.titleItemLi:last-of-type {
  border-right: none;
}

.titleItemLi:hover {
  background-color: rgb(62, 138, 214);
  color: #fff;
}
.active {
  background-color: rgb(62, 138, 214);
  color: #fff;
}
</style>
