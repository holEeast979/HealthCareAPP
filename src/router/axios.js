import axios from "axios";
import { useRouter } from "vue-router";
import { getSessionStorage } from "./common";
// 添加请求拦截器

const router = useRouter();
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    let token = getSessionStorage('token');
    if (token) {
         //前端发送请求给后端时，设置请求头
        config.headers.token  = token;
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

export default axios;