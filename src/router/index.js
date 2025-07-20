import LoginVue from "@/views/Login.vue";
import { createWebHistory, createRouter } from "vue-router";
import index from "@/views/Index.vue";
import appointment from "@/views/Appointment.vue";
import register from "@/views/Register.vue";
import hospital from "@/views/Hospital.vue";
import setmeal from '@/views/Setmeal.vue';
import selectdataVue from "@/views/SelectData.vue";
import confirmorder from '@/views/Confirmorder.vue';
import personal from '@/views/Personal.vue';
import appointmentlist from '@/views/Appointmentlist.vue';
import appointmentsuccess from '@/views/Appointmentsuccess.vue';
import deepseek from "@/views/Deepseek.vue";
import cancel from "@/views/AppointmentCancel.vue";
import reportlist from "@/views/Reportlist.vue";
import report from "@/views/Report.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'login',
            component: LoginVue
        },
        {
            path: '/index',
            name: 'index',
            component: index
        },
        {
            path: '/appointment',
            name: 'appointment',
            component: appointment
        },
        {
            path: '/register',
            name: 'register',
            component: register
        },
        {
            path: '/hospital',
            name: 'hospital',
            component: hospital
        },
        {
            path: '/setmeal',
            name: 'setmeal',
            component: setmeal
        },
        {
            path: '/selectdata',
            name: 'selectdata',
            component: selectdataVue
        },
        {
            path: '/confirmorder',
            name: 'confirmorder',
            component: confirmorder
        },
        {
            path: '/personal',
            name: 'personal',
            component: personal
        },
        {
            path: '/appointmentlist',
            name: 'appointmentlist',
            component: appointmentlist
        },
        {
            path: '/appointmentsuccess',
            name: 'appointmentsuccess',
            component: appointmentsuccess
        },
        {
            path: '/deepseek',
            name: 'deepseek',
            component: deepseek
        },
        {
            path: '/cancel',
            name: 'cancel',
            component: cancel
        },
        {
            path: '/reportlist',
            name: 'reportlist',
            component: reportlist
        },
        {
            path: '/report',
            name:'report',
            component: report 
        }
    ]
})
export default router