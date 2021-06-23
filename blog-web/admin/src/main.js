import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/index.css";
import "./assets/css/iconfont.css";
import config from "./assets/js/config";

import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import moment from "moment";
import http from "./http";

Vue.prototype.config = config;
Vue.prototype.$http = http;
Vue.use(mavonEditor);

Vue.prototype.$infoUpdate = () => {
  http.get("/admin/info").then(res => {
    if (res && res.data.code == 1) {
      const info = res.data.data;
      store.commit("allData", info);
    }
  });
};

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.$moment = moment;
Vue.filter("date", function(value, formatStr = "YYYY-MM-DD") {
  return moment(value).format(formatStr);
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
