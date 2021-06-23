<template>
  <el-card class="main-card">
    <!-- 表格操作 -->
    <div class="operation-container">
    </div>
    <!-- 表格展示 -->
    <el-table border :data="commentList" @selection-change="selectionChange">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      
      <!-- 评论人昵称 -->
      <el-table-column
        prop="nickName"
        label="评论人"
        align="center"
        width="120"
      />
      
      <!-- 评论文章标题 -->
      <el-table-column prop="blogTitle" label="文章标题" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.blogTitle">
            {{ scope.row.blogTitle }}
          </span>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <!-- 被评论的内容 -->
      <el-table-column prop="parentContent" label="被评论的内容" align="center">
        <template slot-scope="scope">
          <span v-html="scope.row.parentContent" class="comment-content" />
        </template>
      </el-table-column>
      <!-- 评论内容 -->
      <el-table-column prop="commentContent" label="评论内容" align="center">
        <template slot-scope="scope">
          <span v-html="scope.row.commentContent" class="comment-content" />
        </template>
      </el-table-column>
      
      <!-- 评论时间 -->
      <el-table-column
        prop="createTime"
        label="评论时间"
        width="150"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:1rem"
            @onConfirm="deleteComments(scope.row.id)"
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
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />
  </el-card>
</template>

<script>
export default {
  created() {
    this.listComments();
  },
  data: function() {
    return {
      remove: false,
      updateIsDelete: false,
      options: [
        {
          value: 0,
          label: "正常"
        },
        {
          value: 1,
          label: "回收站"
        }
      ],
      commentList: [],
      commentIdList: [],
      keywords: null,
      isDelete: 0,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(commentList) {
      this.commentIdList = [];
      commentList.forEach(item => {
        this.commentIdList.push(item.id);
      });
    },
    sizeChange(size) {
      this.size = size;
      this.listComments();
    },
    currentChange(current) {
      this.current = current;
      this.listComments();
    },
    updateCommentStatus(id) {
      let param = new URLSearchParams();
      if (id != null) {
        param.append("idList", [id]);
      } else {
        param.append("idList", this.commentIdList);
      }
      param.append("isDelete", this.isDelete == 0 ? 1 : 0);
      this.axios.put("/api/admin/comments", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listComments();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.updateIsDelete = false;
      });
    },
    deleteComments(id) {
      this.$http.delete("/comment/"+id).then(({ data }) => {
        if (data.code == 1) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listComments();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.remove = false;
      });
    },
    listComments() {
      this.$http
        .get("/comment", {
          params: {
            current: this.current,
            size: this.size,
          }
        })
        .then(({ data }) => {
          this.commentList = data.data;
          this.count = data.data.length;
        });
    }
  },
  watch: {
    isDelete() {
      this.listComments();
    }
  }
};
</script>

<style scoped>
.comment-content {
  display: inline-block;
}
</style>