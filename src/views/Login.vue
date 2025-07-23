<template>

    <body>
        <!-- 总容器 -->
        <div class="wrapper">
            <h1>体检预约-登录</h1>
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
                userId: '15678570952',
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
            
            // 使用模拟数据而不是调用API
            const mockResponse = {
                code: 1,
                data: {
                    token: 'mock-token-12345',
                    users: {
                        userid: state.user.userId,
                        realname: '模拟用户',
                        sex: '男',
                        age: 30,
                        phone: state.user.userId
                    }
                },
                msg: '登录成功'
            };
            
            // 处理成功响应
            const result = mockResponse;
            console.log(result.data);
            if (result.code == 1) {
                alert("登录成功");
                setSessionStorage('token', result.data.token);
                localStorage.setItem('token', result.data.token); // 同时存储在localStorage中
                setSessionStorage("users", result.data.users);
                
                // 获取重定向路径
                const redirect = router.currentRoute.value.query.redirect;
                if (redirect) {
                    // 如果存在重定向路径，跳转到该路径
                    router.push(redirect);
                } else {
                    // 否则默认跳转到首页
                    router.push("/index");
                }
            } else {
                alert(result.msg);
                state.user.userId = "",
                state.user.password = "";
            }
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
            // 模拟发送短信
            console.log('模拟发送短信到：', state.user.userId);
            // 不做任何API调用
        }
        function usesmsLogin() {
            // 使用模拟数据而不是调用API
            const mockResponse = {
                code: 1,
                data: {
                    token: 'mock-token-12345',
                    users: {
                        userid: state.user.userId,
                        realname: '模拟用户',
                        sex: '男',
                        age: 30,
                        phone: state.user.userId
                    }
                },
                msg: '登录成功'
            };
            
            // 处理成功响应
            const result = mockResponse;
            console.log(result);
            if (result.code == 1) {
                alert("登录成功");
                setSessionStorage('token', result.data.token);
                localStorage.setItem('token', result.data.token); // 同时存储在localStorage中
                setSessionStorage("users", result.data.users);
                
                // 获取重定向路径
                const redirect = router.currentRoute.value.query.redirect;
                if (redirect) {
                    // 如果存在重定向路径，跳转到该路径
                    router.push(redirect);
                } else {
                    // 否则默认跳转到首页
                    router.push("/index");
                }
            } else {
                alert(result.msg);
                state.user.userId = "",
                state.user.password = "";
            }
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
    font-size: 7vw;
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
    height: 14vw;
    border: solid 1px #CCC;
    border-radius: 2vw;
    margin-top: 5vw;
    display: flex;
    align-items: center;
}

section .input-sms {
    width: 100%;
    height: 14vw;
    border: solid 1px #CCC;
    border-radius: 2vw;
    margin-top: 5vw;
    display: flex;
    align-items: center;
    position: relative;
}

section .input-sms button {
    position: absolute;
    right: 0;
    top: 0;
    height: 100%;
    width: 40%;
    border: none;
    border-radius: 0 2vw 2vw 0;
    background-color: #70B0BC;
    color: white;
    font-size: 3.8vw;
    font-weight: bold;
}

section .input-box i {
    margin: 0 3.6vw;
    font-size: 6vw;
    color: #888;
}

section .input-sms i {
    margin: 0 3.6vw;
    font-size: 6vw;
    color: #888;
}

section .input-box input {
    border: none;
    outline: none;
    font-size: 4.2vw;
    width: 80%;
}

section .input-sms input {
    border: none;
    outline: none;
    font-size: 4.2vw;
    width: 50%;
}

section .reg-box {
    width: 100%;
    display: flex;
    justify-content: space-between;
    margin-top: 5vw;
    margin-bottom: 2vw;
}

section .smscss {
    font-size: 3.6vw;
    color: #2D727E;
    user-select: none;
    cursor: pointer;
}

section .tosms {
    display: inline;
}

section .reg-box p {
    font-size: 3.8vw;
    color: #2D727E;
    user-select: none;
    cursor: pointer;
}

section .button-box {
    width: 100%;
    height: 14vw;
    border-radius: 2vw;
    background-color: #70B0BC;
    text-align: center;
    line-height: 14vw;
    font-size: 5vw;
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
    font-size: 4vw;
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