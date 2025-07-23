<template>
            <footer>
                <ul>
                    <li @click="toindex">
                        <i class="fa fa-home"></i>
                        <p>云医院</p>
                    </li>
                    <li @click="tomall">
                        <i class="fa fa-opencart"></i>
                        <p>商城</p>
                    </li>
                    <li>
                        <i class="fa fa-compass"></i>
                        <p>发现</p>
                    </li>
                    <li @click="topersonal">
                        <i class="fa fa-user"></i>
                        <p>我</p>
                    </li>
                </ul>
            </footer>
</template>

<script>
import { useRouter } from "vue-router";
export default {
    setup() {
        const router = useRouter();
        function toindex() {
            router.push("/index")
        }
        function topersonal(){
            router.push("/personal") 
        }
        function tomall(){
            router.push("/mall") 
        }
        return {
            toindex,
            topersonal,
            tomall
        };
    }
}
</script>

<style scoped>

</style>
