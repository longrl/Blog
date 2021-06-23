<template>
  <el-card class="main-card">
    <!-- 表格操作 -->
    <div class="operation-container">
      文章列表
    </div>
    <!-- 表格展示 -->
    <el-table border :data="articleList" @selection-change="selectionChange">
      <!-- 表格列 -->
      <el-table-column prop="title" label="标题" align="center" />

      <!-- 文章标签 -->
      <el-table-column prop="tag" label="标签" width="180" align="center">
        <template slot-scope="scope">
          <el-tag
            style="margin-right:0.2rem"
          >
            {{ scope.row.tag }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 文章浏览量 -->
      <el-table-column prop="readnum" label="浏览量" width="80" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.viewsCount">
            {{ scope.row.viewsCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章点赞量 -->
      <el-table-column prop="likenum" label="点赞量" width="80" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.likeCount">
            {{ scope.row.likeCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章发表时间 -->
      <el-table-column prop="time" label="发表时间" width="140" align="center">
      </el-table-column>
      <!-- 文章修改时间 -->

      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="editArticle(scope.row.blogId)"
          >
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @onConfirm="deleteArticles(scope.row.blogId)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10]"
      layout="total, sizes, prev, pager, next, jumper"
    />
  </el-card>
</template>

<script>
export default {
  created() {
    this.listArticles();
  },
  data: function() {
    return {
      updateIsDelete: false,
      remove: false,
      condition: '{"isDelete":0,"isDraft":0}',
      articleList: [],
      articleIdList: [],
      keywords: null,
      isDelete: 0,
      isDraft: 0,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(articleList) {
      this.articleIdList = [];
      articleList.forEach(item => {
        this.articleIdList.push(item.blogId);
      });
    },
    editArticle(id) {
      this.$router.push({ path: "/articles/" + id });
    },
    deleteArticles(id) {
      this.$http.delete("/blog/" + id).then(({ data }) => {
        if (data.code) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listArticles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.remove = false;
      });
    },
    sizeChange(size) {
      this.size = size;
      this.listArticles();
    },
    currentChange(current) {
      this.current = current;
      this.listArticles();
    },
    listArticles() {
      this.$http.get(`/blog/?page=${this.current - 1}`, {}).then(({ data }) => {
        this.articleList = data.data;
        this.count = this.$store.state.count;
      });
    }
  },
  watch: {
    condition() {
      const condition = JSON.parse(this.condition);
      this.isDelete = condition.isDelete;
      this.isDraft = condition.isDraft;
      this.listArticles();
    }
  }
};
</script>
