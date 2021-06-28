/*
 * @Author: your name
 * @Date: 2021-06-07 16:55:51
 * @LastEditTime: 2021-06-12 21:47:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\crawler\src\router\index.js
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
const Home = () => import("../views/home.vue")
const DetailedPage = () => import("../views/detailedPage.vue")
const SearchPage = () => import("../views/searchPage.vue")


const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: "home",
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/detailedPage',
    name: 'DetailedPage',
    component: DetailedPage
  },
  {
    path: '/searchPage',
    name: 'SearchPage',
    component: SearchPage
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
