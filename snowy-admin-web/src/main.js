import {createApp} from 'vue'
import Antd from 'ant-design-vue'
import {createPinia} from 'pinia'

import './style/index.less'
import snowy from './snowy'
import i18n from './locales'
import router from './router'
import App from './App.vue'
import './tailwind.css'

import XEUtils from 'xe-utils'
import VXEUtils from 'vxe-utils'

const app = createApp(App)
app.use(VXEUtils, XEUtils, { mounts: ['cookie'] })
app.use(createPinia())
app.use(router)
app.use(Antd)
app.use(i18n)
app.use(snowy)

// 挂载app
app.mount('#app')
