<template>

    <body>
        <!-- 总容器 -->
        <div class="wrapper">
            <header>
                <i class="fa fa-angle-left" onclick="history.go(-1)"></i>
                <p>注册</p>
                <div></div>
            </header>
            <div class="top-ban"></div>
            <h1>欢迎注册</h1>
            <table>
                <tr>
                    <td>手机号码</td>
                    <td><input type="text" placeholder="请输入手机号码" v-model="user.userId"></td>
                </tr>
                <tr>
                    <td>真实姓名</td>
                    <td><input type="text" placeholder="真实姓名便于查看体检报告" v-model="user.realname"></td>
                </tr>
                <tr>
                    <td>生日</td>
                    <td><input type="date" v-model="user.birthday"></td>
                </tr>
                <tr>
                    <td>性别</td>
                    <td>
                        <input type="radio" value="1" v-model="user.sex">男
                        <input type="radio" value="0" v-model="user.sex">女
                    </td>
                </tr>
                <tr>
                    <td>身份证号</td>
                    <td><input type="text" placeholder="请输入身份证号" v-model="user.identitycard"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input type="password" placeholder="请输入密码" v-model="user.password"></td>
                </tr>
                <tr>
                    <td>确认密码</td>
                    <td><input type="password" placeholder="请再次输入密码" v-model="repassword" show-password></td>
                </tr>
            </table>
            <div class="btn" @click="register">完成</div>
        </div>
    </body>
</template>

<script>
import { reactive, toRefs } from 'vue';
import axios from 'axios';
import { useRouter } from "vue-router";
export default {
    setup() {
        const router = useRouter();
        const state = reactive({
            user: {
                userId: '',
                password: '',
                realname: '',
                sex: 0,
                identitycard: '',
                birthday: '',
                usertype: 1,

            },
            repassword: '',
        });
        function register() {
            if (state.user.userId === '' || state.user.password === '') {
                alert('请输入账号或密码')
                return;
            }
            console.log(state.user.password)
            console.log(state.repassword)
            if (state.user.password !== state.repassword) {
                alert('两次密码不一致')
                return;
            }
            console.log(state.user)
            axios.post('api/user/register', {
                userid: state.user.userId,
                password: state.user.password,
                realname: state.user.realname,
                sex: state.user.sex,
                identitycard: state.user.identitycard,
                birthday: state.user.birthday,
                usertype: state.user.usertype,
            }).then(function (response) {
                // 成功处理
                const result = response.data;
                console.log(response.data);
                if (result.code === 1) {
                    alert("注册成功");
                    router.push("/");
                } else {
                    alert(response.data.msg);
                    state.user.userId = "";
                    state.user.password = "";
                    state.user.realname = "";
                    state.user.sex = "";
                    state.user.identitycard = "";
                    state.user.birthday = "";
                    state.user.usertype = "";
                    state.repassword = '';
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
        return {
            ...toRefs(state),
            register
        };
    }
}
</script>

<style scoped>
/*********************** 总容器 ***********************/
.wrapper {
    width: 100%;
    height: 100%;
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

/*********************** 标题部分 ***********************/
h1 {
    font-size: 7.4vw;
    font-weight: 500;
    box-sizing: border-box;
    padding: 0 3.6vw;
    margin-top: 6vw;
}

/*********************** common样式 ***********************/
.top-ban {
    width: 100%;
    height: 15.7vw;
}

/*********************** table ***********************/
table {
    width: 92.8vw;
    margin: 0 auto;
    margin-top: 5vw;
    border-collapse: collapse;

    font-size: 4.2vw;
}

table tr td {
    height: 12vw;
    border-bottom: solid 1px #DDD;
}

table tr td input {
    border: none;
    outline: none;
}

/*********************** btn ***********************/
.btn {
    width: 92.8vw;
    margin: 0 auto;
    height: 12vw;
    margin-top: 8vw;
    background-color: #137E92;
    border-radius: 2vw;
    color: #FFF;
    font-size: 5vw;
    text-align: center;
    line-height: 12vw;

    user-select: none;
    cursor: pointer;
}
</style>