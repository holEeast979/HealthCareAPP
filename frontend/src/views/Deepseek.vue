<template>
    <div class="chat-container">
        <div class="chat-header">
            <!-- <i class="fa fa-angle-left" onclick="history.go(-1)"></i> -->
            <p onclick="history.go(-1)">返回</p>
            <h2 style="text-align: center;">智能医师</h2>
        </div>
        <div class="chat-history" ref="chatHistory">
            <div class="message" :class="{ ai: !msg.isUser, user: msg.isUser }" v-for="(msg, index) in messages"
                :key="index">
                <div class="avatar">{{ msg.isUser ? '用户' : 'AI' }}</div>
                <div class="content">{{ msg.text }}</div>
            </div>
        </div>
        <div class="input-area">
            <input type="text" placeholder="输入你的问题..." v-model="userMessage" @keyup.enter="sendMessage" />
            <button @click="sendMessage">发送</button>
        </div>
        <global-footer></global-footer>
    </div>
</template>

<script>
import { reactive, toRefs } from 'vue';
import axios from '../router/axios';
export default {
    setup() {
        const state = reactive({
            messages: [
                { text: '你好！我是康中康智能体检报告检查助手，有什么可以帮到你吗？', isUser: false }
            ],
            userMessage: "", // 用户输入的消息
        });
        function deepseekrespon() {
            axios.post('/api/deepseek/getDeepSeekResponse', {
                message: state.userMessage
            })
               .then(function (response) {
                    // 成功处理
                    console.log(response);
                    if (response.data.code == 1) {
                        setTimeout(() => {
                            const aiReply = response.data.data;
                            state.messages.push({ text: aiReply, isUser: false });
                        });
                    } else {
                        setTimeout(() => {
                            const aiReply = "服务器繁忙，请稍后再试！";
                            state.messages.push({ text: aiReply, isUser: false });
                        });
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
        function sendMessage() {
            if (state.userMessage.trim() !== "") {
                // 将用户消息添加到聊天记录
                state.messages.push({ text: state.userMessage, isUser: true });
                deepseekrespon();
                state.userMessage = "";
            }
        }

        return {
            ...toRefs(state),
            sendMessage
        };
    }
};
</script>


<style scoped>
.chat-container {
    max-width: 800px;
    margin: 0 auto;
    height: 100vh;
    display: flex;
    flex-direction: column;
    border: 1px solid #ddd;
}
.chat-header {
  display: flex;
  align-items: center; /* 垂直居中 */
  padding: 15px;
  background: #003e6d; /* 示例背景颜色 */
  color: white; /* 示例文字颜色 */
}

.chat-header p {
  margin-right: auto; /* 推送到最左侧 */
  cursor: pointer;
}


.chat-history {
    flex: 1;
    overflow-y: auto;
    padding: 15px;
    background: #f9f9f9;
}

.message {
    display: flex;
    margin-bottom: 15px;
}

.message .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #4ca3af;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px;
    flex-shrink: 0;
}

.message .content {
    padding: 10px 15px;
    border-radius: 18px;
    max-width: 70%;
}

.message.ai .content {
    background: #e3f2fd;
}

.message.user {
    flex-direction: row-reverse;
}

.message.user .avatar {
    background: #2196F3;
    margin-right: 0;
    margin-left: 10px;
}

.message.user .content {
    background: #f1f1f1;
}

.input-area {
    display: flex;
    padding: 15px;
    background: white;
    border-top: 1px solid #ddd;
    margin-bottom: 40px;
    /* 新增这一行 */
}

input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 20px;
    margin-right: 10px;
}

button {
    padding: 10px 20px;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 20px;
    cursor: pointer;
}

button:hover {
    background: #45a049;
}
</style>    