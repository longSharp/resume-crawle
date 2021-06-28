<template>
  <div class="tab-bar-nav">
    <slot name="nav"></slot>
  </div>
</template>

<script>
export default {
  name: "tabbarnav",
};
</script>

<style scoped>
.tab-bar-nav {
  width: 100%;
}
</style>
