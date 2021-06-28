<template>
  <div class="pageBtn">
    <div class="upPage page"><slot name="upPage"></slot></div>
    <div class="pageNum page"><slot name="pageNum"></slot></div>
    <div class="downPage page"><slot name="downPage"></slot></div>
  </div>
</template>

<script>
export default {
  name: "pageBtn",
};
</script>

<style scoped>
.pageBtn {
  width: 30%;
  display: flex;
  margin-left: 65%;
  margin-top: 20px;
}

.page {
  cursor: pointer;
  text-align: center;
  border: 1px solid rgb(209, 209, 209);
  color: #000;
}

.upPage:hover,
.downPage:hover {
  background-color: rgb(28, 142, 255);
  color: #fff;
}

.pageNum {
  width: 60%;
  display: flex;
  flex: 1;
  flex-direction: row;
}

.upPage,
.downPage {
  width: 20%;
  padding: 10px;

}
</style>
