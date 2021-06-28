/*
 * @Author: your name
 * @Date: 2021-06-07 16:55:51
 * @LastEditTime: 2021-06-27 09:54:33
 * @LastEditors: 龙朝敏
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\main.js
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { myRequest } from "@/network/getRequest";
import VueLazyload from 'vue-lazyload'
Vue.prototype.$myRequest = myRequest
Vue.use(VueLazyload, {
  error: require('./assets/img/error.webp'),
  loading: require('./assets/img/loading.gif')
  })

Vue.config.productionTip = false

new Vue({

  router,
  store,
  render: h => h(App)
}).$mount('#app')
