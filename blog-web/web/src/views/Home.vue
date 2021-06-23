<template>
  <div class="main">
      <div class="home">
          <div class="index">
              <div class="index-img">
                <el-carousel>
                    <el-carousel-item v-for="item in latest" :key="item">
                        <img :src="item.images" alt="">
                    </el-carousel-item>
                </el-carousel>
              </div>
              <div class="latest">
                  <div id="in1">
                      <img src="../assets/1622022576555.jpg" alt="">
                  </div>
                  <div id="in2">
                      <img src="../assets/1622020080663.jpg" alt="">
                  </div>
              </div>
          </div>
          <div class="blog-list">
              <div id="list-name">博客列表</div>
              <div class="list">
                  <div v-for="blog in data" class="blog" :key="blog" @click="link(blog)">
                    <img :src="blog.images" alt="">
                    <p class="title">{{blog.title}}</p>
                    <p class="dtail">{{blog.content}}</p>
                  </div>
              </div>
              
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
      <div class="sider">
        <div class="about">
            <div id="about-name">我的名片</div>
            
                <el-popover
                    placement="right"
                    :width="150"
                    trigger="click"
                    >
                    <template #reference>
                        <el-avatar src="https://autwind-1301936962.cos.ap-nanjing.myqcloud.com/blog/60131619158125016.jpg"/>
                    </template>
                    <img src="../assets/wx.png" alt="" width="150">
                </el-popover>

            <p>网名：autwind|痕迹</p>
            <p>职业：Java工程师</p>
            <p>现居：湖南长沙</p>
        </div>
        <div class="tips">
            <div id="tips-name">网站公告</div>
            <p class="tips-content">网站初次运行，遇见bug见谅，可以联系一下我解决</p>
        </div>
      </div>
      
  </div>
</template>

<script>
export default {
    created(){
        this.$http.get("blog").then(({ data }) => {
            this.data = data.data;
        });
        this.$http.get("admin/info").then(({ data }) => {
            this.count = data.data.blogCount;
        });
        this.$http.get("blog/latests").then(({ data }) => {
            this.latest = data.data;
        });
    },

    data() {
        return {
            data:[],
            current: 1,
            size: 9,
            count: 0,
            latest:[]
        };

    },

    methods:{
        link(blog){
            this.$router.push({ path: "/blog/" + blog.blogId })
        },
        currentChange(current) {
        this.current = current;
        this.listArticles();
        },
        listArticles() {
            this.$http.get(`/blog/?page=${this.current - 1}`, {}).then(({ data }) => {
            this.data = data.data;
        });
    }
        
    }
    
}
</script>

<style>

.main{
    max-width: 1160px;
	margin: auto;
    margin-top: 12px;
    margin-bottom: 12px;
    display:flex;
}
.home{
    width: 850px;
    margin: 0px 6px 0px 0px;
    border-radius: 5px;
    display: flexbox;

}

.index{
    
    display: flex;
}
.index-img{
    width: 70%;
    height: 300px;
    border-radius: 5px;
    
}
.el-carousel{
    height: 300px;
    border-radius: 5px !important;
}
.index-img img{
    width: 100%;
    height: 100%;
    border-radius: 5px;
}

.latest{

    width: 30%;
    margin-left: 6px;
    border-radius: 5px;
}
.latest div{
    background: darkcyan;
    height: 147px;
    border-radius: 5px;
}
.latest #in2{
    margin-top: 6px;
}
.latest img{
    width: 100%;
    height: 100%;
    border-radius: 5px;
}

.about{
    width: 300px;
    height: 250px;
    margin: 0px 0px 0px 6px;
    background:rgba(2, 41, 51, 0.781);
    border-radius: 5px;
    color:white;
}
.about:hover{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
.about #about-name{
    padding: 12px;
    border-bottom: solid 0.1px #eee;
}
.about p{
    padding: 0px 20px 0px 20px;
    margin: 4px 0px 0px 0px;
}
.about .el-avatar{
    margin: 20px 20px 0px 50px;
}
.tips{
    width: 300px;
    height: 250px;
    margin: 12px 0px 0px 6px;
    background:white;
    border-radius: 5px;
}
.tips .tips-content{
    margin: 20px;
    color: rgba(2, 41, 51, 0.781);
}
.tips:hover{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
.tips #tips-name{
    padding: 12px;
    border-bottom: solid 0.1px #eee;
}

.blog-list{
    margin: 12px 0px 12px 0px;
    padding: 6px 20px 6px 20px;
    
    background: white;
    border-radius: 5px;
    
}
.blog-list #list-name{
    border-bottom: solid 0.1px black;
}
.blog-list .list{
    margin: 12px;
    display: flex;
    flex-wrap: wrap;
}
.blog-list .blog{
    padding: 12px 12px 12px 12px;
    width: 30%;
    border-bottom: #eee 1px solid;
    border-left: #eee 1px solid;
    overflow: hidden;  
}
.blog-list .blog:hover{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
.blog-list .blog .dtail{ 
    font-size: 14px;
    color: #999;

    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;

}
.blog-list .blog .title{
    margin: 8px 0px 8px 0px;
} 

.blog-list img{
    width: 100%;
    height: 150px;
    border-radius: 5px;
}




@media only screen and (max-width: 800px) {
    .sider{
        display: none;
    }
    .index-img{
        width: 100%;
        height: 210px;
        display: none;
    }
    .index-img img{
        width: 100%;
        height: 210px;
    }
    .el-carousel{
        height: 210px;
    }
    .home{
        width: 100%;
    }
    .latest{
        display: none;
    }

    .blog-list .blog{
        width: 100%;
    }
    .blog-list img{
        width: 100%;
        height: 190px;
        border-radius: 5px;
    }
}
</style>