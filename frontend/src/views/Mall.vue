<template>
  <div class="wrapper">
    <!-- 头部 -->
    <header>
      <div class="header-content">
        <div class="header-content-left" @click="toggleCitySelector">
          <div class="city-name">{{selectedCity}}</div>
          <i class="fa fa-angle-down"></i>
        </div>
        <div class="header-content-center">
          <span class="mall-title">商城</span>
        </div>
        <div class="header-content-right">
          <div class="cart-icon" @click="$router.push('/cart')">
            <i class="fa fa-shopping-cart"></i>
            <div class="cart-badge" v-if="cartItemCount > 0">{{ cartItemCount }}</div>
          </div>
          <div class="mini-search">
            <i class="fa fa-search"></i>
            <input type="text" placeholder="搜索" 
                   ref="searchInput" 
                   v-model="searchParams.keyword"
                   @keyup.enter="doSearch">
          </div>
        </div>
      </div>
    </header>

    <!-- 城市选择器 - 完全重构 -->
    <div class="city-selector-container" v-if="showCitySelector" @click.self="closeCitySelector">
      <div class="city-selector">
        <div class="city-selector-header">
          <span>选择城市</span>
          <i class="fa fa-close" @click="closeCitySelector"></i>
        </div>
        
        <!-- 热门城市 -->
        <div class="city-section">
          <h3>热门城市</h3>
          <div class="city-grid">
            <div 
              v-for="(city, index) in hotCities" 
              :key="'hot-'+index" 
              class="city-item"
              @click="selectCity(city)"
            >
              {{city}}
            </div>
          </div>
        </div>
        
        <!-- 按省份选择 -->
        <div class="city-section">
          <h3>按省份选择</h3>
          <div 
            v-for="(province, index) in provinces" 
            :key="'province-'+index"
            class="province-item"
          >
            <div class="province-name">{{province.name}}</div>
            <div class="city-list">
              <div 
                v-for="(city, cityIndex) in province.cities" 
                :key="'city-'+index+'-'+cityIndex"
                class="city-name"
                @click="selectCity(city)"
              >
                {{city}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="!showFilter">
    <!-- 分类导航 -->
    <div class="category-nav">
      <div class="category-item active">
        <span>体检</span>
      </div>
      <div class="category-item">
        <span>AI健康</span>
      </div>
      <div class="category-item">
        <span>疫苗</span>
      </div>
      <div class="category-item">
        <span>牙科</span>
      </div>
      <div class="category-item">
        <span>基因</span>
      </div>
      <div class="category-item">
        <span>全球甄选</span>
      </div>
    </div>

    <!-- 自选体检和问卷推荐 -->
    <div class="custom-check-section">
      <div class="custom-check-box" @click="$router.push('/custom-check')">
        <div class="custom-check-icon">
          <i class="fa fa-list-alt"></i>
        </div>
        <div class="custom-check-text">自选体检</div>
      </div>
      <div class="custom-check-box" @click="$router.push('/questionnaire')">
        <div class="custom-check-icon">
          <i class="fa fa-file-text"></i>
        </div>
        <div class="custom-check-text">问卷推荐</div>
      </div>
    </div>

    <!-- 图标分类 -->
    <div class="icon-categories">
      <div class="icon-category" @click="filterByCategory('entry')">
        <div class="icon-wrap"><i class="fa fa-id-card-o"></i></div>
        <p>入职体检</p>
      </div>
      <div class="icon-category" @click="filterByCategory('middle')">
        <div class="icon-wrap"><i class="fa fa-user"></i></div>
        <p>中青年优选</p>
      </div>
      <div class="icon-category" @click="filterByCategory('elder')">
        <div class="icon-wrap"><i class="fa fa-user-plus"></i></div>
        <p>中老年臻爱</p>
      </div>
      <div class="icon-category" @click="filterByCategory('female')">
        <div class="icon-wrap"><i class="fa fa-female"></i></div>
        <p>女性甄选</p>
      </div>
      <div class="icon-category" @click="filterByCategory('male')">
        <div class="icon-wrap"><i class="fa fa-male"></i></div>
        <p>男士套餐</p>
      </div>
    </div>

    <div class="icon-categories second-row">
      <div class="icon-category" @click="filterByCategory('special')">
        <div class="icon-wrap"><i class="fa fa-stethoscope"></i></div>
        <p>专项检测</p>
      </div>
      <div class="icon-category" @click="filterByCategory('marriage')">
        <div class="icon-wrap"><i class="fa fa-heart"></i></div>
        <p>婚检孕检</p>
      </div>
      <div class="icon-category" @click="filterByCategory('cancer')">
        <div class="icon-wrap"><i class="fa fa-medkit"></i></div>
        <p>防癌筛查</p>
      </div>
      <div class="icon-category" @click="filterByCategory('ct')">
        <div class="icon-wrap"><i class="fa fa-hospital-o"></i></div>
        <p>磁共振/CT</p>
      </div>
      <div class="icon-category" @click="filterByCategory('premium')">
        <div class="icon-wrap"><i class="fa fa-star"></i></div>
        <p>尊享高端</p>
      </div>
    </div>

    <!-- 筛选排序栏 -->
    <div class="filter-bar">
        <div class="filter-item" @click="showSort = !showSort; showFilter = false;">
          <span>{{ sortDisplayName }}</span>
        <i class="fa fa-angle-down"></i>
      </div>
        <div class="filter-item" @click="toggleFilter">
        <span>筛选</span>
        <i class="fa fa-filter"></i>
      </div>
    </div>

    <!-- 排序弹出层 -->
      <div v-if="showSort" class="sort-panel-container">
        <div class="sort-panel-overlay" @click="showSort = false"></div>
        <div class="sort-panel">
      <div class="sort-option" @click="selectSort('default')">
        <span>默认排序</span>
        <i class="fa fa-check" v-if="sortType === 'default'"></i>
      </div>
      <div class="sort-option" @click="selectSort('priceLow')">
        <span>价格(从低到高)</span>
        <i class="fa fa-check" v-if="sortType === 'priceLow'"></i>
      </div>
      <div class="sort-option" @click="selectSort('priceHigh')">
        <span>价格(从高到低)</span>
        <i class="fa fa-check" v-if="sortType === 'priceHigh'"></i>
      </div>
      <div class="sort-option" @click="selectSort('salesLow')">
        <span>销量(从低到高)</span>
        <i class="fa fa-check" v-if="sortType === 'salesLow'"></i>
      </div>
      <div class="sort-option" @click="selectSort('salesHigh')">
        <span>销量(从高到低)</span>
        <i class="fa fa-check" v-if="sortType === 'salesHigh'"></i>
      </div>
      </div>
    </div>

    <!-- 产品列表 -->
    <div class="product-list">
        <div v-if="products.length === 0" class="no-data">
          暂无符合条件的套餐
        </div>
        <div v-else class="product-card" v-for="(product, index) in filteredProducts" :key="index">
        <div class="product-image">
          <img :src="product.image" alt="体检套餐">
          <div class="product-tag" v-if="product.tag">{{product.tag}}</div>
        </div>
        <div class="product-info">
          <div class="product-title">
            <div class="subsidy-tag" v-if="product.subsidy">百亿补贴</div>
            <h3>【限时特价】{{product.title}}</h3>
          </div>
          <div class="product-features">
            <span v-for="(feature, idx) in product.features" :key="idx">{{feature}}</span>
          </div>
            <div class="check-items" v-if="product.checkItemDetails && product.checkItemDetails.length > 0">
              <div class="check-item" v-for="(item, idx) in product.checkItemDetails.slice(0, 3)" :key="'item-'+idx">
                <i class="fa fa-check-circle"></i> {{item.name}}
              </div>
              <div class="more-items" v-if="product.checkItemDetails.length > 3">
                +{{product.checkItemDetails.length - 3}}项
              </div>
            </div>
          <div class="hospital-container">
            <div class="product-hospital">{{product.hospital}}</div>
          </div>
                      <div class="product-bottom">
            <div class="price-container">
              <div class="price">¥{{product.price}}</div>
              <div class="original-price" v-if="product.subsidy && product.originalPrice">¥{{product.originalPrice}}</div>
            </div>
            <div class="sales">{{product.sales}}<span>已预约</span><i class="fa fa-fire hot-icon"></i></div>
              <div class="add-btn" @click="addToCart(product)">
                <i class="fa fa-plus-circle"></i>
              </div>
            </div>
            <div class="product-actions">
              <div class="compare-btn" @click="addToCompare(product)">
                <i class="fa fa-balance-scale"></i>
                <span class="compare-text">对比</span>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 没有数据时显示 -->
        <div class="no-data" v-if="filteredProducts.length === 0">
          <i class="fa fa-exclamation-circle"></i>
          <p>暂无符合条件的套餐</p>
        </div>
      </div>
      
      <!-- 分页控制 -->
      <div class="pagination-container">
        <div class="pagination">
          <div class="pagination-btn" 
               :class="{disabled: pagination.page <= 1}" 
               @click="changePage(pagination.page - 1)">
            <i class="fa fa-angle-left"></i>
          </div>
          
          <div class="pagination-indicator">
            <span>{{ pagination.page }}</span> / <span>{{ Math.ceil(pagination.total / pagination.pageSize) || 1 }}</span>
          </div>
          
          <div class="pagination-btn" 
               :class="{disabled: pagination.page >= Math.ceil(pagination.total / pagination.pageSize)}" 
               @click="changePage(pagination.page + 1)">
            <i class="fa fa-angle-right"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- 全屏筛选页面 -->
    <div v-if="showFilter" class="filter-fullscreen">
      <div class="filter-header">
        <div class="filter-back" @click="showFilter = false">
          <i class="fa fa-arrow-left"></i>
        </div>
        <div class="filter-title">筛选</div>
        <div class="filter-placeholder"></div>
      </div>

      <div class="filter-content">
        <h3>适用人群</h3>
        <div class="filter-tags">
          <div class="filter-tag" :class="{active: ageFilter === 'all'}" @click="selectAgeFilter('all')">不限</div>
          <div class="filter-tag" :class="{active: ageFilter === 'middle'}" @click="selectAgeFilter('middle')">中青年</div>
          <div class="filter-tag" :class="{active: ageFilter === 'elder'}" @click="selectAgeFilter('elder')">中老年</div>
          <div class="filter-tag" :class="{active: ageFilter === 'child'}" @click="selectAgeFilter('child')">儿童</div>
          <div class="filter-tag" :class="{active: ageFilter === 'pregnant'}" @click="selectAgeFilter('pregnant')">孕妇</div>
        </div>

        <h3>适用性别</h3>
        <div class="filter-tags">
          <div class="filter-tag" :class="{active: genderFilter === 'all'}" @click="selectGenderFilter('all')">不限</div>
          <div class="filter-tag" :class="{active: genderFilter === 'male'}" @click="selectGenderFilter('male')">男性</div>
          <div class="filter-tag" :class="{active: genderFilter === 'female'}" @click="selectGenderFilter('female')">女性</div>
        </div>

        <h3>包含检项</h3>
        <div class="filter-tags">
          <div class="filter-tag" 
               v-for="(item, index) in checkItemOptions" 
               :key="'item-'+index"
               :class="{active: checkItems.includes(item.id.toString())}" 
               @click="selectCheckItem(item.id.toString())">
            {{item.name}}
          </div>
        </div>
      </div>

      <div class="filter-result-container" v-if="filteredByFilter.length > 0">
        <div class="filter-result-header">
          <h3>筛选结果 <span class="result-count">({{ filteredByFilter.length }})</span></h3>
          <div class="filter-result-view-all" v-if="filteredByFilter.length > 6" @click="viewAllResults">
            查看全部 <i class="fa fa-angle-right"></i>
          </div>
        </div>
        <div class="filter-result-list">
          <div class="filter-result-item" 
               v-for="(product, index) in filteredByFilter.slice(0, 6)" 
               :key="'result-'+index" 
               @click="viewProductFromFilter(product)">
            <div class="filter-result-image">
              <img :src="product.image" alt="体检套餐">
            </div>
            <div class="filter-result-info">
              <div class="filter-result-title">{{ product.title }}</div>
              <div class="filter-result-price">¥{{ product.price }}</div>
            </div>
          </div>
        </div>
        <div class="filter-result-more" v-if="filteredByFilter.length > 6">
          <div class="filter-result-more-text" @click="viewAllResults">
            查看全部{{ filteredByFilter.length }}个套餐
          </div>
        </div>
      </div>
      
      <div class="filter-buttons filter-bottom">
        <div class="reset-btn" @click="resetFilter">重置</div>
        <div class="confirm-btn" @click="closeFilter">关闭</div>
      </div>
    </div>

    <!-- 底部导航 -->
    <Footer></Footer>

    <!-- 点击除排序面板外的任何地方关闭排序面板 -->
    <div v-if="showSort" class="click-outside-handler" @click="closeAllPanels"></div>
    
    <!-- 对比悬浮栏 -->
    <div class="compare-bar" v-if="compareItems.length > 0">
      <div class="compare-items">
        <div class="compare-item" v-for="(item, index) in compareItems" :key="index">
          <img :src="item.image" alt="体检套餐">
          <div class="compare-item-title">{{item.title}}</div>
          <div class="remove-btn" @click="compareItems.splice(index, 1)">×</div>
        </div>
        <div class="compare-item empty" v-if="compareItems.length === 1">
          <div class="add-more">请再选择一个套餐</div>
        </div>
      </div>
      <div class="compare-actions">
        <div class="compare-clear" @click="clearCompare">清空</div>
        <div class="compare-btn" @click="goToComparePage" :class="{disabled: compareItems.length < 2}">对比</div>
      </div>
    </div>
    
    <!-- 提示框 -->
    <div class="toast" v-if="showCompareToast">
      <div class="toast-content">
        <div class="toast-message">{{compareToastMessage}}</div>
        <div class="toast-actions" v-if="compareItems.length === 2 && compareToastMessage.includes('是否前往')">
          <div class="toast-action-btn" @click="showCompareToast = false">取消</div>
          <div class="toast-action-btn confirm" @click="goToComparePage">确定</div>
        </div>
      </div>
    </div>

    <!-- 套餐弹窗 -->
    <div class="category-modal" v-if="showCategoryModal">
      <div class="category-modal-overlay" @click="closeCategoryModal"></div>
      <div class="category-modal-container">
        <div class="category-modal-header">
          <span>{{ currentCategory.title }}</span>
          <i class="fa fa-close" @click="closeCategoryModal"></i>
        </div>
        <div class="category-modal-content">
          <div v-if="filteredCategoryProducts.length === 0" class="no-data-message">
            <i class="fa fa-exclamation-circle"></i>
            <p>暂无相关套餐</p>
          </div>
          <div v-else class="category-product-list">
            <div class="category-product-item" 
                 v-for="(product, index) in filteredCategoryProducts" 
                 :key="index">
              <div class="category-product-image">
                <img :src="product.image" alt="体检套餐">
              </div>
              <div class="category-product-info">
                <div class="category-product-title">{{ product.title }}</div>
                <div class="category-product-features">
                  <span v-for="(feature, idx) in product.features.slice(0, 3)" :key="idx">{{ feature }}</span>
                  <span v-if="product.features.length > 3">+{{ product.features.length - 3 }}项</span>
                </div>
                <div class="category-product-price">¥{{ product.price }}</div>
              </div>
              <div class="category-product-action">
                <div class="category-view-btn" @click="viewProductDetail(product)">
                  查看详情
                </div>
                <div class="category-add-btn" @click="addToCart(product)">
                  加入购物车
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted, reactive, onUnmounted, watch } from 'vue';
import Footer from '@/components/Footer.vue';
import { useRouter } from 'vue-router';
import axios from '../router/axios';
// 导入图片
import meal1 from '@/assets/image/meal1.jpg';
import meal2 from '@/assets/image/meal2.jpg';
import meal3 from '@/assets/image/meal3.jpg';
import meal4 from '@/assets/image/meal4.jpg';

export default {
  components: {
    Footer
  },
  setup() {
    const showSort = ref(false);
    const showFilter = ref(false);
    const showCitySelector = ref(false);
    const selectedCity = ref('广州');
    const sortType = ref('default');
    const ageFilter = ref('all');
    const genderFilter = ref('all');
    const checkItems = ref([]);
    const searchInput = ref(null);
    const router = useRouter();
    
    // 对比功能相关
    const compareItems = ref([]);
    const showCompareToast = ref(false);
    const compareToastMessage = ref('');
    
    // 分页相关
    const pagination = reactive({
      page: 1,
      pageSize: 3, // 确保每页显示3个套餐
      total: 0
    });
    
    // 筛选条件
    const searchParams = reactive({
      gender: 'all',
      age: 'all',
      keyword: ''
    });
    
    // 筛选后的结果
    const filteredByFilter = ref([]);
    
    // 分类弹窗相关
    const showCategoryModal = ref(false);
    const currentCategory = ref({
      type: '',
      title: ''
    });
    const filteredCategoryProducts = ref([]);
    
    // 各分类套餐数据
    const categoryProducts = reactive({
      entry: [
        {
          smid: 101,
          title: "企业入职体检套餐A",
          price: "299",
          image: meal1,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '胸部X光'],
          gender: 'all'
        },
        {
          smid: 102,
          title: "企业入职体检套餐B",
          price: "399",
          image: meal2,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '心电图', '胸部X光'],
          gender: 'all'
        },
        {
          smid: 103,
          title: "公务员入职体检套餐",
          price: "599",
          image: meal3,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '心电图', '胸部X光', '腹部超声'],
          gender: 'all'
        }
      ],
      middle: [
        {
          smid: 201,
          title: "中青年基础体检套餐",
          price: "499",
          image: meal2,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '血脂检查', '血糖检查'],
          gender: 'all'
        },
        {
          smid: 202,
          title: "中青年全面体检套餐",
          price: "699",
          image: meal3,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '血脂检查', '血糖检查', '甲状腺功能', '肿瘤标志物'],
          gender: 'all'
        },
        {
          smid: 203,
          title: "中青年精英体检套餐",
          price: "999",
          image: meal4,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '血脂检查', '血糖检查', '甲状腺功能', '肿瘤标志物', '全身CT', '营养评估'],
          gender: 'all'
        }
      ],
      elder: [
        {
          smid: 301,
          title: "老年人基础体检套餐",
          price: "599",
          image: meal1,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '血脂检查', '血糖检查', '心电图'],
          gender: 'all'
        },
        {
          smid: 302,
          title: "老年人全面体检套餐",
          price: "899",
          image: meal4,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '血脂检查', '血糖检查', '心电图', '骨密度', '颈部血管彩超'],
          gender: 'all'
        },
        {
          smid: 303,
          title: "老年人关爱体检套餐",
          price: "1299",
          image: meal2,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '血脂检查', '血糖检查', '心电图', '骨密度', '颈部血管彩超', '全身CT', '脑部MRI'],
          gender: 'all'
        }
      ],
      female: [
        {
          smid: 401,
          title: "女性基础体检套餐",
          price: "599",
          image: meal3,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '妇科检查', '乳腺检查'],
          gender: 'female'
        },
        {
          smid: 402,
          title: "女性全面体检套餐",
          price: "899",
          image: meal1,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '妇科检查', '乳腺检查', '宫颈检查', '盆腔彩超'],
          gender: 'female'
        },
        {
          smid: 403,
          title: "女性优雅体检套餐",
          price: "1299",
          image: meal4,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '妇科检查', '乳腺检查', '宫颈检查', '盆腔彩超', '骨密度', '甲状腺功能'],
          gender: 'female'
        }
      ],
      male: [
        {
          smid: 501,
          title: "男性基础体检套餐",
          price: "599",
          image: meal2,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '前列腺检查'],
          gender: 'male'
        },
        {
          smid: 502,
          title: "男性全面体检套餐",
          price: "899",
          image: meal3,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '前列腺检查', '精液检查', '男性激素六项'],
          gender: 'male'
        },
        {
          smid: 503,
          title: "男性精英体检套餐",
          price: "1299",
          image: meal1,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '前列腺检查', '精液检查', '男性激素六项', '颈部血管彩超', '心电图'],
          gender: 'male'
        }
      ],
      special: [
        {
          smid: 601,
          title: "心脑血管检测套餐",
          price: "899",
          image: meal4,
          features: ['心电图', '血脂四项', '颈部血管彩超', '冠状动脉CT', '脑血管造影'],
          gender: 'all'
        },
        {
          smid: 602,
          title: "消化系统检测套餐",
          price: "999",
          image: meal1,
          features: ['肝功能', '胃镜', '肠镜', '腹部彩超', '消化道造影'],
          gender: 'all'
        },
        {
          smid: 603,
          title: "呼吸系统检测套餐",
          price: "799",
          image: meal2,
          features: ['肺功能', '胸部CT', '支气管镜', '肺部灌洗液检查'],
          gender: 'all'
        }
      ],
      marriage: [
        {
          smid: 701,
          title: "婚前体检套餐",
          price: "799",
          image: meal3,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '传染病检测', '生殖系统检查'],
          gender: 'all'
        },
        {
          smid: 702,
          title: "孕前检查套餐",
          price: "899",
          image: meal4,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '生殖系统检查', '遗传疾病筛查', '激素六项'],
          gender: 'all'
        },
        {
          smid: 703,
          title: "产前检查套餐",
          price: "999",
          image: meal1,
          features: ['一般检查', '血常规', '尿常规', '肝功能', '孕期营养评估', '四维彩超', '唐筛', '产前筛查'],
          gender: 'female'
        }
      ],
      cancer: [
        {
          smid: 801,
          title: "肺癌筛查套餐",
          price: "1299",
          image: meal2,
          features: ['低剂量螺旋CT', '肿瘤标志物', '基因检测', '肺功能'],
          gender: 'all'
        },
        {
          smid: 802,
          title: "肠癌筛查套餐",
          price: "1399",
          image: meal3,
          features: ['肠镜', '肿瘤标志物', '基因检测', '便潜血检测'],
          gender: 'all'
        },
        {
          smid: 803,
          title: "前列腺癌筛查套餐",
          price: "1199",
          image: meal4,
          features: ['PSA检测', '直肠指检', '超声检查', '组织活检'],
          gender: 'male'
        },
        {
          smid: 804,
          title: "乳腺癌筛查套餐",
          price: "1199",
          image: meal1,
          features: ['乳腺触诊', '乳腺彩超', '乳腺X线检查', '肿瘤标志物'],
          gender: 'female'
        },
        {
          smid: 805,
          title: "宫颈癌筛查套餐",
          price: "999",
          image: meal2,
          features: ['宫颈TCT', 'HPV检测', '阴道镜检查', '肿瘤标志物'],
          gender: 'female'
        }
      ],
      ct: [
        {
          smid: 901,
          title: "全身低剂量CT",
          price: "1899",
          image: meal3,
          features: ['胸部CT', '腹部CT', '盆腔CT', '三维重建'],
          gender: 'all'
        },
        {
          smid: 902,
          title: "脑部MRI套餐",
          price: "1999",
          image: meal4,
          features: ['脑MRI', '脑血管造影', '功能MRI', '三维重建'],
          gender: 'all'
        },
        {
          smid: 903,
          title: "心脑血管MRI/CT套餐",
          price: "2299",
          image: meal1,
          features: ['冠状动脉CT', '心脏MRI', '脑血管MRI', '脑部CT'],
          gender: 'all'
        }
      ],
      premium: [
        {
          smid: 1001,
          title: "VIP尊享体检套餐",
          price: "3999",
          image: meal2,
          features: ['全身体检', '血液全套', '全身CT/MRI', '高端专家门诊', '私人医疗助理'],
          gender: 'all'
        },
        {
          smid: 1002,
          title: "CEO体检套餐",
          price: "5999",
          image: meal3,
          features: ['全身体检', '血液全套', '全身CT/MRI', '基因检测', '营养评估', '心理健康评估', '私人医疗团队'],
          gender: 'all'
        },
        {
          smid: 1003,
          title: "家庭全套体检",
          price: "9999",
          image: meal4,
          features: ['全家4人套餐', '全身体检', '血液全套', '全身CT/MRI', '家庭病史评估', '遗传风险评估', '家庭医生服务'],
          gender: 'all'
        }
      ]
    });
    
    // 点击分类显示对应套餐
    const filterByCategory = (type) => {
      // 设置当前分类
      currentCategory.value = {
        type: type,
        title: getCategoryTitle(type)
      };
      
      // 根据分类获取对应套餐
      filteredCategoryProducts.value = categoryProducts[type] || [];
      
      // 显示弹窗
      showCategoryModal.value = true;
    };
    
    // 关闭分类弹窗
    const closeCategoryModal = () => {
      showCategoryModal.value = false;
    };
    
    // 获取分类标题
    const getCategoryTitle = (type) => {
      const titles = {
        entry: '入职体检套餐',
        middle: '中青年优选套餐',
        elder: '中老年臻爱套餐',
        female: '女性甄选套餐',
        male: '男士套餐',
        special: '专项检测套餐',
        marriage: '婚检孕检套餐',
        cancer: '防癌筛查套餐',
        ct: '磁共振/CT套餐',
        premium: '尊享高端套餐'
      };
      
      return titles[type] || '体检套餐';
    };
    
    // 添加套餐到对比列表
    const addToCompare = (product) => {
      // 检查产品是否已在对比列表中
      const exists = compareItems.value.some(item => item.smid === product.smid);
      
      if (exists) {
        displayCompareToast('该套餐已在对比列表中');
        return;
      }
      
      // 检查对比列表是否已满
      if (compareItems.value.length >= 2) {
        displayCompareToast('对比列表已满，请先清空后再添加');
        if (compareItems.value.length === 2) {
          setTimeout(() => {
            displayCompareToast('是否前往对比页面？', true);
          }, 1500);
        }
        return;
      }
      
      // 显示加载提示
      displayCompareToast('正在获取套餐详情...');
      
      // 创建临时对象，以便于异步更新
      let itemToCompare = { ...product };
      
      // 确保先将基本版本添加到对比列表
      compareItems.value.push(itemToCompare);
      
      // 尝试从/api/setmeal/detail/{smid}获取套餐详细信息
      const fetchDetailedSetmeal = async (smid) => {
        try {
          const token = localStorage.getItem('token');
          if (!token) {
            console.error('未检测到登录token，无法获取详细信息');
            return null;
          }
          
          const response = await axios.get(`/api/setmeal/detail/${smid}`, {
            headers: {
              'token': token
            }
          });
          
          console.log(`套餐${smid}详情响应:`, response);
          
          if (response.data && response.data.code === 1 && response.data.data) {
            return response.data.data;
          }
        } catch (error) {
          console.error(`获取套餐${smid}详情失败:`, error);
        }
        return null;
      };
      
      // 更新对比项目的体检小项信息
      // 不再使用fetchDetailedSetmeal，改为直接获取体检小项
      const fetchCheckItemDetails = async () => {
        try {
          const token = localStorage.getItem('token');
          if (!token) {
            console.error('未检测到登录token，无法获取体检小项');
            return;
          }
          
          // 获取所有体检小项
          const response = await axios.get('/api/setmeal/checkitemdetails', {
            headers: {
              'token': token
            }
          });
          
          console.log('体检小项API响应:', response);
          
          if (response.data && response.data.code === 1 && response.data.data) {
            const allDetails = response.data.data;
            console.log(`获取到${allDetails.length}个体检小项:`, allDetails);
            
            // 查找已添加的项目
            const index = compareItems.value.findIndex(item => item.smid === product.smid);
            if (index !== -1) {
              // 根据套餐类型筛选可能的体检小项
              const name = product.title ? product.title.toLowerCase() : '';
              const checkDetails = [];
              
              if (name.includes('基础') || name.includes('入职')) {
                // 基础套餐相关的体检小项
                const relevantDetails = allDetails.filter(detail => 
                  detail.name.includes('血压') || 
                  detail.name.includes('身高') || 
                  detail.name.includes('体重') || 
                  detail.name.includes('血细胞') || 
                  detail.name.includes('尿')
                );
                
                relevantDetails.forEach(detail => {
                  checkDetails.push({
                    id: detail.cdid,
                    name: detail.name,
                    value: '√',
                    hint: detail.name.includes('尿') ? '尿常规' : 
                          detail.name.includes('血细胞') ? '血常规' : '一般检查'
                  });
                });
              } else if (name.includes('肾') || name.includes('泌尿')) {
                // 肾病套餐相关的体检小项
                const relevantDetails = allDetails.filter(detail => 
                  detail.name.includes('肌酐') || 
                  detail.name.includes('尿素') || 
                  detail.name.includes('尿酸') || 
                  detail.name.includes('尿')
                );
                
                relevantDetails.forEach(detail => {
                  checkDetails.push({
                    id: detail.cdid,
                    name: detail.name,
                    value: '√',
                    hint: detail.name.includes('尿') && !detail.name.includes('尿素') ? '尿常规' : '肾功能检查'
                  });
                });
              } else if (name.includes('肝') || name.includes('消化')) {
                // 肝病套餐相关的体检小项
                const relevantDetails = allDetails.filter(detail => 
                  detail.name.includes('谷草') || 
                  detail.name.includes('谷丙') || 
                  detail.name.includes('胆红素') || 
                  detail.name.includes('蛋白') ||
                  detail.name.includes('肝')
                );
                
                relevantDetails.forEach(detail => {
                  checkDetails.push({
                    id: detail.cdid,
                    name: detail.name,
                    value: '√',
                    hint: '肝功能检查'
                  });
                });
              }
              
              if (checkDetails.length > 0) {
                console.log(`成功筛选出${checkDetails.length}个体检小项`);
                compareItems.value[index].checkItemDetails = checkDetails;
              } else {
                // 如果筛选不到特定的项目，使用通用项目
                const genericDetails = allDetails.slice(0, 5).map(detail => ({
                  id: detail.cdid,
                  name: detail.name,
                  value: '√',
                  hint: '常规检查'
                }));
                
                if (genericDetails.length > 0) {
                  console.log(`使用通用体检小项:`, genericDetails);
                  compareItems.value[index].checkItemDetails = genericDetails;
                }
              }
              
              // 确保checkItemDetails存在
              if (!compareItems.value[index].checkItemDetails || 
                  !Array.isArray(compareItems.value[index].checkItemDetails) || 
                  compareItems.value[index].checkItemDetails.length === 0) {
                // 生成检查项目详情
                generateMockDetails(compareItems.value[index]);
              }
            }
          }
        } catch (error) {
          console.error('获取体检小项失败:', error);
          // 如果无法获取详细数据，为已添加的套餐生成模拟数据
          const index = compareItems.value.findIndex(item => item.smid === product.smid);
          if (index !== -1) {
            generateMockDetails(compareItems.value[index]);
          }
        }
      };
      
      fetchCheckItemDetails();
      displayCompareToast('已添加到对比列表');
      
      // 如果已有两个套餐，提示是否前往对比页面
      if (compareItems.value.length === 2) {
        setTimeout(() => {
          displayCompareToast('是否前往对比页面？', true);
        }, 1500);
      }
    };
    
    // 生成模拟检查项目详情的函数
    const generateMockDetails = (item) => {
      // 根据套餐名称或ID生成模拟的检查项目详情
      const name = item.title ? item.title.toLowerCase() : '';
      const smid = item.smid;
      
      if (name.includes('基础') || name.includes('入职') || smid === 1 || smid === 4) {
        item.checkItemDetails = [
          { id: 1, name: '收缩压', value: '√', hint: '血压检测' },
          { id: 2, name: '舒张压', value: '√', hint: '血压检测' },
          { id: 7, name: '白细胞计数', value: '√', hint: '血常规' },
          { id: 8, name: '红细胞压值', value: '√', hint: '血常规' },
          { id: 9, name: '淋巴细胞计数百分比', value: '√', hint: '血常规' },
          { id: 15, name: '尿白细胞', value: '√', hint: '尿常规' },
          { id: 16, name: '尿亚硝酸盐', value: '√', hint: '尿常规' }
        ];
      } else if (name.includes('肾') || name.includes('泌尿') || smid === 2 || smid === 5) {
        item.checkItemDetails = [
          { id: 21, name: '血清肌酐', value: '√', hint: '肾功能检查' },
          { id: 22, name: '血尿素氮', value: '√', hint: '肾功能检查' },
          { id: 23, name: '血尿酸', value: '√', hint: '肾功能检查' },
          { id: 24, name: '白蛋白', value: '√', hint: '肾功能检查' },
          { id: 16, name: '尿亚硝酸盐', value: '√', hint: '尿常规' },
          { id: 15, name: '尿白细胞', value: '√', hint: '尿常规' }
        ];
      } else if (name.includes('肝') || name.includes('消化') || smid === 3 || smid === 6) {
        item.checkItemDetails = [
          { id: 25, name: '血清谷草转氨酶', value: '√', hint: '肝功能检查' },
          { id: 26, name: '血清谷丙转氨酶', value: '√', hint: '肝功能检查' },
          { id: 27, name: '血清总胆红素', value: '√', hint: '肝功能检查' },
          { id: 28, name: '血清总蛋白', value: '√', hint: '肝功能检查' }
        ];
      } else {
        // 默认检查项
        item.checkItemDetails = [
          { id: 1, name: '基本体检', value: '√', hint: '基础检查' },
          { id: 2, name: '常规检查', value: '√', hint: '基础检查' }
        ];
      }
      
      console.log(`为套餐${item.smid}生成的模拟检查项目:`, item.checkItemDetails);
    };
    
          // 前往对比页面
    const goToComparePage = () => {
      if (compareItems.value.length < 2) {
        compareToastMessage.value = '请选择2个套餐进行对比';
        showCompareToast.value = true;
        setTimeout(() => {
          showCompareToast.value = false;
        }, 2000);
        return;
      }
      
      // 检查是否有checkItemDetails
      for(let i = 0; i < compareItems.value.length; i++) {
        const item = compareItems.value[i];
        console.log(`对比项${i+1}:`, item);
        
        // 从模拟数据中获取检查项目详情
        if(!item.checkItemDetails || !Array.isArray(item.checkItemDetails) || item.checkItemDetails.length === 0) {
          console.warn(`对比项${i+1}没有检查项目详情或为空数组，添加模拟数据`);
          generateMockDetails(item);
          console.log(`为对比项${i+1}添加了${item.checkItemDetails.length}个模拟检查项目数据`);
        } else {
          console.log(`对比项${i+1}已有${item.checkItemDetails.length}个检查项目详情`);
        }
      }
      
      // 将对比套餐数据存入localStorage
      const compareItemsJson = JSON.stringify(compareItems.value);
      localStorage.setItem('compareItems', compareItemsJson);
      console.log('保存到localStorage的对比项:', compareItemsJson);
      
      // 跳转到对比页面
      router.push('/compare');
    };
    
    // 清空对比列表
    const clearCompare = () => {
      compareItems.value = [];
      compareToastMessage.value = '已清空对比列表';
      showCompareToast.value = true;
      setTimeout(() => {
        showCompareToast.value = false;
      }, 2000);
    };
    
    // 显示对比提示
    const displayCompareToast = (message, showAction = false) => {
      compareToastMessage.value = message;
      showCompareToast.value = true;
      
      if (!showAction) {
        // 如果不显示操作按钮，2秒后自动关闭
        setTimeout(() => {
          showCompareToast.value = false;
        }, 2000);
      }
    };
    
    // 热门城市
    const hotCities = ref([
      '北京', '上海', '广州', '深圳', '杭州', 
      '南京', '成都', '重庆', '武汉', '西安'
    ]);
    
    // 省份和城市数据
    const provinces = ref([
      {
        name: '广东省',
        cities: ['广州', '深圳', '珠海', '佛山', '东莞', '中山', '惠州']
      },
      {
        name: '浙江省',
        cities: ['杭州', '宁波', '温州', '嘉兴', '湖州', '绍兴']
      },
      {
        name: '江苏省',
        cities: ['南京', '无锡', '徐州', '常州', '苏州', '南通']
      },
      {
        name: '湖北省',
        cities: ['武汉', '黄石', '十堰', '荆州', '宜昌', '襄阳', '鄂州']
      },
      {
        name: '四川省',
        cities: ['成都', '自贡', '攀枝花', '泸州', '德阳', '绵阳', '广元']
      },
      {
        name: '河南省',
        cities: ['郑州', '开封', '洛阳', '平顶山', '安阳', '鹤壁', '新乡']
      }
    ]);
    
    // 选择城市
    const selectCity = (city) => {
      selectedCity.value = city;
      showCitySelector.value = false;
      console.log('选择城市:', city);
    };

    // 切换城市选择器可见性
    const toggleCitySelector = () => {
      showCitySelector.value = !showCitySelector.value;
      console.log('showCitySelector:', showCitySelector.value);
    };
    
    // 关闭城市选择器
    const closeCitySelector = () => {
      showCitySelector.value = false;
    };
    
    // 聚焦搜索框
    const focusSearch = () => {
      if (searchInput.value) {
        searchInput.value.focus();
      }
    };
    
    const products = ref([]); // 用于存储从后端获取的产品数据
    
          // 从后端获取产品数据
    const fetchProducts = async () => {
      try {
        console.log('开始请求套餐数据...');
        // 获取token
        const token = localStorage.getItem('token');
        if (!token) {
          console.error('未检测到登录token，使用示例数据');
          const allProducts = getSampleProducts();
          
          // 应用筛选
          let filteredList = [...allProducts];
          
          // 应用性别筛选
          if (searchParams.gender !== 'all') {
            filteredList = filteredList.filter(p => 
              p.gender === searchParams.gender || p.gender === 'all'
            );
          }
          
          // 应用年龄筛选
          if (searchParams.age !== 'all') {
            filteredList = filteredList.filter(p => 
              p.age === searchParams.age || p.age === 'all'
            );
          }
          
          // 应用关键字搜索
          if (searchParams.keyword) {
            const keyword = searchParams.keyword.toLowerCase();
            filteredList = filteredList.filter(p => {
              const titleMatch = p.title.toLowerCase().includes(keyword);
              const featureMatch = Array.isArray(p.features) && p.features.some(f => f && f.toLowerCase().includes(keyword));
              return titleMatch || featureMatch;
            });
          }
          
          // 更新总数和当前页数据
          pagination.total = filteredList.length;
          const startIndex = (pagination.page - 1) * pagination.pageSize;
          const endIndex = startIndex + pagination.pageSize;
          products.value = filteredList.slice(startIndex, endIndex);
          console.log(`使用模拟数据，当前页显示：${products.value.length}个，总数：${pagination.total}个，页码：${pagination.page}/${Math.ceil(pagination.total / pagination.pageSize)}`);
          return;
        }
        
        // 使用GET方法获取所有套餐信息(简单版)
        const response = await axios.get('/api/setmeal/all', {
          headers: {
            'token': token
          }
        });
        console.log('API响应:', response);
        
        if (response.data && response.data.code === 1 && response.data.data) {
          // 获取套餐列表
          const setmealList = response.data.data;
          console.log('获取到的套餐列表:', setmealList);
          
          // 转换为前端需要的格式
          const formattedProducts = [];
          
          // 设置分页信息
          pagination.total = setmealList.length;
          
          // 遍历套餐数据并格式化
          for (let i = 0; i < setmealList.length; i++) {
            const setmeal = setmealList[i];
            const imgIndex = (i % 4) + 1; // 循环使用1-4的图片
            let imgSrc;
            
            // 根据索引选择图片
            switch(imgIndex) {
              case 1: imgSrc = meal1; break;
              case 2: imgSrc = meal2; break;
              case 3: imgSrc = meal3; break;
              case 4: imgSrc = meal4; break;
              default: imgSrc = meal1;
            }
            
            // 处理套餐包含的体检大项
            let features = [];
            
                        // 尝试从套餐详情获取套餐包含的体检大项
            console.log(`为套餐${setmeal.smid}(${setmeal.name})获取体检大项`);
            
            try {
              // 由于API问题，不再调用/api/setmeal/detail/{id}
              // 我们之前已经查看了SetmealController.java，尝试访问的API存在，但可能未实现细节
              // console.log(`尝试调用API: /api/setmeal/detail/${setmeal.smid}`);
              // const detailResponse = await axios.get(`/api/setmeal/detail/${setmeal.smid}`, {
              //   headers: {
              //     'token': token
              //   }
              // });
              
              // 直接使用另一个API来获取体检大项信息
              const checkitemResponse = await axios.get('/api/setmeal/checkitems', {
                headers: {
                  'token': token
                }
              });
                             
              console.log(`体检大项API响应:`, checkitemResponse);
              
              if (checkitemResponse.data && checkitemResponse.data.code === 1 && checkitemResponse.data.data) {
                const checkitems = checkitemResponse.data.data;
                console.log(`获取到的所有体检大项:`, checkitems);
                
                // 从名称中推断可能包含的体检大项
                const name = setmeal.name ? setmeal.name.toLowerCase() : '';
                let relevantCheckitems = [];
                
                if (name.includes('基础') || name.includes('入职')) {
                  // 基础套餐通常包含的检查项目
                  relevantCheckitems = checkitems.filter(item => 
                    item.name.includes('一般') || 
                    item.name.includes('血常规') || 
                    item.name.includes('尿常规')
                  );
                } else if (name.includes('肾') || name.includes('泌尿')) {
                  // 肾病套餐通常包含的检查项目
                  relevantCheckitems = checkitems.filter(item => 
                    item.name.includes('肾') || 
                    item.name.includes('泌尿') || 
                    item.name.includes('尿常规')
                  );
                } else if (name.includes('肝') || name.includes('消化')) {
                  // 肝病套餐通常包含的检查项目 
                  relevantCheckitems = checkitems.filter(item => 
                    item.name.includes('肝') || 
                    item.name.includes('消化') || 
                    item.name.includes('胆')
                  );
                } else if (name.includes('女') || name.includes('妇科')) {
                  // 女性套餐通常包含的检查项目
                  relevantCheckitems = checkitems.filter(item => 
                    item.name.includes('妇科') || 
                    item.name.includes('乳腺') || 
                    item.name.includes('女性')
                  );
                } else if (name.includes('男')) {
                  // 男性套餐通常包含的检查项目
                  relevantCheckitems = checkitems.filter(item => 
                    item.name.includes('前列腺') || 
                    item.name.includes('男性') || 
                    item.name.includes('精液')
                  );
                }
                
                if (relevantCheckitems.length > 0) {
                  features = relevantCheckitems.map(item => item.name);
                  console.log(`成功根据API数据推断套餐${setmeal.smid}的体检大项:`, features);
                }
              }
                         } catch (error) {
               console.error(`获取套餐${setmeal.smid}详情失败:`, error);
               console.error(`错误详情:`, error.response ? error.response.data : '无响应数据');
            }
            
            // 如果无法从API获取，根据套餐名称生成
            if (features.length === 0) {
              // 根据套餐名称判断类型
              const name = setmeal.name ? setmeal.name.toLowerCase() : '';
              
              if (name.includes('基础') || name.includes('入职')) {
                features = ['一般检查', '血常规', '尿常规'];
              } else if (name.includes('肾') || name.includes('泌尿')) {
                features = ['肾功能检查', '尿常规', '泌尿系统B超'];
              } else if (name.includes('肝') || name.includes('消化')) {
                features = ['肝功能检查', '消化系统B超', '肝胆脾检查'];
              } else if (name.includes('心') || name.includes('血管')) {
                features = ['心电图检查', '血压检测', '血脂检查'];
              } else if (name.includes('女') || name.includes('妇科')) {
                features = ['妇科检查', '乳腺检查', '宫颈检查'];
              } else if (name.includes('男')) {
                features = ['前列腺检查', '男性激素六项', '精液检查'];
              } else {
                // 默认特性
                features = ['一般检查', '基础体检', (setmeal.gender === 'male' ? '男性' : '女性') + '体检'];
              }
              
              console.log(`使用模拟数据生成套餐${setmeal.smid}的体检大项:`, features);
            }
            
            // 创建产品对象
            const formattedProduct = {
              smid: setmeal.smid,
              image: imgSrc,
              title: setmeal.title || setmeal.name || '未命名套餐',
              features: features, // 使用获取到的体检大项作为特性
              hospital: '爱康未来互联网医院',
              price: setmeal.price ? setmeal.price.toString() : '0',
              sales: Math.floor(1000 + Math.random() * 9000).toString(),
              gender: setmeal.gender || 'all',
              age: setmeal.age || 'all',
              checkItems: setmeal.checkItemCategories || [],
              checkItemDetails: setmeal.checkItemDetails || [] // 确保检查项目详情被保存
            };
            
            // 随机添加一些套餐特性
            if (Math.random() > 0.5) {
              formattedProduct.tag = '热门';
              formattedProduct.subsidy = true;
              formattedProduct.originalPrice = (parseInt(formattedProduct.price) + 200).toString();
            }
            
            formattedProducts.push(formattedProduct);
          }
          
          // 应用前端筛选
          let filteredList = [...formattedProducts];
          
          // 应用性别筛选
          if (searchParams.gender !== 'all') {
            filteredList = filteredList.filter(p => 
              p.gender === searchParams.gender || p.gender === 'all'
            );
          }
          
          // 应用年龄筛选
          if (searchParams.age !== 'all') {
            filteredList = filteredList.filter(p => 
              p.age === searchParams.age || p.age === 'all'
            );
          }
          
          // 应用关键字搜索
          if (searchParams.keyword) {
            const keyword = searchParams.keyword.toLowerCase();
            filteredList = filteredList.filter(p => {
              const titleMatch = p.title.toLowerCase().includes(keyword);
              const featureMatch = Array.isArray(p.features) && p.features.some(f => f && f.toLowerCase().includes(keyword));
              return titleMatch || featureMatch;
            });
          }
          
          // 分页处理
          const startIndex = (pagination.page - 1) * pagination.pageSize;
          const endIndex = startIndex + pagination.pageSize;
          
          // 更新总数和当前页数据
          pagination.total = filteredList.length;
          products.value = filteredList.slice(startIndex, endIndex);
          
          console.log('过滤和分页后的产品数据:', products.value);
        } else {
          console.error('获取产品数据格式异常:', response.data);
          // 如果API返回错误，使用示例数据
          const allProducts = getSampleProducts();
          // 应用筛选和分页
          const filteredList = allProducts;
          pagination.total = filteredList.length;
          const startIndex = (pagination.page - 1) * pagination.pageSize;
          const endIndex = startIndex + pagination.pageSize;
          products.value = filteredList.slice(startIndex, endIndex);
          console.log('使用示例数据:', products.value);
        }
      } catch (error) {
        console.error('获取产品数据失败:', error);
        // 如果API调用失败，使用示例数据
        const allProducts = getSampleProducts();
        // 应用筛选和分页
        const filteredList = allProducts;
        pagination.total = filteredList.length;
        const startIndex = (pagination.page - 1) * pagination.pageSize;
        const endIndex = startIndex + pagination.pageSize;
        products.value = filteredList.slice(startIndex, endIndex);
        console.log('使用示例数据:', products.value);
      }
    };
    
    // 如果API不可用，使用示例数据
    const getSampleProducts = () => {
      // 返回带有正确体检大项的示例数据
      return [
        {
          smid: 1,
          image: meal1,
          title: '男士-基础套餐',
          // 体检大项
          features: ['一般检查', '血常规', '尿常规'],
          hospital: '爱康未来互联网医院',
          price: '350',
          sales: '4587',
          gender: 'male',
          age: 'middle',
          checkItems: [
            { ciid: 1, name: '一般检查' },
            { ciid: 2, name: '血常规' },
            { ciid: 3, name: '尿常规' }
          ],
          checkItemDetails: [
            { id: 1, name: '收缩压', value: '√', hint: '一般检查' },
            { id: 2, name: '舒张压', value: '√', hint: '一般检查' },
            { id: 7, name: '白细胞计数', value: '√', hint: '血常规' },
            { id: 8, name: '红细胞压值', value: '√', hint: '血常规' },
            { id: 15, name: '尿白细胞', value: '√', hint: '尿常规' }
          ]
        },
        {
          smid: 2,
          image: meal2,
          title: '男士-肾病检查',
          // 体检大项
          features: ['肾功能检查', '前列腺检查'],
          hospital: '爱康未来互联网医院',
          price: '600',
          originalPrice: '800',
          sales: '3125',
          gender: 'male',
          age: 'middle',
          checkItems: [
            { ciid: 4, name: '肾功能检查' },
            { ciid: 6, name: '前列腺检查' }
          ],
          checkItemDetails: [
            { id: 21, name: '血清肌酐', value: '√', hint: '肾功能检查' },
            { id: 22, name: '血尿素氮', value: '√', hint: '肾功能检查' },
            { id: 23, name: '血尿酸', value: '√', hint: '肾功能检查' }
          ],
          tag: '专项',
          subsidy: true
        },
        {
          smid: 3,
          image: meal3,
          title: '男士-肝病检查',
          // 体检大项
          features: ['肝功能检查', 'CT检查'],
          hospital: '爱康未来互联网医院',
          price: '600',
          originalPrice: '800',
          sales: '2874',
          gender: 'male',
          age: 'middle',
          checkItems: [
            { ciid: 5, name: '肝功能检查' },
            { ciid: 7, name: 'CT检查' }
          ],
          checkItemDetails: [
            { id: 25, name: '血清谷草转氨酶', value: '√', hint: '肝功能检查' },
            { id: 26, name: '血清谷丙转氨酶', value: '√', hint: '肝功能检查' },
            { id: 27, name: '血清总胆红素', value: '√', hint: '肝功能检查' }
          ],
          tag: '专项',
          subsidy: true
        },
        {
          smid: 4,
          image: meal4,
          title: '女士-基础套餐',
          // 体检大项
          features: ['一般检查', '血常规', '妇科检查'],
          hospital: '爱康未来互联网医院',
          price: '400',
          sales: '5214',
          gender: 'female',
          age: 'middle',
          checkItems: [
            { ciid: 1, name: '一般检查' },
            { ciid: 2, name: '血常规' },
            { ciid: 8, name: '妇科检查' }
          ],
          checkItemDetails: [
            { id: 1, name: '收缩压', value: '√', hint: '一般检查' },
            { id: 2, name: '舒张压', value: '√', hint: '一般检查' },
            { id: 7, name: '白细胞计数', value: '√', hint: '血常规' },
            { id: 30, name: '乳腺检查', value: '√', hint: '妇科检查' }
          ]
        },
        {
          smid: 5,
          image: meal1,
          title: '女士-肾病检查',
          // 体检大项
          features: ['肾功能检查', '盆腔检查'],
          hospital: '爱康未来互联网医院',
          price: '650',
          originalPrice: '850',
          sales: '2365',
          gender: 'female',
          age: 'middle',
          checkItems: [
            { ciid: 4, name: '肾功能检查' },
            { ciid: 9, name: '盆腔检查' }
          ],
          checkItemDetails: [
            { id: 21, name: '血清肌酐', value: '√', hint: '肾功能检查' },
            { id: 22, name: '血尿素氮', value: '√', hint: '肾功能检查' },
            { id: 29, name: '盆腔检查', value: '√', hint: '盆腔检查' }
          ],
          tag: '专项',
          subsidy: true
        },
        {
          smid: 6,
          image: meal2,
          title: '女士-肝病检查',
          // 体检大项
          features: ['肝功能检查', 'CT检查', '妇科检查'],
          hospital: '爱康未来互联网医院',
          price: '650',
          originalPrice: '850',
          sales: '1987',
          gender: 'female',
          age: 'middle',
          checkItems: [
            { ciid: 5, name: '肝功能检查' },
            { ciid: 7, name: 'CT检查' },
            { ciid: 8, name: '妇科检查' }
          ],
          checkItemDetails: [
            { id: 25, name: '血清谷草转氨酶', value: '√', hint: '肝功能检查' },
            { id: 26, name: '血清谷丙转氨酶', value: '√', hint: '肝功能检查' },
            { id: 30, name: '乳腺检查', value: '√', hint: '妇科检查' }
          ],
          tag: '专项',
          subsidy: true
        },
        {
          smid: 7,
          image: meal3,
          title: '中青年体检套餐',
          features: ['一般检查', '血常规', '血脂四项', '肝功能'],
          hospital: '爱康未来互联网医院',
          price: '499',
          sales: '6321',
          gender: 'all',
          age: 'middle',
          checkItems: [
            { ciid: 1, name: '一般检查' },
            { ciid: 2, name: '血常规' },
            { ciid: 5, name: '肝功能检查' }
          ],
          checkItemDetails: [
            { id: 1, name: '收缩压', value: '√', hint: '一般检查' },
            { id: 7, name: '白细胞计数', value: '√', hint: '血常规' },
            { id: 25, name: '血清谷草转氨酶', value: '√', hint: '肝功能检查' }
          ]
        },
        {
          smid: 8,
          image: meal4,
          title: '老年人体检套餐',
          features: ['心脑血管检查', '骨密度测定', '全身彩超'],
          hospital: '爱康未来互联网医院',
          price: '899',
          originalPrice: '1099',
          sales: '2890',
          gender: 'all',
          age: 'elder',
          checkItems: [
            { ciid: 10, name: '心脑血管检查' },
            { ciid: 11, name: '骨密度测定' },
            { ciid: 12, name: '彩超检查' }
          ],
          checkItemDetails: [
            { id: 31, name: '心电图', value: '√', hint: '心脏检查' },
            { id: 32, name: '骨密度', value: '√', hint: '骨骼检查' },
            { id: 33, name: '颈动脉彩超', value: '√', hint: '彩超检查' }
          ],
          tag: '热门',
          subsidy: true
        },
        {
          smid: 9,
          image: meal1,
          title: '入职体检套餐',
          features: ['一般检查', '血常规', '胸部X光', 'B超检查'],
          hospital: '爱康未来互联网医院',
          price: '299',
          sales: '8765',
          gender: 'all',
          age: 'all',
          checkItems: [
            { ciid: 1, name: '一般检查' },
            { ciid: 2, name: '血常规' },
            { ciid: 13, name: 'X光检查' }
          ],
          checkItemDetails: [
            { id: 1, name: '收缩压', value: '√', hint: '一般检查' },
            { id: 7, name: '白细胞计数', value: '√', hint: '血常规' },
            { id: 34, name: '胸部X光', value: '√', hint: 'X光检查' }
          ]
        }
      ];
    };
    
    // 根据筛选和排序条件过滤产品
    const filteredProducts = computed(() => {
      let result = [...products.value];
      
      // 应用年龄筛选
      if (ageFilter.value !== 'all') {
        result = result.filter(p => p.age === ageFilter.value);
      }
      
      // 应用性别筛选
      if (genderFilter.value !== 'all') {
        result = result.filter(p => p.gender === genderFilter.value);
      }
      
      // 应用检查项目筛选
      if (checkItems.value.length > 0) {
        result = result.filter(p => 
          checkItems.value.some(item => p.checkItems && p.checkItems.includes(item))
        );
      }
      
      // 应用排序
      switch(sortType.value) {
        case 'priceLow':
          result.sort((a, b) => parseFloat(a.price) - parseFloat(b.price));
          break;
        case 'priceHigh':
          result.sort((a, b) => parseFloat(b.price) - parseFloat(a.price));
          break;
        case 'salesLow':
          result.sort((a, b) => parseInt(a.sales) - parseInt(b.sales));
          break;
        case 'salesHigh':
          result.sort((a, b) => parseInt(b.sales) - parseInt(a.sales));
          break;
        default:
          // 默认排序，可以自定义
          break;
      }
      
      return result;
    });
    
    // 排序方法
    const selectSort = (type) => {
      console.log('选择排序方式:', type);
      sortType.value = type;
      showSort.value = false;
      console.log('排序方式已设置为:', sortType.value);
    };

    // 切换排序面板可见性
    const toggleSort = () => {
      console.log('toggleSort 被调用, 当前showSort:', showSort.value);
      showSort.value = !showSort.value;
      showFilter.value = false; // 关闭筛选面板
      console.log('toggleSort 调用后, showSort:', showSort.value);
    };

    // 切换筛选面板可见性
    const toggleFilter = () => {
      showFilter.value = !showFilter.value;
      showSort.value = false; // 关闭排序面板
    };
    
    // 检查项目切换
    const toggleCheckItem = (item) => {
      const index = checkItems.value.indexOf(item);
      if (index === -1) {
        checkItems.value.push(item);
      } else {
        checkItems.value.splice(index, 1);
      }
    };
    
    // 选择年龄筛选并立即应用
    const selectAgeFilter = (age) => {
      ageFilter.value = age;
      applyFilter();
    };
    
    // 选择性别筛选并立即应用
    const selectGenderFilter = (gender) => {
      genderFilter.value = gender;
      applyFilter();
    };
    
    // 选择检查项目并立即应用
    const selectCheckItem = (item) => {
      const index = checkItems.value.indexOf(item);
      if (index === -1) {
        checkItems.value.push(item);
      } else {
        checkItems.value.splice(index, 1);
      }
      applyFilter();
    };
    
    // 立即应用筛选
    const applyFilter = () => {
      // 从示例数据或API获取全部产品
      const allProducts = getSampleProducts();
      
      // 应用筛选逻辑
      let result = [...allProducts];
      
      // 应用年龄筛选
      if (ageFilter.value !== 'all') {
        result = result.filter(p => p.age === ageFilter.value || p.age === 'all');
      }
      
      // 应用性别筛选
      if (genderFilter.value !== 'all') {
        result = result.filter(p => p.gender === genderFilter.value || p.gender === 'all');
      }
      
      // 应用检查项目筛选
      if (checkItems.value.length > 0) {
        result = result.filter(p => 
          p.checkItems && p.checkItems.some(item => 
            checkItems.value.includes(item.ciid.toString())
          )
        );
      }
      
      // 更新筛选结果
      filteredByFilter.value = result;
    };
    
    // 查看筛选结果中的产品详情
    const viewProductFromFilter = (product) => {
      localStorage.setItem('currentProduct', JSON.stringify(product));
      
      router.push({
        path: '/setmeal', 
        query: { 
          smid: product.smid
        }
      });
      
      showFilter.value = false;
    };
    
    // 关闭筛选面板
    const closeFilter = () => {
      showFilter.value = false;
    };
    
    // 重置筛选
    const resetFilter = () => {
      ageFilter.value = 'all';
      genderFilter.value = 'all';
      checkItems.value = [];
      
      // 重置筛选参数
      searchParams.age = 'all';
      searchParams.gender = 'all';
      searchParams.keyword = '';
      
      // 重置分页
      pagination.page = 1;
      
      // 清空筛选结果
      filteredByFilter.value = [];
    };
    
    // 确认筛选
    const confirmFilter = () => {
      // 更新筛选参数
      searchParams.age = ageFilter.value;
      searchParams.gender = genderFilter.value;
      
      // 重置分页
      pagination.page = 1;
      
      // 重新获取数据
      fetchProducts();
      
      // 关闭筛选面板
      showFilter.value = false;
    };
    
    // 关闭所有面板
    const closeAllPanels = () => {
      showSort.value = false;
      showFilter.value = false;
      showCitySelector.value = false; // 同时关闭城市选择器
    };
    
    // 监听页面滚动，关闭所有面板
    const handleScroll = () => {
      // 添加延迟，避免干扰点击事件
      setTimeout(() => {
        if (showSort.value || showFilter.value) {
          showSort.value = false;
          showFilter.value = false;
        }
      }, 100);
    };

    // 购物车相关
    const cartItemCount = ref(0);

    // 更新购物车数量
    const updateCartCount = () => {
      const cart = localStorage.getItem('shoppingCart');
      if (cart) {
        try {
          const cartItems = JSON.parse(cart);
          cartItemCount.value = cartItems.length;
        } catch (e) {
          console.error('解析购物车数据出错:', e);
          cartItemCount.value = 0;
        }
      } else {
        cartItemCount.value = 0;
      }
    };

    // 监听购物车变化
    window.addEventListener('storage', (e) => {
      if (e.key === 'shoppingCart') {
        updateCartCount();
      }
    });

    // 添加到购物车
    const addToCart = (product) => {
      // 获取现有购物车
      let cart = localStorage.getItem('shoppingCart');
      let cartItems = cart ? JSON.parse(cart) : [];
      
      // 检查是否已在购物车中
      const exists = cartItems.some(item => item.smid === product.smid);
      
      if (!exists) {
        // 添加到购物车
        cartItems.push(product);
        localStorage.setItem('shoppingCart', JSON.stringify(cartItems));
        displayCompareToast(`已将${product.title}加入购物车`);
        updateCartCount(); // 更新购物车数量
      } else {
        displayCompareToast('该套餐已在购物车中');
      }
    };
    
    onMounted(() => {
      fetchProducts(); // 在组件挂载时获取产品数据
      console.log('当前选择的城市:', selectedCity.value);
      
      // 添加滚动事件监听
      window.addEventListener('scroll', handleScroll);
      
      // 初始化购物车数量
      updateCartCount();
      
      // 检查是否有保存的筛选条件
      const savedFilterConditions = localStorage.getItem('filterConditions');
      if (savedFilterConditions) {
        try {
          const conditions = JSON.parse(savedFilterConditions);
          
          // 应用保存的筛选条件
          if (conditions.age) ageFilter.value = conditions.age;
          if (conditions.gender) genderFilter.value = conditions.gender;
          if (conditions.checkItems) checkItems.value = conditions.checkItems;
          
          // 应用筛选
          setTimeout(() => {
            applyFilter();
          }, 300);
        } catch (e) {
          console.error('解析筛选条件出错:', e);
        }
      }
    });

    onUnmounted(() => {
      // 组件卸载时移除事件监听
      window.removeEventListener('scroll', handleScroll);
      window.removeEventListener('storage', updateCartCount);
    });

    // 排序显示名称
    const sortDisplayName = computed(() => {
      switch(sortType.value) {
        case 'default':
          return '默认排序';
        case 'priceLow':
          return '价格(从低到高)';
        case 'priceHigh':
          return '价格(从高到低)';
        case 'salesLow':
          return '销量(从低到高)';
        case 'salesHigh':
          return '销量(从高到低)';
        default:
          return '默认排序';
      }
    });

    // 分页变化
    const changePage = (newPage) => {
      if (newPage >= 1 && newPage <= Math.ceil(pagination.total / pagination.pageSize)) {
        console.log(`切换到第${newPage}页`);
        pagination.page = newPage;
        
        // 如果使用的是模拟数据，直接处理分页，不需要再调用fetchProducts
        if (products.value.length > 0 && products.value[0].smid <= 6) { // 假设模拟数据的smid为1-6
          const allProducts = getSampleProducts();
          // 应用筛选
          let filteredList = [...allProducts];
          
          // 应用性别筛选
          if (searchParams.gender !== 'all') {
            filteredList = filteredList.filter(p => 
              p.gender === searchParams.gender || p.gender === 'all'
            );
          }
          
          // 应用年龄筛选
          if (searchParams.age !== 'all') {
            filteredList = filteredList.filter(p => 
              p.age === searchParams.age || p.age === 'all'
            );
          }
          
          // 应用关键字搜索
          if (searchParams.keyword) {
            const keyword = searchParams.keyword.toLowerCase();
            filteredList = filteredList.filter(p => {
              const titleMatch = p.title.toLowerCase().includes(keyword);
              const featureMatch = Array.isArray(p.features) && p.features.some(f => f && f.toLowerCase().includes(keyword));
              return titleMatch || featureMatch;
            });
          }
          
          // 更新总数和当前页数据
          pagination.total = filteredList.length;
          const startIndex = (pagination.page - 1) * pagination.pageSize;
          const endIndex = startIndex + pagination.pageSize;
          products.value = filteredList.slice(startIndex, endIndex);
          console.log(`当前页显示产品：${products.value.length}个，总数：${pagination.total}个`);
        } else {
          // 如果不是模拟数据，则通过API获取新数据
          fetchProducts();
        }
      }
    };

    // 执行搜索
    const doSearch = () => {
      pagination.page = 1; // 搜索时重置到第一页
      fetchProducts();
    };

    // 检查项目选项（改为体检大项）
    const checkItemOptions = ref([
      { id: 1, name: '一般检查' },
      { id: 2, name: '血常规' },
      { id: 3, name: '尿常规' },
      { id: 4, name: '肝功能' },
      { id: 5, name: '肾功能' },
      { id: 6, name: '心电图' },
      { id: 7, name: '血脂四项' },
      { id: 8, name: '血糖检查' },
      { id: 9, name: '甲状腺功能' },
      { id: 10, name: '肿瘤标志物' },
      { id: 11, name: '妇科检查' },
      { id: 12, name: '乳腺检查' },
      { id: 13, name: '前列腺检查' },
      { id: 14, name: '骨密度测定' },
      { id: 15, name: 'CT检查' },
      { id: 16, name: 'MRI检查' },
      { id: 17, name: '彩超检查' },
      { id: 18, name: 'X光检查' },
      { id: 19, name: '营养评估' },
      { id: 20, name: '精液检查' }
    ]);

    // 跳转到自选体检页面
    const goToCustomCheck = () => {
      router.push('/custom-check');
    };

    // 查看产品详情
    const viewProductDetail = (product) => {
      // 将产品添加到localStorage以便在详情页查看
      localStorage.setItem('currentProduct', JSON.stringify(product));
      
      // 根据产品ID跳转到详情页
      router.push({
        path: '/setmeal', 
        query: { 
          type: currentCategory.value.type,
          smid: product.smid
        }
      });
      
      // 关闭弹窗
      closeCategoryModal();
    };

    // 查看所有筛选结果
    const viewAllResults = () => {
      // 将筛选结果存入 localStorage
      localStorage.setItem('filteredProducts', JSON.stringify(filteredByFilter.value));
      
      // 将筛选条件存入 localStorage
      localStorage.setItem('filterConditions', JSON.stringify({
        age: ageFilter.value,
        gender: genderFilter.value,
        checkItems: checkItems.value
      }));
      
      // 关闭筛选面板并跳转到筛选结果页面
      showFilter.value = false;
      
      // 更新产品列表
      products.value = filteredByFilter.value;
      
      // 显示消息提示
      displayCompareToast('已应用筛选条件');
    };

    return {
      showSort,
      showFilter,
      showCitySelector,
      selectedCity,
      hotCities,
      provinces,
      selectCity,
      sortType,
      ageFilter,
      genderFilter,
      checkItems,
      products,
      filteredProducts,
      selectSort,
      toggleCheckItem,
      resetFilter,
      confirmFilter,
      closeAllPanels,
      toggleCitySelector,
      closeCitySelector,
      focusSearch,
      searchInput,
      sortDisplayName,
      toggleSort,
      toggleFilter,
      addToCompare,
      compareItems,
      showCompareToast,
      compareToastMessage,
      goToComparePage,
      clearCompare,
      pagination,
      changePage,
      doSearch,
      searchParams,
      displayCompareToast,
      checkItemOptions,
      handleScroll,
      cartItemCount,
      updateCartCount,
      addToCart,
      goToCustomCheck,
      showCategoryModal,
      currentCategory,
      filteredCategoryProducts,
      filterByCategory,
      closeCategoryModal,
      getCategoryTitle,
      viewProductDetail,
      selectAgeFilter,
      selectGenderFilter,
      selectCheckItem,
      filteredByFilter,
      viewProductFromFilter,
      closeFilter,
      applyFilter,
      viewAllResults
    };
  }
};
</script>

<style scoped>
.wrapper {
  width: 100%;
  background-color: #F9F9F9;
  padding-bottom: 15vw;
}

/* 头部 */
header {
  width: 100%;
  background-color: #FFF;
  padding-top: 5vw;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.header-content {
  width: 100%;
  height: 12vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 4vw;
}

.header-content-left {
  display: flex;
  align-items: center;
  width: 20%;
  justify-content: flex-start;
  cursor: pointer;
}

.city-name {
  font-size: 4vw;
  font-weight: 500;
  white-space: nowrap;
  overflow: visible; /* 修改为可见 */
  margin-right: 1vw;
  color: #333;
}

.header-content-left i {
  margin-left: 0;
  font-size: 3.5vw;
  cursor: pointer;
  color: #999;
}

.header-content-center {
  width: 40%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.mall-title {
  font-size: 5vw;
  font-weight: 600;
  color: #FF6B01;
  font-family: 'Arial Rounded MT Bold', 'Microsoft YaHei', sans-serif;
  text-shadow: 0px 1px 1px rgba(0, 0, 0, 0.1);
  letter-spacing: 0.5vw;
}

.header-content-right {
  width: 40%;
  display: flex;
  justify-content: flex-end;
}

/* 右侧迷你搜索框 */
.mini-search {
  display: flex;
  align-items: center;
  background-color: #F5F5F5;
  border-radius: 4vw;
  padding: 0 3vw;
  height: 8vw;
  width: 100%;
}

.mini-search i {
  font-size: 3.5vw;
  color: #999;
  margin-right: 2vw;
}

.mini-search input {
  border: none;
  background: transparent;
  font-size: 3.5vw;
  width: 100%;
  outline: none;
  color: #333;
}

/* 移除旧的搜索栏样式 */
.search-bar {
  display: none;
}

.vertical-text {
  font-size: 4.5vw;
  font-weight: 500;
  line-height: 5vw;
  text-align: center;
}

/* 城市选择器 - 完全重构 */
.city-selector-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: flex-start;
  z-index: 200; /* 确保在遮罩层之上 */
}

.city-selector {
  width: 90%;
  max-width: 500px;
  background-color: #FFF;
  border-radius: 4vw;
  margin-top: 20vw;
  box-shadow: 0 4vw 20vw rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  max-height: 80vh; /* 限制最大高度 */
  overflow-y: auto;
}

.city-selector-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 3vw 4vw;
  border-bottom: 1px solid #EAEAEA;
  background-color: #F8F8F8;
  position: sticky;
  top: 0;
  z-index: 10;
}

.city-selector-header span {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
}

.city-selector-header i {
  font-size: 5vw;
  color: #999;
  cursor: pointer;
  padding: 2vw;
}

.city-section {
  padding: 3vw 4vw;
}

.city-section h3 {
  font-size: 3.8vw;
  color: #666;
  margin-bottom: 3vw;
  font-weight: normal;
}

.city-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 2vw;
}

.city-item {
  width: calc(33.33% - 2vw);
  height: 10vw;
  background-color: #F5F5F5;
  border-radius: 1vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 3.5vw;
  color: #333;
  cursor: pointer;
}

.city-item:active {
  background-color: #FFE4CC;
  color: #FF6B01;
}

.province-item {
  margin-bottom: 4vw;
}

.province-name {
  font-size: 4vw;
  color: #333;
  margin-bottom: 2vw;
  font-weight: 500;
}

.city-list {
  display: flex;
  flex-wrap: wrap;
}

.city-name {
  font-size: 3.5vw;
  color: #666;
  padding: 1.5vw 3vw 1.5vw 0;
  cursor: pointer;
}

.city-name:active {
  color: #FF6B01;
}

/* 省市选择区域样式 */
.province-item .city-list .city-name {
  font-size: 3.5vw;
  color: #666;
  margin-right: 4vw;
  margin-bottom: 2vw;
  padding: 1vw 0;
  cursor: pointer; /* 添加指针样式 */
  text-align: center; /* 确保文字居中 */
}

.province-item .city-list .city-name:hover {
  color: #FF6B01;
}

/* 分类导航 */
.category-nav {
  width: 100%;
  height: 12vw;
  background-color: #FFF;
  display: flex;
  align-items: center;
  overflow-x: auto;
  white-space: nowrap;
  padding: 0 2vw;
  -webkit-overflow-scrolling: touch;
}

.category-nav::-webkit-scrollbar {
  display: none;
}

.category-item {
  height: 100%;
  padding: 0 4vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

.category-item span {
  font-size: 3.8vw;
  color: #333;
  position: relative;
}

.category-item.active span {
  color: #FF6B01;
  font-weight: bold;
}

.category-item.active span::after {
  content: "";
  position: absolute;
  bottom: -1.5vw;
  left: 0;
  width: 100%;
  height: 0.5vw;
  background-color: #FF6B01;
  border-radius: 0.5vw;
}

/* 自选体检和问卷推荐 */
.custom-check-section {
  width: 100%;
  background-color: #FFF;
  display: flex;
  justify-content: space-around;
  padding: 3vw 0;
  border-bottom: 1px solid #EAEAEA;
  margin-bottom: 2vw;
}

.custom-check-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

.custom-check-icon {
  width: 12vw;
  height: 12vw;
  background-color: #F8F0E5;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1vw;
}

.custom-check-icon i {
  font-size: 6vw;
  color: #FF6B01;
}

.custom-check-text {
  font-size: 3vw;
  color: #333;
}

/* 图标分类 */
.icon-categories {
  width: 100%;
  background-color: #FFF;
  display: flex;
  padding: 4vw 2vw;
}

.second-row {
  margin-top: 0.5vw;
}

.icon-category {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.icon-wrap {
  width: 12vw;
  height: 12vw;
  background-color: #F8F0E5;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon-wrap i {
  font-size: 6vw;
  color: #FF6B01;
}

.icon-category p {
  font-size: 3vw;
  margin-top: 2vw;
  color: #333;
}

/* 排序面板容器和遮罩 */
.sort-panel-container {
  position: relative;
  width: 100%;
  z-index: 999;
}

.sort-panel-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: transparent;
  z-index: 998;
}

/* 排序面板 */
.sort-panel {
  width: 100%;
  background-color: #FFF;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 999;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-top: 1px solid #EAEAEA;
}

/* 筛选面板 */
.filter-panel {
  width: 100%;
  background-color: #FFF;
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 100;
  padding: 4vw;
  max-height: 70vh;
  overflow-y: auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-top: 1px solid #EAEAEA;
}

/* 筛选排序栏 */
.filter-bar {
  width: 100%;
  height: 12vw;
  background-color: #FFF;
  display: flex;
  border-bottom: 1px solid #EAEAEA;
  margin-top: 2vw;
  position: relative;
  z-index: 101;
}

.filter-item {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 3.5vw;
  color: #333;
}

.filter-item i {
  margin-left: 1vw;
  font-size: 3.5vw;
}

/* 排序面板 */
.sort-option {
  height: 12vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 5vw;
  border-bottom: 1px solid #F2F2F2;
  font-size: 3.5vw;
}

.sort-option i {
  color: #FF6B01;
}

/* 筛选面板 */
.filter-panel h3 {
  font-size: 3.8vw;
  font-weight: normal;
  margin-bottom: 3vw;
  color: #333;
}

.filter-tags {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 3vw;
  gap: 2vw;
}

.filter-tag {
  display: inline-block;
  padding: 2vw 3vw;
  background-color: #F5F5F5;
  border-radius: 1.5vw;
  font-size: 3.5vw;
  color: #666;
  min-width: 16vw;
  text-align: center;
}

.filter-tag.active {
  background-color: #FFF0E6;
  color: #FF6B01;
  border: 1px solid #FF6B01;
}

.filter-buttons {
  display: flex;
  position: sticky;
  bottom: 0;
  background-color: #FFF;
  padding: 2vw 0;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
}

.reset-btn, .confirm-btn {
  flex: 1;
  height: 10vw;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5vw;
  font-size: 4vw;
  margin: 0 3vw;
}

.reset-btn {
  background-color: #F5F5F5;
  color: #333;
  border: 1px solid #EAEAEA;
}

.confirm-btn {
  background-color: #FF6B01;
  color: #FFF;
}

/* 产品列表 */
.product-list {
  width: 100%;
  padding: 2vw;
}

.product-card {
  width: 100%;
  background-color: #FFF;
  border-radius: 2vw;
  margin-bottom: 3vw;
  overflow: hidden;
  display: flex;
}

.product-image {
  width: 30%;
  position: relative;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-tag {
  position: absolute;
  top: 0;
  left: 0;
  background-color: #FF5722;
  color: #FFF;
  padding: 1vw 2vw;
  font-size: 3vw;
  border-bottom-right-radius: 2vw;
}

.product-info {
  width: 70%;
  padding: 3vw;
}

.product-title {
  display: flex;
  align-items: flex-start;
  margin-bottom: 2vw;
}

.subsidy-tag {
  display: inline-block;
  background-color: #FF4D4F;
  color: white;
  font-size: 2.8vw;
  padding: 0.5vw 1.5vw;
  border-radius: 0.8vw;
  margin-right: 2vw;
  margin-top: 0.5vw;
}

.product-info h3 {
  font-size: 3.8vw;
  font-weight: bold;
  color: #333;
  flex: 1;
}

.product-features {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 2vw;
}

.product-features span {
  font-size: 3vw;
  color: #999;
  margin-right: 2vw;
  margin-bottom: 1vw;
}

.product-features span:not(:last-child)::after {
  content: "|";
  margin-left: 2vw;
  color: #DEDEDE;
}

.check-items {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 2vw;
}

.check-item {
  display: flex;
  align-items: center;
  font-size: 2.8vw;
  color: #666;
  margin-right: 2vw;
  margin-bottom: 1vw;
}

.check-item i {
  font-size: 3.5vw;
  color: #FF6B01;
  margin-right: 0.5vw;
}

.more-items {
  font-size: 2.8vw;
  color: #FF6B01;
  margin-left: 1vw;
}

.hospital-container {
  margin-bottom: 2vw;
}

.product-hospital {
  display: inline-block;
  font-size: 3vw;
  color: #999;
  background-color: #F8F8F8;
  padding: 1vw 2.5vw;
  border-radius: 4vw;
}

.product-bottom {
  display: flex;
  align-items: center;
}

.price-container {
  display: flex;
  align-items: baseline;
  flex: 1.5;
}

.price {
  color: #FF6B01;
  font-size: 4.5vw;
  font-weight: bold;
  margin-right: 2vw;
}

.original-price {
  color: #999;
  font-size: 3vw;
  text-decoration: line-through;
}

.sales {
  font-size: 2.8vw;
  color: #999;
  flex: 1;
  text-align: right;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.sales span {
  margin-left: 0.5vw;
}

.hot-icon {
  color: #FF6B01;
  font-size: 3vw;
  margin-left: 1vw;
}

.add-btn {
  flex: 0.5;
  text-align: right;
}

.product-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 2vw;
}

.add-btn i {
  font-size: 6vw;
  color: #FF6B01;
}

.compare-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background-color: #FF6B01;
  border-radius: 5vw;
  padding: 1.5vw 3vw;
  font-size: 3.5vw;
  color: #fff;
  cursor: pointer;
  white-space: nowrap;
  font-weight: bold;
}

.compare-btn i {
  font-size: 3.8vw;
  margin-right: 1vw;
  color: #fff;
}

.compare-text {
  font-size: 3.5vw;
}

/* 购物车图标 */
.cart-icon {
  position: relative;
  margin-right: 2vw;
  cursor: pointer;
}

.cart-icon i {
  font-size: 4vw;
  color: #999;
}

.cart-badge {
  position: absolute;
  top: -0.5vw;
  right: -0.5vw;
  background-color: #FF6B01;
  color: #FFF;
  border-radius: 50%;
  width: 4vw;
  height: 4vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.5vw;
  font-weight: bold;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

/* 遮罩层 */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100; /* 确保遮罩层在排序面板和筛选面板之上 */
}

/* 适配底部导航 */
footer {
  z-index: 99;
}

/* 测试用样式，确保城市名称可见 */
.city-name {
  color: #000;
  display: block;
}

/* 全屏筛选页面 */
.filter-fullscreen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #FFF;
  z-index: 999;
  display: flex;
  flex-direction: column;
}

.filter-header {
  height: 12vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 4vw;
  border-bottom: 1px solid #EAEAEA;
  background-color: #FFF;
  position: sticky;
  top: 0;
  z-index: 10;
}

.filter-back {
  width: 10vw;
  height: 10vw;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.filter-back i {
  font-size: 5vw;
  color: #333;
}

.filter-title {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
}

.filter-placeholder {
  width: 10vw;
}

.filter-content {
  flex: 0 1 auto;
  overflow-y: auto;
  padding: 3vw;
  max-height: 55vh; /* 确保筛选内容不会占用太多空间 */
}

.filter-content h3 {
  font-size: 4vw;
  color: #333;
  margin: 2vw 0 2vw 0;
  font-weight: bold;
}

.filter-bottom {
  padding: 2vw 4vw;
  border-top: 1px solid #EAEAEA;
  background-color: #FFF;
  position: sticky;
  bottom: 0;
  z-index: 10;
}

/* 对比悬浮栏 */
.compare-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 92%; /* 减少宽度，防止按钮超出 */
  background-color: #FFF;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 2vw 2vw;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
  margin: 0 4%; /* 添加左右边距 */
  border-radius: 2vw 2vw 0 0; /* 添加顶部圆角 */
}

.compare-items {
  display: flex;
  align-items: center;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
  flex: 0.5; /* 减小比例 */
  max-width: 50%; /* 缩小左侧区域宽度 */
}

.compare-item {
  display: flex;
  align-items: center;
  margin-right: 2vw;
  background-color: #F5F5F5;
  border-radius: 2vw;
  padding: 0.5vw 1vw;
  font-size: 3vw;
  color: #333;
  max-width: 20vw; /* 限制单个对比项的宽度 */
}

.compare-item img {
  width: 6vw;
  height: 6vw;
  border-radius: 50%;
  margin-right: 1vw;
  object-fit: cover;
}

.compare-item-title {
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 12vw; /* 限制标题宽度 */
}

.remove-btn {
  font-size: 4vw;
  color: #FF6B01;
  cursor: pointer;
  padding: 0.5vw 1vw;
  border-radius: 50%;
  background-color: #FFF0E6;
}

.compare-item.empty {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 3vw;
}

.compare-actions {
  display: flex;
  align-items: center;
  justify-content: flex-end; /* 靠右对齐 */
  flex: 0.5; /* 增加按钮区域比例 */
  padding-right: 2vw; /* 确保右侧有足够空间 */
}

.compare-clear {
  color: #FF6B01;
  margin-right: 3vw;
  padding: 1vw 2vw;
  cursor: pointer;
  font-size: 3.5vw;
  white-space: nowrap;
}

.compare-btn {
  background-color: #FF6B01;
  color: #FFF;
  padding: 1vw 1.5vw; /* 进一步缩短内边距 */
  border-radius: 2vw;
  font-size: 3.8vw;
  font-weight: bold;
  cursor: pointer;
  white-space: nowrap;
  display: inline-block; /* 使按钮宽度适应内容 */
  width: min-content; /* 最小宽度适应内容 */
  text-align: center; /* 文字居中 */
}

.compare-btn.disabled {
  background-color: #DCDCDC;
  color: #888;
  cursor: not-allowed;
}

/* 提示框 */
.toast {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.7);
  color: #FFF;
  padding: 2vw 4vw;
  border-radius: 1vw;
  font-size: 3.5vw;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.5s ease-in-out;
}

.toast.show {
  opacity: 1;
}

.toast-content {
  text-align: center;
}

.toast-message {
  margin-bottom: 1vw;
}

.toast-actions {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

.toast-action-btn {
  padding: 0.8vw 2vw;
  border-radius: 0.8vw;
  font-size: 3.2vw;
  cursor: pointer;
}

.toast-action-btn.confirm {
  background-color: #FF6B01;
  color: #FFF;
}

/* 分页控制 */
.pagination-container {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 3vw 0;
  background-color: #FFF;
  position: sticky;
  bottom: 0;
  z-index: 99;
}

.pagination {
  display: flex;
  align-items: center;
  background-color: #F5F5F5;
  border-radius: 5vw;
  padding: 1vw 3vw;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.pagination-btn {
  width: 8vw;
  height: 8vw;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4vw;
  color: #333;
  cursor: pointer;
}

.pagination-btn.disabled {
  color: #DCDCDC;
  cursor: not-allowed;
}

.pagination-indicator {
  font-size: 3.5vw;
  color: #333;
  margin: 0 2vw;
}

/* 无数据提示 */
.no-data {
  width: 100%;
  height: 40vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #FFF;
  border-radius: 2vw;
  margin-bottom: 3vw;
  color: #999;
}

.no-data i {
  font-size: 10vw;
  margin-bottom: 2vw;
}

.no-data p {
  font-size: 4vw;
}

/* 套餐弹窗 */
.category-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.category-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.category-modal-container {
  width: 90%;
  max-width: 500px;
  background-color: #FFF;
  border-radius: 4vw;
  box-shadow: 0 4vw 20vw rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 1000;
  max-height: 80vh;
  overflow-y: auto;
}

.category-modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 3vw 4vw;
  border-bottom: 1px solid #EAEAEA;
  background-color: #F8F8F8;
  position: sticky;
  top: 0;
  z-index: 10;
}

.category-modal-header span {
  font-size: 4.5vw;
  font-weight: bold;
  color: #333;
}

.category-modal-header i {
  font-size: 5vw;
  color: #999;
  cursor: pointer;
  padding: 2vw;
}

.category-modal-content {
  flex: 1;
  overflow-y: auto;
  padding: 4vw;
}

.category-modal-content h3 {
  font-size: 4.5vw;
  color: #333;
  margin: 4vw 0 2vw 0;
  font-weight: bold;
}

.category-product-list {
  display: flex;
  flex-direction: column;
  padding: 2vw;
  gap: 3vw;
}

.category-product-item {
  background-color: #FFF;
  border-radius: 3vw;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 0 1vw 2vw rgba(0, 0, 0, 0.05);
  border: 1px solid #EAEAEA;
}

.category-product-image {
  width: 100%;
  height: 30vw;
}

.category-product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.category-product-info {
  padding: 3vw;
}

.category-product-title {
  font-size: 4vw;
  color: #333;
  font-weight: bold;
  margin-bottom: 2vw;
}

.category-product-features {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 1vw;
}

.category-product-features span {
  font-size: 3vw;
  color: #999;
  margin-right: 1vw;
  margin-bottom: 0.5vw;
}

.category-product-features span:not(:last-child)::after {
  content: "|";
  margin-left: 1vw;
  color: #DEDEDE;
}

.category-product-price {
  font-size: 3.2vw;
  color: #FF6B01;
  font-weight: bold;
}

.category-product-action {
  display: flex;
  justify-content: flex-end;
  padding: 2vw 3vw;
  border-top: 1px solid #F5F5F5;
  background-color: #FAFAFA;
}

.category-view-btn, .category-add-btn {
  padding: 1.5vw 3vw;
  border-radius: 1.5vw;
  font-size: 3.5vw;
  color: #FFF;
  margin-left: 2vw;
  text-align: center;
}

.category-view-btn {
  background-color: #FF9800;
}

.category-add-btn {
  background-color: #FF6B01;
}

.no-data-message {
  width: 100%;
  height: 40vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #FFF;
  border-radius: 2vw;
  margin-bottom: 3vw;
  color: #999;
}

.no-data-message i {
  font-size: 10vw;
  margin-bottom: 2vw;
}

.no-data-message p {
  font-size: 4vw;
}

/* 筛选结果样式 */
.filter-result-container {
  margin-top: 3vw;
  padding: 2vw 0;
  border-top: 1px solid #EAEAEA;
}

.filter-result-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2vw;
  padding: 0 1vw;
}

.filter-result-header h3 {
  font-size: 4vw;
  color: #333;
  font-weight: bold;
}

.result-count {
  color: #FF6B01;
}

.filter-result-view-all {
  font-size: 3.5vw;
  color: #999;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.filter-result-view-all i {
  margin-left: 1vw;
  font-size: 3.5vw;
}

.filter-result-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 2vw;
  max-height: 35vh; /* 限制高度避免筛选页面被挤压 */
  overflow-y: auto;
  padding: 0 1vw;
}

.filter-result-item {
  display: flex;
  flex-direction: column;
  background-color: #FFF;
  border-radius: 2vw;
  overflow: hidden;
  padding: 1.5vw;
  box-shadow: 0 0.5vw 1vw rgba(0, 0, 0, 0.05);
  cursor: pointer;
}

.filter-result-image {
  width: 100%;
  height: 20vw;
  margin-bottom: 1.5vw;
}

.filter-result-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 1vw;
}

.filter-result-info {
  display: flex;
  flex-direction: column;
}

.filter-result-title {
  font-size: 3.5vw;
  color: #333;
  font-weight: 500;
  margin-bottom: 1vw;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.filter-result-price {
  font-size: 4vw;
  color: #FF6B01;
  font-weight: bold;
}

.filter-result-more {
  text-align: center;
  margin-top: 2vw;
  padding: 2vw 0;
}

.filter-result-more-text {
  display: inline-block;
  padding: 2vw 5vw;
  background-color: #F8F8F8;
  color: #666;
  font-size: 3.5vw;
  border-radius: 5vw;
  cursor: pointer;
}

.filter-result-more-text:active {
  background-color: #F0F0F0;
}
</style> 