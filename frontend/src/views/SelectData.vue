<template>

    <body>
        <!-- 总容器 -->
        <div class="wrapper">
            <header>
                <i class="fa fa-angle-left" onclick="history.go(-1)"></i>
                <p>选择体检日期</p>
                <div></div>
            </header>
            <div class="top-ban"></div>

            <section>
                <div class="date-box">
                    <i class="fa fa-caret-left" @click="substractMonth"></i>
                    <p>{{ year }}年{{ month }}月</p>
                    <i class="fa fa-caret-right" @click="addMonth"></i>
                </div>
                <table>
                    <tr>
                        <th>日</th>
                        <th>一</th>
                        <th>二</th>
                        <th>三</th>
                        <th>四</th>
                        <th>五</th>
                        <th>六</th>
                    </tr>
                </table>
                <ul>
                    <li v-for="(calendar, index) in calendarArr" :key="calendar.ymd">
                        <p :class="{ pselect: calendar.selected == 1, fontcolor: calendar.remainder != null && calendar.remainder != 0 }"
                            @click="selectDay(index)">{{ calendar.day }}
                        </p>
                        <p>{{ calendar.remainder != 0 && calendar.remainder != null ? "余" + calendar.remainder : "" }}
                        </p>
                    </li>
                </ul>
            </section>
            <div class="bottom-btn">
                <div></div>
                <div @click="toConfirmOrder">下一步</div>
            </div>

            <div class="bottom-ban"></div>
            <global-footer></global-footer>
        </div>
    </body>
</template>
<script>
import { reactive, toRefs } from 'vue';
import axios from '../router/axios';
import { useRouter, useRoute } from "vue-router";
export default {
    setup() {
        let date = new Date();
        const router = useRouter();
        const route = useRoute();
        const state = reactive({
            calendarArr: [],
            month: date.getMonth() + 1,
            year: date.getFullYear(),
            hpid: route.query.hpid,
            smId: route.query.smid,
            selectDay: ""
        });
        getcalendar();
        function getcalendar() {
            axios.post('/api/calendar/listcalendar', {
                hpid: state.hpid,
                month: state.month,
                year: state.year
            })
                .then(function (response) {
                    // 成功处理
                    state.calendarArr = response.data.data;
                    for (let i = 0; i < state.calendarArr.length; i++) {
                        if (
                            state.calendarArr[i].ymd != null &&
                            state.calendarArr[i].ymd != ""
                        ) {
                            state.calendarArr[i].day = parseInt(
                                state.calendarArr[i].ymd.substring(8, state.calendarArr[i].ymd.length)); // 2025-04-02  02  //怎样获取日
                        }
                        //再写一个if语句:给可以预约的日期加多一个属性，用来区分是否被选中
                        if (state.calendarArr[i].remainder != null && state.calendarArr[i].remainder != 0) {
                            state.calendarArr[i].selected = 0;  //默认未选中
                            //如果是当日+1，默认选中
                        } if (state.calendarArr[i].ymd == new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().substring(0, 10)) {
                            state.calendarArr[i].selected = 1;  //默认选中后一天
                        }
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
        function selectDay(index) {
            //做个判定：没有预约的号了，那么return
            if (state.calendarArr[index].remainder == null || state.calendarArr[index].remainder == 0) {
                return;
            }
            //什么叫做选中：
            //先清空所有日期的选中样式
            for (let i = 0; i < state.calendarArr.length; i++) {
                state.calendarArr[i].selected = 0;
            }
            state.calendarArr[index].selected = 1;
            state.selectDay = state.calendarArr[index].ymd;   //2025-04-11
        }
        //切换月份函数
        function substractMonth() {
            if (state.month > 1) {
                state.month--;
            } else {
                state.year--;
                state.month = 12;
            }
            getcalendar();
        }

        function addMonth() {
            if (state.month < 12) {
                state.month++;
            } else {
                state.year++;
                state.month = 1;
            }
            getcalendar();
        }

        function toConfirmOrder() {
            if (state.selectDay == "") {
                alert("请先选中预约日期");
                return;
            }
            router.push({ path: "/confirmorder", query: { hpId: state.hpid, smId: state.smId, selectDay: state.selectDay } });
        }
        function toindex() {
            router.push("/index")
        }
        function topersonal() {
            router.push("/personal")
        }
        return {
            ...toRefs(state),
            toConfirmOrder,
            toindex,
            topersonal,
            getcalendar,
            selectDay,
            addMonth,
            substractMonth
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
    width: 82vw;
    margin: 0 auto;
    margin-top: 12vw;
}

section .date-box {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 4.5vw;
    font-weight: 600;
}

section .date-box p {
    color: #000;
}

section .date-box i {
    color: #999;
    user-select: none;
    cursor: pointer;
}

section table {
    width: 100%;
    font-size: 3.8vw;
}

section table tr th {
    text-align: center;
    font-weight: 600;
    line-height: 12vw;
}

section ul {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
}

section ul li {
    width: 14.28%;
    height: 12vw;
    font-weight: 600;
    color: #999;
}

section ul li p:first-child {
    width: 6vw;
    height: 6vw;
    margin: 0 auto;
    border-radius: 3vw;

    display: flex;
    justify-content: center;
    align-items: center;

    font-size: 4.2vw;

    user-select: none;
    cursor: pointer;
}

section ul li p:last-child {
    font-size: 3vw;
    font-weight: 300;
    text-align: center;
    user-select: none;
}

.fontcolor {
    color: #333;
}

.pselect {
    background-color: #FB902B;
    color: #FFF;
}

/*********************** bottom-btn ***********************/
.bottom-btn {
    width: 100%;
    height: 12vw;
    background-color: #FFF;

    position: fixed;
    left: 0;
    bottom: 14.2vw;

    display: flex;
}

.bottom-btn div:first-child {
    flex: 2;
}

.bottom-btn div:last-child {
    flex: 1;
    background-color: #117C94;
    text-align: center;
    line-height: 12vw;
    font-size: 5vw;
    color: #FFF;

    user-select: none;
    cursor: pointer;
}
</style>