import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Tag from '../views/Tag.vue'
import Archive from '../views/Archive.vue'
import Blog from '../views/Blog.vue'

const routes = [
  {
    path:'/',
    component: Home
  },
  {
    path:'/about',
    component: About
  },
  {
    path:'/tag',
    component: Tag
  },
  {
    path:'/archive',
    component: Archive
  },
  {
    path:'/blog/:id',
    component: Blog
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
