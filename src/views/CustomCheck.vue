<template>
  <div class="custom-check-container">
    <div class="header">
      <div class="back-btn" @click="$router.go(-1)">
        <i class="fa fa-angle-left"></i>
      </div>
      <div class="title">自选体检</div>
      <div class="right-btn" @click="showCart">
        <i class="fa fa-shopping-cart"></i>
        <span class="cart-count" v-if="cartCount > 0">{{ cartCount }}</span>
      </div>
    </div>
    
    <div class="content">
      <div class="intro-section">
        <div class="intro-title">自由定制您的体检套餐</div>
        <div class="intro-desc">您可以根据自身需求选择检查项目，生成专属体检套餐</div>
      </div>
      
      <!-- 成功提示 -->
      <div v-if="showSuccessMsg" class="overlay">
        <div class="success-message">
          <i class="fa fa-check-circle"></i>
          <span>已成功添加到购物车</span>
          <div class="success-actions">
            <button class="continue-btn" @click="hideSuccess">继续选择</button>
            <button class="go-cart-btn" @click="goToCart">去购物车</button>
          </div>
        </div>
      </div>
      
      <!-- 检查项目选择区域 -->
      <div class="check-items-section">
        <div v-for="(category, index) in checkCategories" :key="index" class="category-item">
          <div class="category-header" @click="toggleCategory(index)">
            <div class="category-title">{{ category.name }}</div>
            <div class="category-price">¥{{ getCategoryPrice(category) }}</div>
            <div class="toggle-btn">
              <i class="fa" :class="category.expanded ? 'fa-angle-up' : 'fa-angle-down'"></i>
            </div>
          </div>
          
          <div v-if="category.expanded" class="category-content">
            <div v-for="(item, idx) in category.items" :key="idx" class="check-item">
              <div class="check-item-info">
                <div class="item-name">{{ item.name }}</div>
                <div class="item-price">¥{{ item.price }}</div>
              </div>
              
              <div class="check-item-select">
                <div 
                  class="select-btn" 
                  :class="{ 'selected': selectedItems.includes(item.id) }"
                  @click="toggleCheckItem(item)">
                  <i class="fa" :class="selectedItems.includes(item.id) ? 'fa-check-circle' : 'fa-circle-o'"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 套餐预览 -->
      <div class="preview-section">
        <div class="preview-title">您的自选套餐</div>
        <div class="preview-content">
          <div v-if="selectedItems.length === 0" class="empty-preview">
            请选择至少一个检查项目
          </div>
          <div v-else class="preview-items">
            <div v-for="(item, index) in selectedItemsDetails" :key="index" class="preview-item">
              <div class="preview-item-name">{{ item.name }}</div>
              <div class="preview-item-price">¥{{ item.price }}</div>
              <div class="remove-btn" @click="removeItem(item)">
                <i class="fa fa-trash"></i>
              </div>
            </div>
          </div>
        </div>
        
        <div class="total-price">
          <div class="total-label">总计金额：</div>
          <div class="total-value">¥{{ totalPrice }}</div>
        </div>
        
        <div class="add-cart-btn" @click="addCustomPackageToCart">
          加入购物车
        </div>
      </div>
    </div>
    
    <!-- 底部导航 -->
    <Footer />
  </div>
</template>

<script>
import Footer from '@/components/Footer.vue';
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import customCheckImg from '@/assets/image/custom-check.jpg';

export default {
  name: 'CustomCheck',
  components: {
    Footer
  },
  setup() {
    const router = useRouter();
    const selectedItems = ref([]);
    const cartCount = ref(0);
    const showSuccessMsg = ref(false); // 新增：控制成功提示的显示
    
    // 检查大项数据
    const checkCategories = ref([
      {
        id: 1,
        name: '一般检查项目',
        expanded: true,
        items: [
          { id: 101, name: '身高', price: 10 },
          { id: 102, name: '体重', price: 10 },
          { id: 103, name: '血压', price: 15 },
          { id: 104, name: '脉搏', price: 15 },
          { id: 105, name: '体温', price: 10 },
        ]
      },
      {
        id: 2,
        name: '血液检查',
        expanded: false,
        items: [
          { id: 201, name: '血常规', price: 35 },
          { id: 202, name: '血脂四项', price: 60 },
          { id: 203, name: '血糖', price: 20 },
          { id: 204, name: '肝功能', price: 80 },
          { id: 205, name: '肾功能', price: 80 },
        ]
      },
      {
        id: 3,
        name: '影像检查',
        expanded: false,
        items: [
          { id: 301, name: '腹部彩超', price: 120 },
          { id: 302, name: '心脏彩超', price: 200 },
          { id: 303, name: '颈动脉彩超', price: 150 },
          { id: 304, name: '胸部X光', price: 100 },
          { id: 305, name: '甲状腺彩超', price: 120 },
        ]
      },
      {
        id: 4,
        name: '男性专项',
        expanded: false,
        items: [
          { id: 401, name: '前列腺彩超', price: 150 },
          { id: 402, name: '前列腺特异性抗原', price: 120 },
          { id: 403, name: '男性激素六项', price: 300 },
        ]
      },
      {
        id: 5,
        name: '女性专项',
        expanded: false,
        items: [
          { id: 501, name: '乳腺彩超', price: 150 },
          { id: 502, name: '妇科B超', price: 120 },
          { id: 503, name: '宫颈细胞学检查', price: 200 },
          { id: 504, name: '女性激素六项', price: 300 },
        ]
      },
      {
        id: 6,
        name: '肿瘤筛查',
        expanded: false,
        items: [
          { id: 601, name: '肿瘤标志物（肝）', price: 220 },
          { id: 602, name: '肿瘤标志物（肺）', price: 220 },
          { id: 603, name: '肿瘤标志物（消化道）', price: 220 },
          { id: 604, name: '肿瘤标志物（全套）', price: 800 },
        ]
      }
    ]);
    
    // 计算选中项目的详情
    const selectedItemsDetails = computed(() => {
      const result = [];
      
      checkCategories.value.forEach(category => {
        category.items.forEach(item => {
          if (selectedItems.value.includes(item.id)) {
            result.push(item);
          }
        });
      });
      
      return result;
    });
    
    // 计算总价
    const totalPrice = computed(() => {
      return selectedItemsDetails.value.reduce((sum, item) => sum + item.price, 0);
    });
    
    // 获取分类价格
    const getCategoryPrice = (category) => {
      return category.items.reduce((sum, item) => sum + item.price, 0);
    };
    
    // 展开/收起分类
    const toggleCategory = (index) => {
      checkCategories.value[index].expanded = !checkCategories.value[index].expanded;
    };
    
    // 选择/取消选择检查项目
    const toggleCheckItem = (item) => {
      const index = selectedItems.value.indexOf(item.id);
      
      if (index === -1) {
        selectedItems.value.push(item.id);
      } else {
        selectedItems.value.splice(index, 1);
      }
    };
    
    // 从预览中移除项目
    const removeItem = (item) => {
      const index = selectedItems.value.indexOf(item.id);
      
      if (index !== -1) {
        selectedItems.value.splice(index, 1);
      }
    };
    
    // 加入购物车
    const addCustomPackageToCart = () => {
      if (selectedItems.value.length === 0) {
        alert('请至少选择一个检查项目');
        return;
      }
      
      try {
        console.log('正在添加自选套餐到购物车...');
        console.log('已选项目数量:', selectedItems.value.length);
        console.log('总价:', totalPrice.value);
        
        // 创建自定义套餐对象
        const customPackage = {
          smid: 'custom-' + new Date().getTime(), // 生成唯一ID
          title: '自选体检套餐',
          description: `包含${selectedItems.value.length}项检查项目`,
          price: totalPrice.value,
          hospital: '爱康未来互联网医院',
          image: customCheckImg, // 使用自定义体检图片
          features: Array.from(new Set(selectedItemsDetails.value.map(item => {
            // 根据ID获取所属分类名称
            const categoryId = Math.floor(item.id / 100);
            const category = checkCategories.value.find(c => c.id === categoryId);
            return category ? category.name : '自选项目';
          }))),
          checkItemDetails: selectedItemsDetails.value.map(item => ({
            id: item.id,
            name: item.name,
            value: '√',
            hint: '自选项目'
          }))
        };
        
        console.log('生成的套餐对象:', customPackage);
        
        // 获取购物车数据
        let cartItems = [];
        try {
          const cartData = localStorage.getItem('shoppingCart');
          console.log('获取到的购物车数据:', cartData);
          if (cartData) {
            cartItems = JSON.parse(cartData);
          }
        } catch (error) {
          console.error('解析购物车数据出错:', error);
          cartItems = [];
        }
        
        // 添加到购物车
        cartItems.push(customPackage);
        console.log('更新后的购物车:', cartItems);
        
        // 保存到本地存储
        localStorage.setItem('shoppingCart', JSON.stringify(cartItems));
        console.log('已保存到localStorage');
        
        // 更新购物车数量
        updateCartCount();
        
        // 提示用户
        alert('自选套餐已添加到购物车');
        
        // 清空选择的项目
        selectedItems.value = [];
        
        // 显示成功提示
        showSuccessMsg.value = true;
        
        // 可选：跳转到购物车页面
        // router.push('/cart');
      } catch (error) {
        console.error('加入购物车失败:', error);
        alert('添加到购物车失败，请重试');
      }
    };
    
    // 显示购物车
    const showCart = () => {
      router.push('/cart');
    };

    // 隐藏成功提示
    const hideSuccess = () => {
      showSuccessMsg.value = false;
    };

    // 跳转到购物车页面
    const goToCart = () => {
      router.push('/cart');
      hideSuccess(); // 隐藏成功提示
    };
    
    // 更新购物车数量
    const updateCartCount = () => {
      const cart = localStorage.getItem('shoppingCart');
      if (cart) {
        cartCount.value = JSON.parse(cart).length;
      } else {
        cartCount.value = 0;
      }
    };
    
    // 组件挂载时更新购物车数量
    onMounted(() => {
      updateCartCount();
    });
    
    return {
      checkCategories,
      selectedItems,
      selectedItemsDetails,
      totalPrice,
      cartCount,
      getCategoryPrice,
      toggleCategory,
      toggleCheckItem,
      removeItem,
      addCustomPackageToCart,
      showCart,
      showSuccessMsg, // 暴露给模板
      hideSuccess, // 暴露给模板
      goToCart // 暴露给模板
    };
  }
}
</script>

<style scoped>
.custom-check-container {
  width: 100%;
  min-height: 100vh;
  background-color: #F5F7FA;
  padding-bottom: 15vw;
}

.header {
  width: 100%;
  height: 12vw;
  background-color: #FFF;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 4vw;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.back-btn, .right-btn {
  width: 10vw;
  height: 10vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 5vw;
  color: #333;
  position: relative;
}

.title {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
}

.cart-count {
  position: absolute;
  top: 0;
  right: 0;
  background-color: #FF6B01;
  color: #FFF;
  font-size: 2.5vw;
  width: 4vw;
  height: 4vw;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content {
  padding: 4vw;
}

.intro-section {
  background-color: #FFF;
  border-radius: 2vw;
  padding: 4vw;
  margin-bottom: 4vw;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.intro-title {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
  margin-bottom: 2vw;
}

.intro-desc {
  font-size: 3.5vw;
  color: #666;
}

.check-items-section {
  margin-bottom: 4vw;
}

.category-item {
  background-color: #FFF;
  border-radius: 2vw;
  margin-bottom: 3vw;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.category-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 4vw;
  cursor: pointer;
}

.category-title {
  font-size: 4vw;
  font-weight: bold;
  color: #333;
}

.category-price {
  font-size: 3.5vw;
  color: #FF6B01;
  font-weight: bold;
}

.toggle-btn {
  width: 8vw;
  height: 8vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4vw;
  color: #999;
}

.category-content {
  border-top: 1px solid #F0F0F0;
}

.check-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 3vw 4vw;
  border-bottom: 1px solid #F0F0F0;
}

.check-item:last-child {
  border-bottom: none;
}

.check-item-info {
  flex: 1;
}

.item-name {
  font-size: 3.8vw;
  color: #333;
  margin-bottom: 1vw;
}

.item-price {
  font-size: 3.5vw;
  color: #FF6B01;
}

.check-item-select {
  width: 8vw;
  height: 8vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

.select-btn {
  width: 6vw;
  height: 6vw;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 6vw;
  color: #CCCCCC;
}

.select-btn.selected {
  color: #FF6B01;
}

.preview-section {
  background-color: #FFF;
  border-radius: 2vw;
  padding: 4vw;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.preview-title {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
  margin-bottom: 3vw;
}

.preview-content {
  margin-bottom: 3vw;
}

.empty-preview {
  padding: 5vw 0;
  text-align: center;
  color: #999;
  font-size: 3.5vw;
}

.preview-items {
  max-height: 50vw;
  overflow-y: auto;
}

.preview-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 2vw 0;
  border-bottom: 1px solid #F0F0F0;
}

.preview-item:last-child {
  border-bottom: none;
}

.preview-item-name {
  flex: 1;
  font-size: 3.5vw;
  color: #333;
}

.preview-item-price {
  font-size: 3.5vw;
  color: #FF6B01;
  margin-right: 3vw;
}

.remove-btn {
  width: 8vw;
  height: 8vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4vw;
  color: #999;
}

.total-price {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-bottom: 4vw;
}

.total-label {
  font-size: 3.8vw;
  color: #333;
  margin-right: 2vw;
}

.total-value {
  font-size: 5vw;
  font-weight: bold;
  color: #FF6B01;
}

.add-cart-btn {
  width: 100%;
  height: 12vw;
  background-color: #FF6B01;
  color: #FFF;
  border-radius: 6vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4.5vw;
  font-weight: bold;
}

/* 成功提示样式 */
.success-message {
  background-color: #FFF;
  border-radius: 2vw;
  padding: 6vw 4vw;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  max-width: 400px;
}

.success-message i {
  font-size: 15vw;
  color: #4CAF50; /* 绿色 */
  margin-bottom: 4vw;
}

.success-message span {
  font-size: 5vw;
  color: #333;
  text-align: center;
  margin-bottom: 6vw;
  font-weight: bold;
}

.success-actions {
  display: flex;
  gap: 3vw;
}

.success-actions button {
  width: 25vw;
  height: 8vw;
  background-color: #FF6B01;
  color: #FFF;
  border-radius: 4vw;
  font-size: 3.5vw;
  font-weight: bold;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.success-actions button:hover {
  background-color: #E65C00;
}

.success-actions .continue-btn {
  background-color: #4CAF50; /* 更深的绿色 */
}

.success-actions .continue-btn:hover {
  background-color: #388E3C;
}

/* 背景遮罩样式 */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999; /* 确保在其他内容之上 */
}
</style> 