import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'; // 引入路由配置
import 'font-awesome/css/font-awesome.min.css';
import Footer from './components/Footer.vue';

const app = createApp(App);
app.use(router);
app.component('global-footer', Footer) // 使用路由
app.mount('#app')