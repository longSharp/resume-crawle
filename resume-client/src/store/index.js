/*
 * @Author: your name
 * @Date: 2021-06-07 16:55:51
 * @LastEditTime: 2021-06-11 14:28:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\store\index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    contentList: [],
    activeState: '0837a2395c7940eeab8b4a85a5244abf',
    detailedList: {}
  },
  mutations: {
    contentList(state, obj) {
      state.contentList = obj.list
      state.activeState = obj.activeState
    },
    getDetailed(state, obj) {
      obj.imgUrl = obj.imgUrl.split(";").filter(item => item !== "")
      state.detailedList = obj
    }
  },
  actions: {
  },
  modules: {
  }
})
