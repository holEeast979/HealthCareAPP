import axios from "axios";
import { getSessionStorage } from "./common";
// 添加请求拦截器

axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    let token = localStorage.getItem('token') || getSessionStorage('token');
    if (token) {
        //前端发送请求给后端时，设置请求头
        config.headers.token = token;
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

// 添加响应拦截器，处理可能的错误
axios.interceptors.response.use(
    response => {
        // 对响应数据做点什么
        return response;
    },
    error => {
        // 对响应错误做点什么
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);

export default axios;