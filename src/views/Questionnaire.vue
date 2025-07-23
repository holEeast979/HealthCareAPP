<template>
  <div class="questionnaire-container">
    <div class="questionnaire-card">
      <div v-if="!showResult">
        <div class="progress-container">
          <div class="progress-text">{{ currentQuestionIndex + 1 }}/{{ questions.length }}</div>
          <div class="progress-bar">
            <div class="progress-fill" :style="{ width: progressPercentage + '%' }"></div>
          </div>
        </div>
        
        <div class="question-container">
          <h2>{{ currentQuestion.text }}</h2>
          
          <div class="options-container">
            <template v-if="currentQuestion.type === 'radio'">
              <div 
                v-for="(option, index) in currentQuestion.options" 
                :key="index"
                class="option-item"
                :class="{ 'selected': currentAnswers[currentQuestionIndex] === option.value }"
                @click="selectOption(option.value)">
                {{ option.text }}
              </div>
            </template>
            
            <template v-if="currentQuestion.type === 'checkbox'">
              <div 
                v-for="(option, index) in currentQuestion.options" 
                :key="index"
                class="option-item"
                :class="{ 'selected': currentAnswers[currentQuestionIndex]?.includes(option.value) }"
                @click="toggleOption(option.value)">
                {{ option.text }}
              </div>
            </template>
            
            <template v-if="currentQuestion.type === 'number'">
              <input 
                type="number" 
                v-model="currentAnswers[currentQuestionIndex]" 
                class="number-input" 
                :placeholder="currentQuestion.placeholder || '请输入'" />
            </template>
          </div>
        </div>
        
        <div class="navigation-buttons">
          <button 
            class="nav-button back" 
            @click="prevQuestion" 
            :disabled="currentQuestionIndex === 0">
            上一步
          </button>
          <button 
            class="nav-button next" 
            @click="nextQuestion"
            :disabled="!canProceed">
            {{ currentQuestionIndex === questions.length - 1 ? '完成' : '下一步' }}
          </button>
        </div>
      </div>
      
      <div v-else class="result-container">
        <h2>为您推荐的体检套餐</h2>
        <div class="recommendation-reason">
          <p>根据您的问卷回答，我们为您精心推荐以下套餐:</p>
        </div>
        
        <div class="recommended-packages">
          <div 
            v-for="(item, index) in recommendedPackages" 
            :key="index"
            class="package-card">
            <div class="package-header">
              <h3>{{ item.name }}</h3>
              <div class="package-price">¥{{ item.price }}</div>
            </div>
            <div class="package-description">{{ item.description }}</div>
            <div class="recommendation-tag">推荐理由: {{ item.reason }}</div>
            <div class="package-actions">
              <button class="view-details-btn" @click="viewPackageDetails(item.id)">查看详情</button>
              <button class="add-cart-btn" @click="addToCart(item)">加入购物车</button>
            </div>
          </div>
        </div>
        
        <div class="action-buttons">
          <button @click="resetQuestionnaire" class="reset-button">重新开始</button>
          <button @click="goToMall" class="mall-button">前往商城</button>
          <button @click="goToHome" class="home-button">返回主页</button>
        </div>
      </div>
    </div>
    <!-- 添加底部导航 -->
    <Footer />
  </div>
</template>

<script>
import Footer from '@/components/Footer.vue';
import meal1 from '@/assets/image/meal1.jpg';
import meal2 from '@/assets/image/meal2.jpg';
import meal3 from '@/assets/image/meal3.jpg';
import meal4 from '@/assets/image/meal4.jpg';

export default {
  name: 'Questionnaire',
  components: {
    Footer
  },
  data() {
    return {
      currentQuestionIndex: 0,
      showResult: false,
      currentAnswers: {},
      questions: [
        {
          text: '您的性别是？',
          type: 'radio',
          options: [
            { text: '男', value: 'male' },
            { text: '女', value: 'female' }
          ],
          required: true
        },
        {
          text: '您的年龄是？',
          type: 'number',
          placeholder: '请输入您的年龄',
          required: true
        },
        {
          text: '您平时有吸烟习惯吗？',
          type: 'radio',
          options: [
            { text: '不吸烟', value: 'no' },
            { text: '偶尔吸烟', value: 'occasionally' },
            { text: '经常吸烟', value: 'regularly' }
          ],
          required: true
        },
        {
          text: '您平时有饮酒习惯吗？',
          type: 'radio',
          options: [
            { text: '不饮酒', value: 'no' },
            { text: '偶尔饮酒', value: 'occasionally' },
            { text: '经常饮酒', value: 'regularly' }
          ],
          required: true
        },
        {
          text: '您的家族中是否有以下疾病史？（可多选）',
          type: 'checkbox',
          options: [
            { text: '心脏病', value: 'heart' },
            { text: '糖尿病', value: 'diabetes' },
            { text: '高血压', value: 'hypertension' },
            { text: '癌症', value: 'cancer' },
            { text: '无', value: 'none' }
          ],
          required: true
        },
        {
          text: '您平时经常感到疲劳或精力不足吗？',
          type: 'radio',
          options: [
            { text: '从不', value: 'never' },
            { text: '偶尔', value: 'sometimes' },
            { text: '经常', value: 'often' }
          ],
          required: true
        },
        {
          text: '您在过去半年内是否做过体检？',
          type: 'radio',
          options: [
            { text: '是', value: 'yes' },
            { text: '否', value: 'no' }
          ],
          required: true
        }
      ],
      recommendedPackages: [],
      cartItems: [],
      mealImages: [meal1, meal2, meal3, meal4]
    }
  },
  computed: {
    currentQuestion() {
      return this.questions[this.currentQuestionIndex] || {}
    },
    progressPercentage() {
      return ((this.currentQuestionIndex + 1) / this.questions.length) * 100
    },
    canProceed() {
      const question = this.currentQuestion
      const answer = this.currentAnswers[this.currentQuestionIndex]
      
      if (!question.required) return true
      
      if (question.type === 'checkbox') {
        return Array.isArray(answer) && answer.length > 0
      }
      
      return answer !== undefined && answer !== ''
    }
  },
  methods: {
    selectOption(value) {
      this.currentAnswers[this.currentQuestionIndex] = value
    },
    toggleOption(value) {
      if (!Array.isArray(this.currentAnswers[this.currentQuestionIndex])) {
        this.currentAnswers[this.currentQuestionIndex] = []
      }
      
      const answers = this.currentAnswers[this.currentQuestionIndex]
      const index = answers.indexOf(value)
      
      if (index === -1) {
        // 如果选择了"无"，清空其他选项
        if (value === 'none') {
          this.currentAnswers[this.currentQuestionIndex] = ['none']
        } else {
          // 如果选择了其他选项，移除"无"
          const noneIndex = answers.indexOf('none')
          if (noneIndex !== -1) {
            answers.splice(noneIndex, 1)
          }
          answers.push(value)
        }
      } else {
        answers.splice(index, 1)
      }
    },
    prevQuestion() {
      if (this.currentQuestionIndex > 0) {
        this.currentQuestionIndex--
      }
    },
    nextQuestion() {
      if (this.currentQuestionIndex < this.questions.length - 1) {
        this.currentQuestionIndex++
      } else {
        this.generateRecommendations()
        this.showResult = true
      }
    },
    resetQuestionnaire() {
      this.currentQuestionIndex = 0
      this.currentAnswers = {}
      this.showResult = false
      this.recommendedPackages = []
    },
    goToMall() {
      this.$router.push('/mall')
    },
    goToHome() {
      this.$router.push('/')
    },
    viewPackageDetails(packageId) {
      this.$router.push({
        path: '/setmeal',
        query: { id: packageId }
      })
    },
    addToCart(item) {
      // 获取本地存储中的购物车数据
      let cartItems = JSON.parse(localStorage.getItem('shoppingCart') || '[]')
      
      // 检查商品是否已在购物车中
      const existingItemIndex = cartItems.findIndex(cartItem => cartItem.id === item.id)
      
      if (existingItemIndex !== -1) {
        // 如果已存在，更新商品（购物车页面没有处理数量，所以这里不增加数量）
        alert('该套餐已在购物车中')
        return
      } else {
        // 如果不存在，添加新商品
        cartItems.push({
          id: item.id,
          title: item.name,
          price: item.price,
          hospital: '推荐体检医院', // 添加默认医院名称
          image: this.mealImages[(item.id - 1) % 4], // 使用预导入的图片
          description: item.description
        })
      }
      
      // 更新本地存储
      localStorage.setItem('shoppingCart', JSON.stringify(cartItems))
      
      // 提示用户
      alert('已添加到购物车！')
    },
    generateRecommendations() {
      // 基于用户回答生成推荐套餐
      const gender = this.currentAnswers[0]
      const age = parseInt(this.currentAnswers[1])
      const smoking = this.currentAnswers[2]
      const drinking = this.currentAnswers[3]
      const familyHistory = this.currentAnswers[4] || []
      const fatigue = this.currentAnswers[5]
      const hadCheckup = this.currentAnswers[6]
      
      // 清空现有推荐
      this.recommendedPackages = []
      
      // 基础套餐 - 适合所有人
      const basicPackage = {
        id: 1,
        name: '基础健康套餐',
        price: 399,
        description: '包含血常规、尿常规、肝功能、肾功能、血脂等基础检查项目，适合所有人群的基础体检。',
        reason: '所有人群都适合进行基础体检，定期了解自己的健康状况'
      }
      
      // 根据性别、年龄和健康风险推荐套餐
      if (gender === 'male') {
        if (age >= 45 || smoking === 'regularly' || drinking === 'regularly') {
          this.recommendedPackages.push({
            id: 2,
            name: '男性综合套餐',
            price: 899,
            description: '包含基础检查、心脑血管检查、前列腺检查、肿瘤标志物等项目，适合中老年男性及有不良生活习惯的男性。',
            reason: age >= 45 ? '您的年龄已超过45岁，建议更全面的健康检查' : '您的生活习惯可能增加某些疾病风险'
          })
        }
      } else if (gender === 'female') {
        if (age >= 40) {
          this.recommendedPackages.push({
            id: 3,
            name: '女性综合套餐',
            price: 999,
            description: '包含基础检查、心脑血管检查、妇科检查、乳腺检查、骨密度等项目，适合中老年女性。',
            reason: '女性超过40岁，建议进行更全面的健康检查，特别是针对女性特有的健康风险'
          })
        }
      }
      
      // 根据家族病史推荐专项检查
      if (familyHistory.includes('heart') || familyHistory.includes('hypertension')) {
        this.recommendedPackages.push({
          id: 4,
          name: '心脑血管专项套餐',
          price: 699,
          description: '针对心脑血管疾病风险人群，包含心电图、动脉硬化检测、血脂全套、颈动脉彩超等检查项目。',
          reason: '您的家族有心脏病或高血压史，建议进行心脑血管专项检查'
        })
      }
      
      if (familyHistory.includes('diabetes')) {
        this.recommendedPackages.push({
          id: 5,
          name: '糖尿病风险筛查套餐',
          price: 599,
          description: '针对糖尿病风险人群，包含空腹血糖、糖化血红蛋白、胰岛素、尿微量白蛋白等项目。',
          reason: '您的家族有糖尿病史，建议进行糖尿病风险专项检查'
        })
      }
      
      if (familyHistory.includes('cancer')) {
        this.recommendedPackages.push({
          id: 6,
          name: '肿瘤标志物筛查套餐',
          price: 1299,
          description: '包含多种肿瘤标志物检测、影像学检查等，帮助早期发现潜在的肿瘤风险。',
          reason: '您的家族有癌症史，建议进行肿瘤风险专项筛查'
        })
      }
      
      // 如果没有特殊推荐，至少推荐基础套餐
      if (this.recommendedPackages.length === 0) {
        this.recommendedPackages.push(basicPackage)
      } else if (this.recommendedPackages.length > 3) {
        // 如果推荐太多，限制为前3个
        this.recommendedPackages = this.recommendedPackages.slice(0, 3)
      }
    }
  }
}
</script>

<style scoped>
.questionnaire-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 150px);
  background-color: #f5f7fa;
  padding: 10px;
}

.questionnaire-card {
  width: 100%;
  max-width: 600px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 0 auto;
}

.progress-container {
  margin-bottom: 30px;
}

.progress-text {
  text-align: center;
  margin-bottom: 10px;
  font-size: 16px;
  color: #666;
}

.progress-bar {
  height: 10px;
  background-color: #ebeef5;
  border-radius: 10px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background-color: #409eff;
  border-radius: 10px;
  transition: width 0.3s ease;
}

.question-container {
  margin-bottom: 30px;
}

.question-container h2 {
  font-size: 20px;
  margin-bottom: 20px;
  color: #333;
  font-weight: 600;
}

.options-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.option-item {
  padding: 15px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  font-size: 16px;
}

.option-item:hover {
  background-color: #f0f9ff;
  border-color: #b3d8ff;
}

.option-item.selected {
  background-color: #ecf5ff;
  border-color: #409eff;
  color: #409eff;
}

.number-input {
  width: 100%;
  padding: 15px;
  font-size: 16px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  outline: none;
  transition: border-color 0.3s;
}

.number-input:focus {
  border-color: #409eff;
}

.navigation-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.nav-button {
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.nav-button.back {
  background-color: #f4f4f5;
  color: #606266;
  border: 1px solid #dcdfe6;
}

.nav-button.back:hover:not(:disabled) {
  background-color: #e9e9eb;
}

.nav-button.next {
  background-color: #409eff;
  color: white;
  border: none;
}

.nav-button.next:hover:not(:disabled) {
  background-color: #66b1ff;
}

.nav-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* 结果页样式 */
.result-container {
  text-align: center;
}

.result-container h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.recommendation-reason {
  margin-bottom: 30px;
  color: #606266;
  font-size: 16px;
}

.recommended-packages {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 30px;
}

.package-card {
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 12px;
  background-color: #f9fafc;
  text-align: left;
}

.package-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.package-header h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
}

.package-price {
  font-size: 20px;
  font-weight: bold;
  color: #f56c6c;
}

.package-description {
  margin-bottom: 15px;
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
}

.recommendation-tag {
  margin-bottom: 15px;
  padding: 6px 10px;
  background-color: #f0f9eb;
  color: #67c23a;
  font-size: 14px;
  border-radius: 4px;
  display: inline-block;
}

.package-actions {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}

.view-details-btn, .add-cart-btn {
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 14px;
  border: none;
}

.view-details-btn {
  background-color: #409eff;
  color: white;
}

.view-details-btn:hover {
  background-color: #66b1ff;
}

.add-cart-btn {
  background-color: #67c23a;
  color: white;
}

.add-cart-btn:hover {
  background-color: #85ce61;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

.action-buttons button {
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.reset-button {
  background-color: #f4f4f5;
  color: #606266;
  border: 1px solid #dcdfe6;
}

.reset-button:hover {
  background-color: #e9e9eb;
}

.mall-button {
  background-color: #409eff;
  color: white;
  border: none;
}

.mall-button:hover {
  background-color: #66b1ff;
}

.home-button {
  background-color: #909399;
  color: white;
  border: none;
}

.home-button:hover {
  background-color: #a6a9ad;
}

@media (max-width: 768px) {
  .questionnaire-card {
    padding: 15px;
    width: 95%;
    max-width: none;
  }
  
  .question-container h2 {
    font-size: 18px;
  }
  
  .option-item {
    padding: 12px;
    font-size: 14px;
  }
  
  .navigation-buttons {
    flex-direction: column;
    gap: 10px;
  }
  
  .nav-button {
    width: 100%;
  }
  
  .action-buttons {
    flex-direction: column;
    gap: 10px;
  }
  
  .action-buttons button {
    width: 100%;
  }
  
  .package-card {
    padding: 15px;
  }
  
  .package-header h3 {
    font-size: 16px;
  }

  .package-actions {
    flex-direction: column;
  }
}
</style> 