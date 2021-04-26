import Vue from 'vue'
import App from '@/App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import vuetify from './plugins/vuetify';
import VueResource from 'vue-resource';
import VueLocalStorage from 'vue-localstorage'
Vue.use(Vuetify)
Vue.use(VueResource)
Vue.use(VueLocalStorage)
Vue.config.productionTip = false


new Vue({
    vuetify,
    render: h => h(App)
}).$mount('#app')
