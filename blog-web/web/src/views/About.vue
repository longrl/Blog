<template>
  <div class="about-box">
    <v-md-editor v-model="data.content" mode="preview"></v-md-editor>
  </div>
</template>

<script>
export default {
    created(){
      this.$http.get("webInfo").then(({ data }) => {
            this.data = data.data;
        });
    },

    data(){
      return{
        data:[],
      }
    }
}
</script>

<style>
.about-box{
  margin: auto;
  max-width:960px;
  background: white;
}
</style>