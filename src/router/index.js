import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import Appointment from '@/views/Appointment.vue'
import Hospital from '@/views/Hospital.vue'
import SelectData from '@/views/SelectData.vue'
import Confirmorder from '@/views/Confirmorder.vue'
import Appointmentlist from '@/views/Appointmentlist.vue'
import Appointmentsuccess from '@/views/Appointmentsuccess.vue'
import AppointmentCancel from '@/views/AppointmentCancel.vue'
import Setmeal from '@/views/Setmeal.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Report from '@/views/Report.vue'
import Reportlist from '@/views/Reportlist.vue'
import Personal from '@/views/Personal.vue'
import Deepseek from '@/views/Deepseek.vue'
import Mall from '@/views/Mall.vue'
import Cart from '@/views/Cart.vue'
import Compare from '@/views/Compare.vue'
import CustomCheck from '@/views/CustomCheck.vue'
import Questionnaire from '@/views/Questionnaire.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login' // 将根路径重定向到登录页面
    },
    {
      path: '/index',
      name: 'index',
      component: Index
    },
    {
      path: '/appointment',
      name: 'appointment',
      component: Appointment
    },
    {
      path: '/hospital',
      name: 'hospital',
      component: Hospital
    },
    {
      path: '/selectdata',
      name: 'selectdata',
      component: SelectData
    },
    {
      path: '/confirmorder',
      name: 'confirmorder',
      component: Confirmorder
    },
    {
      path: '/appointmentlist',
      name: 'appointmentlist',
      component: Appointmentlist
    },
    {
      path: '/appointmentsuccess',
      name: 'appointmentsuccess',
      component: Appointmentsuccess
    },
    {
      path: '/appointmentcancel',
      name: 'appointmentcancel',
      component: AppointmentCancel
    },
    {
      path: '/setmeal',
      name: 'setmeal',
      component: Setmeal
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/report',
      name: 'report',
      component: Report
    },
    {
      path: '/reportlist',
      name: 'reportlist',
      component: Reportlist
    },
    {
      path: '/personal',
      name: 'personal',
      component: Personal
    },
    {
      path: '/deepseek',
      name: 'deepseek',
      component: Deepseek
    },
    {
      path: '/mall',
      name: 'mall',
      component: Mall
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart
    },
    {
      path: '/compare',
      name: 'compare',
      component: Compare
    },
    {
      path: '/custom-check',
      name: 'custom-check',
      component: CustomCheck
    },
    {
      path: '/questionnaire',
      name: 'questionnaire',
      component: Questionnaire
    }
  ]
})

// 需要登录权限的路由
const authRequiredRoutes = [
  'mall', 'cart', 'compare', 'custom-check', 'appointmentlist', 
  'report', 'reportlist', 'personal', 'deepseek', 'appointmentsuccess'
];

// 全局前置守卫
router.beforeEach((to, from, next) => {
  // 检查用户是否已登录
  const token = localStorage.getItem('token');
  
  // 如果路由需要认证且用户未登录，则重定向到登录页
  if (authRequiredRoutes.includes(to.name) && !token) {
    console.log('检测到未登录状态，正在重定向到登录页');
    next({ 
      name: 'login',
      query: { redirect: to.fullPath } // 保存原本想要访问的页面路径
    });
  } else {
    next(); // 正常导航
  }
});

export default router