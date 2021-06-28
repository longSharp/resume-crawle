/*
 * @Author: 何友钦
 * @Date: 2021-06-10 20:41:04
 * @LastEditTime: 2021-06-10 22:19:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \shopping-projectd:\学习项目\resume\crawler\src\network\getRequest.js
 */
import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)

const BASE_URL = "http://localhost:9428"
export const myRequest = (options => {
  return axios({
    url: BASE_URL + options.url,
    method: options.method || "get",
    params: options.data || {},
    success: (res) => {
      if (res.statusCode !== 200) {
        return uni.showToast({
          title: "获取数据失败"
        })
      }
      resolve(res)
    },
    fail: (err) => {
      uni.showToast({
        title: "请求接口失败"
      })
      reject(err)
    }
  })
})


