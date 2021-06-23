<template>
  <el-card class="main-card">
    <!-- 文章标题 -->
    <div class="article-title-container">
      <el-input
        v-model="article.title"
        size="medium"
        placeholder="输入文章标题"
      />
      <el-button
        type="danger"
        size="medium"
        @click="addOrEdit = true"
        style="margin-left:10px"
      >
        发布文章
      </el-button>
    </div>
    <!-- 文章内容 -->
    <mavon-editor
      ref="md"
      v-model="value"
      style="height:calc(100vh - 225px)"
      @change="change"
    />
    <!-- 添加文章对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="40%" top="10vh">
      <div class="dialog-title-container" slot="title">
        上传文章
      </div>
      <!-- 文章数据 -->
      <el-form label-width="80px" size="medium" :model="article">
        <el-form-item label="文章标签">
          <el-select
            v-model="article.tagId"
            placeholder="请选择标签"
          >
            <el-option
              v-for="item in tagList"
              :key="item.id"
              :label="item.tagName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="上传封面">
          <el-upload
            class="upload-cover"
            drag
            action=""
            multiple
            :on-success="uploadCover"
          >
            <i class="el-icon-upload" v-if="article.images == ''" />
            <div class="el-upload__text" v-if="article.images == ''">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <img
              v-else
              :src="article.images"
              width="360px"
              height="180px"
            />
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="danger" @click="saveOrUpdateArticle">
          发 表
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  created() {
    const path = this.$route.path;
    const arr = path.split("/");
    const articleId = arr[2];
    if (articleId) {
      this.$http.get("blog/" + articleId).then(({ data }) => {
        this.article = data.data;
        this.value = data.data.content;
      });
    }else{
      this.$http.get("blog/latest").then(({ data }) => {
        const blogId = data.data.blogId
        this.article.blogId = blogId + 100;
      });
    }
    this.$http.get("tag").then(({ data }) => {
      this.tagList = data.data;
    });
  },

  data: function() {
    return {
      tagList:[],
      value: "",
      addOrEdit: false,
      article: {
        id: null,
        tagId: null,
        content: "",
        html: "",
        time: "",
        title: "",
        blogId: 0,
        images: ""
      }
    };
  },
  methods: {
    change(value, render) {
      this.article.content = value;
      this.article.html = render; // 解析的html
    },
    uploadCover(response) {
      this.article.images = response.data.url;
    },
    saveOrUpdateArticle() {
      const path = this.$route.path;
      const arr = path.split("/");
      const articleId = arr[2];
      var url = "blog";
      if (this.article.title.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.article.content.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }
      if (this.article.tag == "") {
        this.$message.error("文章标签不能为空");
        return false;
      }
      if (!articleId) {
        this.article.time = this.$moment(new Date()).format("YYYY-MM-DD");
      }

      this.$http.post(url, this.article).then(({ data }) => {
        if (data.code == 1) {
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
        this.addOrEdit = false;
      });
    }
  }
};
</script>

<style scoped>
.article-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
}
.save-btn {
  margin-left: 0.75rem;
  background: #fff;
  color: #f56c6c;
}
</style>
