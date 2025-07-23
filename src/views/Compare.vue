<template>
  <div class="compare-container">
    <div class="compare-header">
      <div class="back-btn" @click="$router.go(-1)">
        <i class="fa fa-angle-left"></i>
      </div>
      <div class="title">套餐对比</div>
      <div class="placeholder"></div>
    </div>

    <div class="compare-content">
      <!-- 套餐基本信息 -->
      <div class="compare-section">
        <div class="section-title">套餐信息</div>
        <div class="compare-row">
          <div class="row-label">套餐图片</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, index) in compareItems" :key="'img-'+index">
              <img :src="item.image" alt="套餐图片">
            </div>
          </div>
        </div>
        <div class="compare-row">
          <div class="row-label">套餐名称</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, index) in compareItems" :key="'name-'+index">
              {{ item.title }}
            </div>
          </div>
        </div>
        <div class="compare-row">
          <div class="row-label">价格</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, index) in compareItems" :key="'price-'+index">
              <div class="price">¥{{ item.price }}</div>
              <div class="original-price" v-if="item.originalPrice">¥{{ item.originalPrice }}</div>
            </div>
          </div>
        </div>
        <div class="compare-row">
          <div class="row-label">医院</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, index) in compareItems" :key="'hospital-'+index">
              {{ item.hospital }}
            </div>
          </div>
        </div>
        <div class="compare-row">
          <div class="row-label">适用人群</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, index) in compareItems" :key="'age-'+index">
              {{ getAgeGroup(item.age) }}
            </div>
          </div>
        </div>
        <div class="compare-row">
          <div class="row-label">适用性别</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, index) in compareItems" :key="'gender-'+index">
              {{ getGender(item.gender) }}
            </div>
          </div>
        </div>
      </div>

      <!-- 检查项目对比 -->
      <div class="compare-section">
        <div class="section-title">检查项目对比</div>
        <div class="compare-row" v-for="(feature, index) in allFeatures" :key="'feature-'+index">
          <div class="row-label">{{ feature }}</div>
          <div class="row-values">
            <div class="row-value" v-for="(item, itemIndex) in compareItems" :key="'feature-'+index+'-'+itemIndex">
              <div class="feature-status" :class="{
                'has-feature': hasFeature(item, feature),
                'no-feature': !hasFeature(item, feature)
              }">
                <i class="fa" :class="hasFeature(item, feature) ? 'fa-check' : 'fa-times'"></i>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 包含检项对比 -->
      <div class="compare-section">
        <div class="section-title">包含检项对比</div>
        <div v-if="allCheckItemDetails.length === 0" class="no-items">
          暂无详细检查项对比信息
        </div>
        <div v-else class="compare-row" v-for="(checkItemDetail, index) in allCheckDetailedItems" :key="'detail-'+index">
          <div class="row-label">{{ checkItemDetail.name }} <span v-if="checkItemDetail.unit">({{ checkItemDetail.unit }})</span></div>
          <div class="row-values">
            <div class="row-value" v-for="(item, itemIndex) in compareItems" :key="'detail-'+index+'-'+itemIndex">
              <div class="feature-status" :class="{
                'has-feature': hasCheckDetailedItem(item, checkItemDetail.name),
                'no-feature': !hasCheckDetailedItem(item, checkItemDetail.name)
              }">
                <i class="fa" :class="hasCheckDetailedItem(item, checkItemDetail.name) ? 'fa-check' : 'fa-times'"></i>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div style="height: 15vw;"></div> <!-- 添加空间以避免购物车按钮遮挡内容 -->

      <div class="compare-actions">
        <div class="action-btn-group">
          <div class="action-btn add-left" @click="addToCart('left')">选择左边</div>
          <div class="action-btn add-right" @click="addToCart('right')">选择右边</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    const compareItems = ref([]);

    // 从localStorage获取对比套餐数据
    onMounted(async () => {
      try {
        const storedItems = localStorage.getItem('compareItems');
        console.log('从localStorage获取的对比项:', storedItems);
        
        if (storedItems) {
          compareItems.value = JSON.parse(storedItems);
          console.log('解析后的对比项:', compareItems.value);
          console.log('第一个套餐的检查项目详情:', compareItems.value[0]?.checkItemDetails);
          console.log('第二个套餐的检查项目详情:', compareItems.value[1]?.checkItemDetails);
          
          // 直接使用存储的检查项目详情数据，不再需要加载模拟数据
          // compareItems.value中已经包含了正确的checkItemDetails
        }
        
        // 如果没有足够的对比套餐，返回上一页
        if (!compareItems.value || compareItems.value.length < 2) {
          alert('请先选择两个套餐进行对比');
          router.go(-1);
        }
      } catch (error) {
        console.error('加载对比数据出错:', error);
        alert('加载对比数据出错，将返回上一页');
        router.go(-1);
      }
    });

    // 从后端获取检查项目详情数据
    const fetchCheckItemDetailedData = async () => {
      try {
        // 创建检查项目详情数据
        const checkDetailedItems = [
          { id: 1, name: '收缩压', unit: 'mmHg', minrange: 0, maxrange: 140, normalValue: '<140' },
          { id: 2, name: '舒张压', unit: 'mmHg', minrange: 0, maxrange: 90, normalValue: '<90' },
          { id: 3, name: '身高', unit: 'cm', minrange: null, maxrange: null, normalValue: null },
          { id: 4, name: '体重', unit: 'kg', minrange: null, maxrange: null, normalValue: null },
          { id: 5, name: '腰围', unit: 'cm', minrange: null, maxrange: null, normalValue: null },
          { id: 6, name: '臀围', unit: 'cm', minrange: null, maxrange: null, normalValue: null },
          { id: 7, name: '白细胞计数', unit: '10^9/L', minrange: 4, maxrange: 10, normalValue: '4-10' },
          { id: 8, name: '红细胞压值', unit: '%', minrange: 36, maxrange: 50, normalValue: '36-50' },
          { id: 9, name: '淋巴细胞计数百分比', unit: '%', minrange: 18.3, maxrange: 47.9, normalValue: '18.3-47.9' },
          { id: 10, name: '单核细胞计数百分比', unit: '%', minrange: 4.2, maxrange: 15.2, normalValue: '4.2-15.2' },
          { id: 11, name: '嗜酸性粒细胞计数百分比', unit: '%', minrange: 0.2, maxrange: 7.6, normalValue: '0.2-7.6' },
          { id: 12, name: '中性粒细胞计数绝对值', unit: '10^9/L', minrange: 1.8, maxrange: 8.89, normalValue: '1.8-8.89' },
          { id: 13, name: '血小板计数', unit: '10^9/L', minrange: 100, maxrange: 300, normalValue: '100-300' },
          { id: 14, name: '血红细胞计数', unit: '10^12/L', minrange: 3.5, maxrange: 5.5, normalValue: '3.5-5.5' },
          { id: 15, name: '尿白细胞', unit: '/ul', minrange: null, maxrange: null, normalValue: '-' },
          { id: 16, name: '尿亚硝酸盐', unit: null, minrange: null, maxrange: null, normalValue: '- (阴性)' },
          { id: 17, name: '尿液酸碱度', unit: null, minrange: 5.5, maxrange: 6.5, normalValue: '5.5-6.5' },
          { id: 18, name: '尿蛋白定性', unit: 'g/L', minrange: null, maxrange: null, normalValue: '-' },
          { id: 19, name: '尿比重', unit: null, minrange: 1.015, maxrange: 1.025, normalValue: '1.015-1.025' },
          { id: 20, name: '尿维生素', unit: 'mmol/L', minrange: null, maxrange: null, normalValue: '0' },
          { id: 21, name: '血清肌酐', unit: 'umol/L', minrange: 41, maxrange: 111, normalValue: '41-111' },
          { id: 22, name: '血尿素氮', unit: 'mmol/L', minrange: 2.85, maxrange: 7.14, normalValue: '2.85-7.14' },
          { id: 23, name: '血尿酸', unit: 'umol/L', minrange: 208, maxrange: 428, normalValue: '208-428' },
          { id: 24, name: '白蛋白', unit: 'g/L', minrange: 35, maxrange: 55, normalValue: '35-55' },
          { id: 25, name: '血清谷草转氨酶', unit: 'U/L', minrange: 15, maxrange: 40, normalValue: '15-40' },
          { id: 26, name: '血清谷丙转氨酶', unit: 'U/L', minrange: 9, maxrange: 50, normalValue: '9-50' },
          { id: 27, name: '血清总胆红素', unit: 'umol/L', minrange: 0, maxrange: 20, normalValue: '0-20' },
          { id: 28, name: '血清总蛋白', unit: 'g/L', minrange: 60, maxrange: 87, normalValue: '60-87' },
          { id: 29, name: '盆腔检查', unit: null, minrange: null, maxrange: null, normalValue: null },
          { id: 30, name: '乳腺检查', unit: null, minrange: null, maxrange: null, normalValue: null },
          { id: 31, name: '子宫检查', unit: null, minrange: null, maxrange: null, normalValue: null }
        ];
        
        // 为每个对比项目添加详细检查项目
        compareItems.value.forEach(item => {
          // 根据套餐类型分配不同的检查项目
          if (item.gender === 'male') {
            // 男性套餐
            if (item.title.includes('基础')) {
              item.checkDetailedItems = checkDetailedItems.filter(x => [1, 2, 7, 8, 9].includes(x.id));
            } else if (item.title.includes('肾病')) {
              item.checkDetailedItems = checkDetailedItems.filter(x => [21, 22, 23, 24].includes(x.id));
            } else if (item.title.includes('肝病')) {
              item.checkDetailedItems = checkDetailedItems.filter(x => [25, 26, 27, 28].includes(x.id));
            }
          } else {
            // 女性套餐
            if (item.title.includes('基础')) {
              item.checkDetailedItems = checkDetailedItems.filter(x => [1, 2, 7, 8, 9].includes(x.id));
            } else if (item.title.includes('肾病')) {
              item.checkDetailedItems = checkDetailedItems.filter(x => [21, 22, 23, 24, 29].includes(x.id));
            } else if (item.title.includes('肝病')) {
              item.checkDetailedItems = checkDetailedItems.filter(x => [25, 26, 27, 28, 30].includes(x.id));
            }
          }
        });
        
        console.log('添加检查项目详情后的对比项:', compareItems.value);
      } catch (error) {
        console.error('获取检查项目详情数据失败:', error);
      }
    };

    // 获取所有特征项
    const allFeatures = computed(() => {
      const features = new Set();
      compareItems.value.forEach(item => {
        if (item.features && Array.isArray(item.features)) {
          item.features.forEach(feature => {
            features.add(feature);
          });
        }
      });
      return Array.from(features);
    });

    // 获取所有检查项
    const allCheckItems = computed(() => {
      const checkItems = new Set();
      compareItems.value.forEach(item => {
        if (item.checkItems && Array.isArray(item.checkItems)) {
          item.checkItems.forEach(checkItem => {
            // 如果检查项是对象，使用name属性，否则直接使用值
            if (typeof checkItem === 'object' && checkItem.name) {
              checkItems.add(checkItem.name);
            } else {
              checkItems.add(checkItem);
            }
          });
        }
      });
      return Array.from(checkItems);
    });

    // 获取所有检查项详情
    const allCheckItemDetails = computed(() => {
      const checkItemMap = new Map();
      
      compareItems.value.forEach(item => {
        if (item.checkItemDetails && Array.isArray(item.checkItemDetails)) {
          item.checkItemDetails.forEach(detail => {
            if (!checkItemMap.has(detail.id)) {
              checkItemMap.set(detail.id, detail);
            }
          });
        }
      });
      
      return Array.from(checkItemMap.values());
    });

    // 获取所有详细检查项详情 - 使用checkItemDetails代替checkDetailedItems
    const allCheckDetailedItems = computed(() => {
      const detailedItemMap = new Map();
      
      compareItems.value.forEach(item => {
        if (item.checkItemDetails && Array.isArray(item.checkItemDetails)) {
          item.checkItemDetails.forEach(detail => {
            // 使用name作为键，确保唯一性
            const key = detail.name || detail.id;
            if (key && !detailedItemMap.has(key)) {
              detailedItemMap.set(key, detail);
            }
          });
        }
      });
      
      return Array.from(detailedItemMap.values());
    });

    // 检查套餐是否包含特定特征
    const hasFeature = (item, feature) => {
      return item.features && Array.isArray(item.features) && item.features.includes(feature);
    };

    // 检查套餐是否包含特定检查项
    const hasCheckItem = (item, checkItemName) => {
      if (!item.checkItems || !Array.isArray(item.checkItems)) return false;
      // 检查是否是数组中的对象
      return item.checkItems.some(category => 
        (typeof category === 'object' && category.name === checkItemName) || 
        category === checkItemName
      );
    };

    // 检查套餐是否包含特定检查项详情
    const hasCheckItemDetail = (item, checkItemName) => {
      if (!item.checkItemDetails || !Array.isArray(item.checkItemDetails)) {
        return false;
      }
      
      const result = item.checkItemDetails.some(detail => 
        detail.name === checkItemName
      );
      
      return result;
    };

    // 检查套餐是否包含特定详细检查项详情
    const hasCheckDetailedItem = (item, checkItemName) => {
      // 直接使用checkItemDetails，不再使用checkDetailedItems
      // 这样保持一致性，并简化代码
      if (!item.checkItemDetails || !Array.isArray(item.checkItemDetails)) {
        return false;
      }
      
      return item.checkItemDetails.some(detail => detail.name === checkItemName);
    };

    // 获取年龄组名称
    const getAgeGroup = (age) => {
      switch (age) {
        case 'middle':
          return '中青年';
        case 'elder':
          return '中老年';
        case 'child':
          return '儿童';
        case 'pregnant':
          return '孕妇';
        default:
          return '通用';
      }
    };

    // 获取性别名称
    const getGender = (gender) => {
      switch (gender) {
        case 'male':
          return '男性';
        case 'female':
          return '女性';
        default:
          return '不限';
      }
    };

    // 获取检查项目名称
    const getCheckItemName = (checkItem) => {
      const checkItemNames = {
        'spirochete': '幽门螺杆菌检测',
        'ct': '胸肺部CT',
        'heart': '心脏彩超',
        'thyroid': '甲状腺彩超',
        'hpv': 'TCT+HPV筛查',
        'breast': '乳腺彩超',
        'prostate': '前列腺彩超',
        'tumor': 'TM肿瘤12项'
      };
      return checkItemNames[checkItem] || checkItem;
    };

    // 添加到购物车
    const addToCart = (type) => {
      let message = '';
      let itemToAdd = null;
      
      // 根据选择确定要添加的套餐
      if (type === 'left' && compareItems.value.length > 0) {
        itemToAdd = compareItems.value[0];
        message = `已将${itemToAdd.title}加入购物车`;
      } else if (type === 'right' && compareItems.value.length > 1) {
        itemToAdd = compareItems.value[1];
        message = `已将${itemToAdd.title}加入购物车`;
      }
      
      if (itemToAdd) {
        // 获取现有购物车
        let cart = localStorage.getItem('shoppingCart');
        let cartItems = cart ? JSON.parse(cart) : [];
        
        // 检查是否已在购物车中
        const exists = cartItems.some(item => item.smid === itemToAdd.smid);
        
        if (!exists) {
          // 添加到购物车
          cartItems.push(itemToAdd);
          localStorage.setItem('shoppingCart', JSON.stringify(cartItems));
          alert(message);
          router.push('/cart'); // 添加后跳转到购物车页面
        } else {
          alert('该套餐已在购物车中');
        }
      } else {
        alert('选择的套餐不存在');
      }
    };

    return {
      compareItems,
      allFeatures,
      allCheckItems,
      allCheckItemDetails,
      allCheckDetailedItems,
      hasFeature,
      hasCheckItem,
      hasCheckItemDetail,
      hasCheckDetailedItem,
      getAgeGroup,
      getGender,
      getCheckItemName,
      addToCart
    };
  }
};
</script>

<style scoped>
.compare-container {
  width: 100%;
  min-height: 100vh;
  background-color: #F9F9F9;
  padding-bottom: 15vw;
}

.compare-header {
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

.placeholder {
  width: 8vw;
}

.compare-content {
  padding: 4vw;
}

.compare-section {
  background-color: #FFF;
  border-radius: 2vw;
  margin-bottom: 4vw;
  overflow: hidden;
}

.section-title {
  font-size: 4vw;
  font-weight: bold;
  color: #333;
  padding: 3vw;
  border-bottom: 1px solid #F5F5F5;
}

.compare-row {
  display: flex;
  border-bottom: 1px solid #F5F5F5;
}

.row-label {
  width: 25%;
  padding: 3vw;
  font-size: 3.5vw;
  color: #666;
  background-color: #F9F9F9;
  border-right: 1px solid #F5F5F5;
  display: flex;
  align-items: center;
}

.row-values {
  width: 75%;
  display: flex;
}

.row-value {
  flex: 1;
  padding: 3vw;
  font-size: 3.5vw;
  color: #333;
  text-align: center;
  border-right: 1px solid #F5F5F5;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.row-value:last-child {
  border-right: none;
}

.row-value img {
  width: 20vw;
  height: 20vw;
  object-fit: cover;
  border-radius: 2vw;
}

.price {
  font-size: 4vw;
  font-weight: bold;
  color: #FF6B01;
}

.original-price {
  font-size: 3vw;
  color: #999;
  text-decoration: line-through;
}

.feature-status {
  width: 6vw;
  height: 6vw;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.has-feature {
  background-color: #E6F7E6;
  color: #52C41A;
}

.no-feature {
  background-color: #FFF1F0;
  color: #F5222D;
}

.compare-actions {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: #FFF;
  padding: 2vw 4vw;
  display: flex;
  flex-direction: column;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
}

.action-btn {
  width: 100%;
  height: 10vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4vw;
  color: #FFF;
  border-radius: 1vw;
  margin-bottom: 2vw;
}

.add-both {
  background-color: #FF6B01;
}

.action-btn-group {
  display: flex;
  justify-content: space-between;
}

.add-left, .add-right {
  width: 48%;
  background-color: #4CAF50;
}

.no-items {
  padding: 5vw;
  text-align: center;
  color: #999;
  font-size: 3.5vw;
}
</style> 