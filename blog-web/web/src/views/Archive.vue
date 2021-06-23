<template>
  <div>
    <div class="block">
      <el-timeline v-for="blog in data" :key="blog">
        <el-timeline-item :timestamp="blog.createTime" placement="top" icon="el-icon-more" color="rgba(2, 41, 51, 0.781)">
          
          <h5>{{blog.title}}</h5>
          
        </el-timeline-item>
      </el-timeline>
      <div>
        <el-pagination
            class="pagination-container"
            background
            :current-page="current"
            @current-change="currentChange"
            :total="count"
            :page-size="size"
            layout="total, prev, pager, next"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created(){
        this.$http.get("blog/archives").then(({ data }) => {
            this.data = data.data;
            this.count = data.data.length
        });
    },
    data(){
      return{
        data:[],
        current: 1,
        size: 9,
        count: 0
      }
    },
    methods:{
      time(data){
        return new Date(data).getDay
      },
      currentChange(current) {
        this.current = current;
        this.listArticles();
        },
        listArticles() {
            this.$http.get(`/blog/archives/?page=${this.current - 1}`, {}).then(({ data }) => {
            this.data = data.data;
        });
        }
    }
}
</script>

<style>
.block{
  max-width: 960px;
	margin: auto;
  padding: 20px;
  margin-top: 12px;
  margin-bottom: 12px;
  background: white;
}

@media only screen and (max-width: 600px) {
  .block{
    max-width: 100%;
    padding: 14px;
  }
}
</style>