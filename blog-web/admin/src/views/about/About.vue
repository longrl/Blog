<template>
  <el-card class="main-card">
    <mavon-editor
      v-model="about.content"
      style="height:calc(100vh - 215px)"
      @change="change"
    />
    <el-button
      type="danger"
      size="medium"
      class="edit-btn"
      @click="updateAbout"
    >
      修改
    </el-button>
  </el-card>
</template>

<script>
export default {
  created() {
    this.getAbout();
  },
  data: function() {
    return {
      about: {
        id: 1,
        content: "",
        html: ""
      }
    };
  },

  methods: {
    change(value, render) {
      this.about.content = value;
      this.about.html = render; // 解析的html
    },
    getAbout() {
      this.$http.get("about").then(({ data }) => {
        this.about = data.body;
      });
    },
    updateAbout() {
      this.$http.post("about", this.about).then(({ data }) => {
        if (data.status == 1) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.edit-btn {
  float: right;
  margin: 1rem 0;
}
</style>
