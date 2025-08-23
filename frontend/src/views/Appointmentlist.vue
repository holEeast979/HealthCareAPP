<template>

    <body>
        <!-- 总容器 -->
        <div class="wrapper">
            <header>
                <i class="fa fa-angle-left" onclick="history.go(-1)"></i>
                <p>我的预约</p>
                <div></div>
            </header>
            <div class="top-ban"></div>

            <ul>
                <li v-for="(od, index) in orderlist">
                    <div class="left">
                        <p>{{ od.orderdate }}</p>
                        <p>{{ od.name }}</p>
                    </div>
                    <div class="right" @click="toappointmentlist(index)">
                        取消预约
                    </div>
                </li>
            </ul>

            <div class="bottom-ban"></div>
            <global-footer></global-footer>
        </div>
    </body>
</template>

<script>
import { reactive, toRefs } from 'vue';
import axios from '../router/axios';
import { useRouter, useRoute } from "vue-router";
import { getSessionStorage } from '../router/common.js'
export default {
    setup() {
        const router = useRouter();
        const state = reactive({
            orderlist: [],
            ordersid: '',
            price: '',
        });
        orders()
        function orders() {
            axios.post('api/orders/getorders', {
                userid: getSessionStorage("users").userid,
            })
                .then(function (response) {
                    // 成功处理
                    console.log(response.data);
                    state.orderlist = response.data.data;
                    //时间戳转换
                    for (let i = 0; i < state.orderlist.length; i++) {
                        let timestamp = response.data.data[i].orderdate;
                        const formattedDate = formatDate(timestamp);
                        state.orderlist[i].orderdate = formattedDate;
                    }
                })
                .catch(function (error) {
                    // 错误处理
                    console.log(error);
                })
                .finally(function () {
                    // 总是执行
                });
        }
        function formatDate(timestamp) {
            const date = new Date(timestamp);
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            return `${year}-${month}-${day}`;
        }
        function toappointmentlist(index) {
            if (state.orderlist[index].isCancel == 0) {
                state.ordersid = state.orderlist[index].orderid;
                state.price = state.orderlist[index].price * 0.9;
                if (confirm("您的预约距离体检日期不足三天，只能退回支付金额的90%，确认要取消吗？")) {
                    axios.post('api/orders/updataOrderstate', {
                        orderid: state.orderlist[index].orderid,
                    })
                        .then(function (response) {
                            // 成功处理
                            console.log(response.data);
                            if (response.data.code == 1) {
                                router.push("/cancel");
                                toRefund(state.ordersid, state.price);
                            }
                        })
                        .catch(function (error) {
                            // 错误处理
                            console.log(error);
                        })
                        .finally(function () {
                            // 总是执行
                        });
                } else {
                    console.log("取消");
                }
            } else {
                state.ordersid = state.orderlist[index].orderid;
                state.price = state.orderlist[index].price;
                if (confirm("支付金额将全部退回支付账户，确认要取消吗？")) {
                    axios.post('api/orders/updataOrderstate', {
                        orderid: state.orderlist[index].orderid,
                    })
                        .then(function (response) {
                            // 成功处理
                            console.log(response.data);
                            if (response.data.code == 1) {
                                router.push("/cancel");
                                toRefund(state.ordersid, state.price);
                            }
                        })
                        .catch(function (error) {
                            // 错误处理
                            console.log(error);
                        })
                        .finally(function () {
                            // 总是执行
                        });
                } else {
                    console.log("取消");
                }
            }
        }
        function toRefund(orderid, price) {
            console.log(orderid, price);
            axios.post('/api/alipay/refund', {
                orderid: orderid,
                price: price
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(function (response) {
                    // 成功处理
                    console.log(response);
                })
                .catch(function (error) {
                    // 错误处理
                    console.log(error);
                })
                .finally(function () {
                    // 总是执行
                });
        }
        return {
            ...toRefs(state),
            orders,
            toappointmentlist
        };
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

/*********************** ul ***********************/
ul {
    width: 86vw;
    margin: 0 auto;
}

ul li {
    width: 100%;
    height: 14vw;
    border-bottom: solid 1px #EEE;

    display: flex;
    justify-content: space-between;
    align-items: center;
}

ul li .left {
    user-select: none;
    cursor: pointer;
}

ul li .left p:first-child {
    color: #555;
    font-size: 3.3vw;
}

ul li .left p:last-child {
    color: #333;
    font-size: 4.2vw;
    font-weight: 600;
}

ul li .right {
    font-size: 4vw;
    color: #E6A23C;
}
</style>