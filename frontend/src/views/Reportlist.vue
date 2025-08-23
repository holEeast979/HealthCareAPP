<template>
    <div class="wrapper">
        <header>
            <i class="fa fa-angle-left" onclick="history.go(-1)"></i>
            <p>健康档案</p>
            <div></div>
        </header>
        <div class="top-ban"></div>

        <section>
            <img src="../assets/image/report.png">
            <ul >
                <li v-for="(tjian,index) in tjianlist">
                    <div class="left">
                        <i class="fa fa-file-text-o"></i>
                        <div>
                            <p>{{ tjian.orderdate }} 体检报告</p>
                            <p>{{ tjian.name }}</p>
                        </div>
                    </div>
                    <div class="right" @click="totjian(index)">
                        <i class="fa fa-angle-right"></i>
                    </div>
                </li>
            </ul>
        </section>

        <div class="bottom-ban"></div>
        <global-footer></global-footer>
    </div>
</template>

<script>
import { reactive, toRefs } from 'vue';
import axios from 'axios';
// import router from '@/router';
import { useRouter } from "vue-router";
import { getSessionStorage } from '../router/common.js'
export default {
    setup() {
        const router = useRouter();
        const state = reactive({
            tjianlist:[],
        })
        tjianlist();
        function tjianlist() {
            axios.get('/api/orders/getTjianlist',{
                params: {
                    userid: getSessionStorage("users").userid
                }
            })
                .then(function (response) {
                    // 成功处理
                    const result = response.data;
                    if (result.code == 1) {
                        state.tjianlist = result.data; 
                    }
                    console.log("体检列表",state.tjianlist);
                })
                .catch(function (error) {
                    // 错误处理
                    console.log(error);
                })
                .finally(function () {
                    // 总是执行
                });
        }
        function totjian(index) {
            router.push({ 
                path: '/report', 
                query: { orderid: state.tjianlist[index].orderid } 
            });
        }
        return {
            ...toRefs(state),
            totjian
        }
    }
}

</script>

<style scoped>
/*********************** 总容器 ***********************/
.wrapper {
    width: 100%;
    height: 100%;
    background-color: #F9F9F9;
}

/*********************** header ***********************/
header {
    width: 100%;
    height: 15.7vw;
    background-color: #FFF;

    position: fixed;
    left: 0;
    top: 0;

    display: flex;
    align-items: center;
    justify-content: space-between;

    box-sizing: border-box;
    padding: 0 3.6vw;
}

header .fa {
    font-size: 8vw;
}

/*********************** footer ***********************/
footer {
    width: 100%;
    height: 14.2vw;
    box-sizing: border-box;
    border-top: solid 1px #E9E9E9;
    background-color: #FFF;

    position: fixed;
    left: 0;
    bottom: 0;
}

footer ul {
    width: 100%;
    height: 14.2vw;
    display: flex;
    align-items: center;
    justify-content: space-around;
}

footer ul li {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    font-size: 3vw;
    color: #999;

    user-select: none;
    cursor: pointer;
}

footer ul li .fa {
    font-size: 5vw;
}

/*********************** common样式 ***********************/
.top-ban {
    width: 100%;
    height: 15.7vw;
}

.bottom-ban {
    width: 100%;
    height: 14.2vw;
}

/*********************** section ***********************/
section {
    width: 100%;
}

section img {
    width: 100%;
    display: block;
}

section ul {
    width: 86vw;
    margin: 0 auto;
}

section ul li {
    width: 100%;
    height: 18vw;
    border-bottom: solid 1px #EEE;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

section ul li .left {
    display: flex;
    align-items: center;
}

section ul li .left i {
    font-size: 8vw;
    color: #6BB9B6;
    margin-right: 3vw;
}

section ul li .left p:first-child {
    color: #555;
    font-size: 4.2vw;
    font-weight: 600;
}

section ul li .left p:last-child {
    color: #999;
    font-size: 3.2vw;
    font-weight: 600;
    margin-top: 1vw;
}

section ul li .right {
    user-select: none;
    cursor: pointer;
}

section ul li .right i {
    color: #999;
    font-size: 6vw;
}
</style>