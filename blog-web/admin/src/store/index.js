import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    tabList: [{ name: "首页", path: "/" }],
    collapse: false,
    count: null,
    avatar: null
  },
  mutations: {
    allData(state, info) {
      state.avatar = info.avatar;
      state.count = info.blogCount;
    },
    trigger(state) {
      state.collapse = !state.collapse;
    },
    saveTab(state, tab) {
      if (state.tabList.findIndex(item => item.path === tab.path) == -1) {
        state.tabList.push({ name: tab.name, path: tab.path });
      }
    },
    removeTab(state, tab) {
      var index = state.tabList.findIndex(item => item.name === tab.name);
      state.tabList.splice(index, 1);
    },
    resetTab(state) {
      state.tabList = [{ name: "首页", path: "/" }];
    }
  }
});
