<template>
  <div class="tag">
    <h2>标签</h2>
    <el-tag type="info" v-for="tag in data" :key="tag">{{tag.tagName}}</el-tag>

  </div>
</template>

<script>
export default {
  created(){
    this.$http.get("tag").then(({ data }) => {
            this.data = data.data;
        });
  },
  data(){
    return{
      data:[]
    }
  }

}
</script>

<style>
.tag{
  max-width: 960px;
	margin: auto;
  padding: 20px;
  margin-top: 12px;
  margin-bottom: 12px;
  background: white;
}
.el-tag{
  margin: 6px;
}
</style>