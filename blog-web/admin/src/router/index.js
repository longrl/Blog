import Vue from "vue";
import VueRouter from "vue-router";
import Layout from "../layout/index.vue";

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
};

const routes = [
  {
    path: "/login",
    name: "登录",
    hidden: true,
    component: () => import("../views/login/Login.vue")
  },
  {
    path: "/",
    component: Layout,
    children: [
      {
        path: "",
        name: "首页",
        icon: "iconfont el-icon-myshouye",
        component: () => import("../views/home/Home.vue"),
        meta: {
          requireAuth: true
        }
      }
    ]
  },
  {
    path: "/articles/*",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "",
        name: "修改文章",
        icon: "el-icon-edit",
        component: () => import("../views/article/Article.vue"),
        meta: {
          requireAuth: true
        }
      }
    ]
  },
  {
    path: "/article-submenu",
    name: "文章管理",
    icon: "iconfont el-icon-mywenzhang-copy",
    redirect: "/articles",
    component: Layout,
    children: [
      {
        path: "/articles",
        name: "添加文章",
        icon: "iconfont el-icon-myfabiaowenzhang",
        component: () => import("../views/article/Article.vue"),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/article-list",
        name: "文章列表",
        icon: "iconfont el-icon-mywenzhangliebiao",
        component: () => import("../views/article/ArticleList.vue"),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/tags",
        name: "标签列表",
        icon: "iconfont el-icon-myicontag",
        component: () => import("../views/tag/Tag.vue"),
        meta: {
          requireAuth: true
        }
      }
    ]
  },
  {
    path: "/message-submenu",
    name: "消息管理",
    icon: "iconfont el-icon-myxiaoxi",
    redirect: "/comments",
    component: Layout,
    children: [
      {
        path: "/comments",
        name: "评论列表",
        icon: "iconfont el-icon-mypinglunzu",
        component: () => import("../views/comment/Comment.vue"),
        meta: {
          requireAuth: true
        }
      }
    ]
  },
  {
    path: "/system-submenu",
    name: "系统管理",
    icon: "iconfont el-icon-myxitong",
    redirect: "/comments",
    component: Layout,
    children: [
      {
        path: "/about",
        name: "关于我",
        icon: "iconfont el-icon-myguanyuwo",
        component: () => import("../views/about/About.vue"),
        meta: {
          requireAuth: true
        }
      }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  routes
});
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (localStorage.getItem("Authorization")) {
      next();
    } else {
      console.log("未登录 请先登录");
      next({ path: "/login" });
    }
  } else {
    next();
  }
});
export default router;
