<template>
  <div class="cart-container">
    <div class="cart-header">
      <div class="back-btn" @click="$router.go(-1)">
        <i class="fa fa-angle-left"></i>
      </div>
      <div class="title">购物车</div>
      <div class="clear-btn" @click="clearCart">清空</div>
    </div>

    <div class="cart-content">
      <div v-if="cartItems.length === 0" class="empty-cart">
        <i class="fa fa-shopping-cart"></i>
        <p>购物车是空的</p>
        <div class="go-shop-btn" @click="$router.push('/mall')">去商城逛逛</div>
      </div>

      <div v-else>
        <div class="cart-items">
          <div class="cart-item" v-for="(item, index) in cartItems" :key="index">
            <div class="item-image">
              <img :src="item.image" alt="套餐图片">
            </div>
            <div class="item-info">
              <div class="item-title">{{ item.title }}</div>
              <div class="item-hospital">{{ item.hospital }}</div>
              <div class="item-price">¥{{ item.price }}</div>
            </div>
            <div class="item-actions">
              <div class="remove-btn" @click="removeItem(index)">
                <i class="fa fa-trash"></i>
              </div>
            </div>
          </div>
        </div>

        <div class="cart-summary">
          <div class="total-label">总计：</div>
          <div class="total-price">¥{{ totalPrice }}</div>
        </div>

        <div class="cart-checkout">
          <div class="checkout-btn" @click="checkout">提交订单</div>
        </div>
      </div>
    </div>

    <Footer></Footer>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Footer from '@/components/Footer.vue';

export default {
  components: {
    Footer
  },
  setup() {
    const router = useRouter();
    const cartItems = ref([]);

    // 从localStorage加载购物车数据
    onMounted(() => {
      loadCart();
    });

    const loadCart = () => {
      const cart = localStorage.getItem('shoppingCart');
      if (cart) {
        cartItems.value = JSON.parse(cart);
        console.log('加载购物车数据:', cartItems.value);
      }
    };

    // 计算总价
    const totalPrice = computed(() => {
      return cartItems.value.reduce((total, item) => {
        return total + parseFloat(item.price || 0);
      }, 0).toFixed(2);
    });

    // 移除购物车中的单个项目
    const removeItem = (index) => {
      if (confirm('确定要从购物车中移除此项吗？')) {
        cartItems.value.splice(index, 1);
        saveCart();
      }
    };

    // 清空购物车
    const clearCart = () => {
      if (cartItems.value.length === 0) {
        alert('购物车已经是空的');
        return;
      }
      
      if (confirm('确定要清空购物车吗？')) {
        cartItems.value = [];
        saveCart();
      }
    };

    // 保存购物车到localStorage
    const saveCart = () => {
      localStorage.setItem('shoppingCart', JSON.stringify(cartItems.value));
    };

    // 结算
    const checkout = () => {
      if (cartItems.value.length === 0) {
        alert('购物车是空的，请先添加商品');
        return;
      }
      
      // 这里可以添加更多的结算逻辑
      alert('订单提交成功！');
      // 清空购物车
      cartItems.value = [];
      saveCart();
      // 跳转到首页或订单页
      router.push('/index');
    };

    return {
      cartItems,
      totalPrice,
      removeItem,
      clearCart,
      checkout
    };
  }
};
</script>

<style scoped>
.cart-container {
  width: 100%;
  min-height: 100vh;
  background-color: #F9F9F9;
  padding-bottom: 15vw;
}

.cart-header {
  width: 100%;
  height: 12vw;
  background-color: #FFF;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 4vw;
  position: sticky;
  top: 0;
  z-index: 10;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.back-btn {
  width: 8vw;
  height: 8vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

.back-btn i {
  font-size: 5vw;
  color: #333;
}

.title {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
}

.clear-btn {
  font-size: 3.5vw;
  color: #FF6B01;
  padding: 1vw 2vw;
}

.cart-content {
  padding: 4vw;
}

.empty-cart {
  width: 100%;
  height: 50vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #999;
}

.empty-cart i {
  font-size: 15vw;
  margin-bottom: 3vw;
}

.empty-cart p {
  font-size: 4vw;
  margin-bottom: 5vw;
}

.go-shop-btn {
  background-color: #FF6B01;
  color: #FFF;
  padding: 2vw 5vw;
  border-radius: 5vw;
  font-size: 4vw;
}

.cart-items {
  margin-bottom: 4vw;
}

.cart-item {
  background-color: #FFF;
  border-radius: 2vw;
  margin-bottom: 3vw;
  padding: 3vw;
  display: flex;
  align-items: center;
}

.item-image {
  width: 20vw;
  height: 20vw;
  margin-right: 3vw;
}

.item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 1vw;
}

.item-info {
  flex: 1;
}

.item-title {
  font-size: 4vw;
  font-weight: bold;
  color: #333;
  margin-bottom: 1vw;
}

.item-hospital {
  font-size: 3vw;
  color: #999;
  margin-bottom: 1vw;
}

.item-price {
  font-size: 4.5vw;
  font-weight: bold;
  color: #FF6B01;
}

.item-actions {
  display: flex;
  align-items: center;
}

.remove-btn {
  width: 8vw;
  height: 8vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

.remove-btn i {
  font-size: 5vw;
  color: #999;
}

.cart-summary {
  background-color: #FFF;
  border-radius: 2vw;
  padding: 3vw;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 3vw;
}

.total-label {
  font-size: 3.5vw;
  color: #333;
  margin-right: 2vw;
}

.total-price {
  font-size: 5vw;
  font-weight: bold;
  color: #FF6B01;
}

.cart-checkout {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 5vw;
}

.checkout-btn {
  width: 80%;
  height: 12vw;
  background-color: #FF6B01;
  color: #FFF;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6vw;
  font-size: 4.5vw;
  font-weight: bold;
}
</style> 