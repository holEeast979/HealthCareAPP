<template>

    <body>
        <!-- 总容器 -->
        <div class="wrapper">
            <h1>康中康体检预约-登录</h1>
            <section>
                <div class="input-box">
                    <i class="fa fa-user-o"></i>
                    <input type="text" placeholder="输入手机号" v-model="user.userId">
                </div>

                <div class="input-box" v-if="!showSmsLogin">
                    <i class="fa fa-lock"></i>
                    <input type="password" placeholder="输入登录密码" v-model="user.password">
                </div>

                <div class="input-sms" v-if="showSmsLogin">
                    <i class="fa fa-commenting-o"></i>
                    <input type="text" placeholder="验证码" v-model="sms">
                    <button id="sendCodeBtn" @click="startCountdown(); getSmsLogin()"
                        style="width: 40%; height: 12vw; right: 0;border: none;">获取验证码</button>
                </div>

                <div class="reg-box">
                    <p @click="toregister">注册</p>
                    <p>忘记密码？</p>
                </div>
                <div class="button-box" @click="showSmsLogin ? usesmsLogin() : login()">登录</div>
                <div class="reg-box">
                    <p class="smscss" @click="toSmsLogin">{{ showSmsLogin ? "账号密码登录" : "短信验证登录" }}</p>
                </div>
            </section>
            <footer>
                <div>
                    <div class="line"></div>
                    <p>有疑问请联系客服</p>
                    <div class="line"></div>
                </div>
                <p>4008-XXX-XXX</p>
            </footer>
        </div>
    </body>
</template>

<script>
import { reactive, toRefs } from 'vue';
import axios from 'axios';
// import router from '@/router';
import { useRouter } from "vue-router";
import { setSessionStorage } from '../router/common.js'
export default {
    setup() {
        const router = useRouter();
        const state = reactive({
            user: {
                userId: '13202302676',
                password: '123456'
            },
            showSmsLogin: false,
            sms: ""
        });
        function login() {
            if (state.user.userId === '' || state.user.password === '') {
                alert('请输入账号或密码')
                return;
            }
            axios.post('api/user/login', {
                userid: state.user.userId,
                password: state.user.password,
            })
                .then(function (response) {
                    // 处理成功响应
                    const result = response.data;
                    console.log(result.data);
                    if (result.code == 1) {
                        alert("登录成功");
                        setSessionStorage('token', result.data.token);
                        setSessionStorage("users", result.data.users);
                        router.push("/index")
                    } else {
                        alert(response.data.msg);
                        state.user.userId = "",
                            state.user.password = "";
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
        function toregister() {
            console.log("注册");
            router.push("/register")
        }
        function toSmsLogin() {
            state.showSmsLogin = !state.showSmsLogin;
        }
        function startCountdown() {
            // 设置倒计时时间，例如60秒
            let countdown = 60;
            // 更新按钮文本以显示倒计时
            function updateButtonText() {
                if (countdown > 0) {
                    // 更新按钮文本，例如："重新发送（60）"
                    document.getElementById('sendCodeBtn').innerText = `重新发送(${countdown})`;
                    countdown--; // 每秒减一
                } else {
                    // 倒计时结束，恢复按钮文本和功能
                    document.getElementById('sendCodeBtn').innerText = '发送验证码';
                    document.getElementById('sendCodeBtn').disabled = false;
                    clearInterval(intervalId);
                }
            }
            // 禁用按钮，防止重复点击
            document.getElementById('sendCodeBtn').disabled = true;
            // 开始倒计时
            updateButtonText();
            const intervalId = setInterval(updateButtonText, 1000);
        }
        function getSmsLogin() {
            axios.post('/api/sendSMS', {
                phone: state.user.userId,
            })
                .then(function (response) {
                    // 成功处理
                    console.log(response.data);
                })
                .catch(function (error) {
                    // 错误处理
                    console.log(error);
                })
                .finally(function () {
                    // 总是执行
                });
        }
        function usesmsLogin() {
            axios.get('/api/checkSMS', {
                // 这里的 params 应该是一个对象，包含电话号码和短信验证码
                params: {
                    phone: state.user.userId, // 电话号码
                    sms: state.sms // 短信验证码
                }
            })
                .then(function (response) {
                    // 成功处理
                    const result = response.data;
                    console.log(result);
                    if (result.code == 1) {
                        alert("登录成功");
                        setSessionStorage('token', result.data.token);
                        setSessionStorage("users", result.data.users);
                        router.push("/index")
                    } else {
                        alert(response.data.msg);
                        state.user.userId = "",
                        state.user.password = "";
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
            login,
            toregister,
            toSmsLogin,
            startCountdown,
            getSmsLogin,
            usesmsLogin
        };
    }
}
</script>

<style scoped>
/*********************** 总容器 ***********************/
.wrapper {
    width: 100%;
    height: 100%;
    background-image: linear-gradient(45deg, #266C9F, #266C9F, #7EB059);
    overflow: hidden;
}

/*********************** 标题部分 ***********************/
h1 {
    text-align: center;
    color: #FFF;
    font-size: 9.5vw;
    font-weight: 500;
    margin-top: 13vh;
    margin-bottom: 3vh;
}

/*********************** 内容部分 ***********************/
section {
    width: 86vw;
    margin: 0 auto;
    background-color: #FFF;
    border-radius: 2.4vw;
    box-sizing: border-box;
    padding: 6vw;
}

section .input-box {
    width: 100%;
    height: 12vw;
    border: solid 1px #CCC;
    border-radius: 2vw;
    margin-top: 5vw;

    display: flex;
    align-items: center;
}

section .input-sms {
    width: 100%;
    height: 12vw;
    border: solid 1px #CCC;
    border-radius: 2vw;
    margin-top: 5vw;
    display: flex;
    align-items: center;
    position: relative;
}

section .input-sms button {
    position: absolute;
    right: 10;
    top: 0;
    width: 100%;
}

section .input-box i {
    margin: 0 3.6vw;
    font-size: 5.4vw;
    color: #CCC;
}

section .input-sms i {
    margin: 0 3.6vw;
    font-size: 5.4vw;
    color: #CCC;
}


section .input-box input {
    border: none;
    outline: none;
}

section .input-sms input {
    border: none;
    outline: none;
}

section .reg-box {
    width: 100%;
    display: flex;
    justify-content: space-between;
    margin-top: 5vw;
    margin-bottom: 2vw;
}

section .smscss {
    font-size: 2.3vw;
    color: #2D727E;
    user-select: none;
    cursor: pointer;
}

section .tosms {
    display: inline;
}

section .reg-box p {
    font-size: 3.3vw;
    color: #2D727E;
    user-select: none;
    cursor: pointer;
}

section .button-box {
    width: 100%;
    height: 13vw;
    border-radius: 2vw;
    background-color: #70B0BC;

    text-align: center;
    line-height: 13vw;
    font-size: 4.6vw;
    color: #FFF;
    letter-spacing: 1vw;

    user-select: none;
    cursor: pointer;
}

/*********************** footer部分 ***********************/
footer {
    width: 86vw;
    margin: 0 auto;
    margin-top: 12vw;
    font-size: 3.8vw;
    color: #ffffff;
}

footer div {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

footer div .line {
    width: 22vw;
    height: 1px;
    background-color: #ffffff;
}

footer>p {
    text-align: center;
    margin-top: 2vw;
}

.smsbutton {}
</style>