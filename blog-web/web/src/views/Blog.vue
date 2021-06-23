<template>
  <div class="blog-d">
      <div class="blog-main">
          <div class="content-header">
            <img :src="data.images" alt="">
            <div class="b-title">{{data.title}}</div>
            <div class="el-icon-date date">{{data.time}}</div>
          </div>
          <div>
              <v-md-editor v-model="data.content" mode="preview"></v-md-editor>
          </div>
          <div><br></div>
          <div class="comment">
              <div class="input-box" id="test">
                  <input type="text" placeholder="Name" v-model="reply.nickname">
                  <input type="text" placeholder="Email" v-model="reply.email">
              </div>
              <div v-if="reply.parentId >0">
                  <textarea class="re-text" v-model="name"></textarea>
              </div>
              
              <div>
                  <textarea class="textarea" placeholder="What do you want to say..." v-model="reply.content">@</textarea>
                  <button type="button" @click="submit">SUBMIT</button><span id="notice">{{notice}}</span>
              </div>
              
  
            <template v-if="comment.length > 0" >
                <div class="comment-box"><span>Comment List</span><span>({{comment.length}})</span></div>
                <div class="comment-list">
                    <div class="box-card" v-for="i in comment" :key="i">
                        <div class="comment-header">
                            <span id="nickname">{{i.nickname}} <span v-if="i.nickname==flag" id="name-tag">小白</span>: </span><span id="re" @click="_comment(i.id,i.nickname)">reply</span><span id="comment-time">{{i.time}}</span>
                        </div>
                        <div v-if="i.parentContent != null" class="replay">
                            <p>{{i.pnickname}}: {{i.parentContent}}</p>
                        </div>
                        <div>
                            <p id="comment-content">{{i.content}}</p>
                        </div>
                    </div>    
                </div>
            </template>

          </div>
      </div>
      <div class="blog-sider">
        <div class="b-t">
            <div>
                <div class="b-t-name"><i class="el-icon-monitor"></i> 最近更新</div>
                <div class="latests" v-for="(i,index) in latest" :key="i" @click="link(i.blogId)">{{index+1}}.{{i.title}}</div>
            </div>
        </div>
        <div class="b-t">
            <div>
                <h4 class="b-t-name">标签相关</h4>
                <div class="latests" v-for="(i,index) in tags" :key="i" @click="link(i.blogId)">{{index+1}}.{{i.title}}</div>
            </div>
        </div>
      </div>
  </div>

</template>

<script>
export default {
    created(){
        const id = this.$route.params.id
        this.reply.blogId=id
        this.$http.get("blog/"+id).then(({ data }) => {
            this.data = data.data;
            this.$http.get("tag/"+ data.data.tagId).then(({ data }) => {
                this.tags = data.data
        });
        });
        this.$http.get("blog/latests").then(({ data }) => {
            this.latest = data.data;
        });
        this.$http.get("comment/"+id).then(({ data }) => {
            this.comment = data.data;
        });

    },

    data() {
        return {
            data:'',
            latest:[],
            tags:[],
            comment:[],
            reply:{
                id:null,
                blogId:null,
                parentId:-1,
                content:'',
                email:'',
                nickname:''
            },
            name:null,
            notice:null,
            flag:'autwind'
        };
    },

    methods:{
        link(id){
            this.$router.push({ path: "/blog/" + id })
        },
        _comment(parentId,name){
            this.reply.parentId = parentId
            this.name = '@'+name
            document.getElementById('test').scrollIntoView()
        },
        submit(){
            if(this.reply.nickname==''){
                this.notice='你的名字是第一印象哦～～'
                return false
            }
            if(this.reply.email==''){
                this.notice='请留下你的邮箱哦～～'
                console.log(this.reply)
                return false
            }
            if(this.reply.email!=''){
                var reg = /^\w+@+[0-9a-zA-Z]+\.(com|com.cn|edu|hk|cn|net)$/
                if(!reg.test(this.reply.email)){
                    this.notice='请留一个格式正确的邮箱哦～～'
                    return false
                }
            }
            if(this.reply.content==''){
                this.notice='想说点啥呢？？？？'
                return false
            }
            this.$http.post("comment",this.reply).then(({ data }) => {
                if(data.code==1){
                    this.$notify.success({
                        title: "成功",
                        message: data.message
                    });
                    this.comment.push(this.reply)
                    this.notice=''
                }else{
                    this.$notify.error({
                        title: "失败",
                        message: data.message
                    });
                }
                this.latest = data.data;
                
            })
            
            
        }
        
    },
    watch: {
	'$route' (to, from) {
    // 路由发生变化页面刷新
	this.$router.go(0);
		}
},
}
</script>

<style>
.blog-d{
    max-width: 1060px;
    height: 100%;
	margin: auto;
    margin-top: 12px;
    margin-bottom: 12px;
    display: flex;
    position: relative;
}
.blog-main{
    width: 700pX;
    background: white;
    border-radius: 2px;
    padding: 20px;
}

.content-header img{
    width: 100%;
    height: 80%;
    border-radius: 2px;
}
.content-header {
    padding: 10px 35px 10px 35px;
    height: 400px;
}
.blog-sider{
    position: -webkit-sticky;
    position: sticky;
    top: 15px;
    bottom: 300px;
    width: 300px;
    height: 100%;
}

.blog-sider .b-t{
    
    width: 300px;
    height: 250px;
    padding: 20px;
    margin: 0px 0px 10px 25px;
    background:white;
    border-radius: 5px;
}
.blog-sider .b-t .latests{
    margin: 10px 0;
    padding: 3px 0px 0px 10px;
    border-bottom: #eee 1px solid;
    color: rgb(32, 43, 59);
}
.blog-sider .b-t .latests:hover{
    background: rgba(161, 189, 186, 0.274);
}

.blog-sider .b-t:hover{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)

}

.b-t-name{
    margin: 0px 0px 10px 0px;
    font-size: 18px;
    color: #000;
}
.b-title{
    font-weight: bold;
    font-size: 22px;
    margin: 20px 0px 0px 0px;
}
.date{
    margin: 1px 0px 20px 10px;
    font-size: 12px;
    color: slategrey;
}

@media only screen and (max-width: 800px) {
    .blog-main{
        width: 100%;
        padding: 10px 0px 10px 0px;
    }
    .content{
        margin: auto;
        margin-top: 0px;
    }
    .blog-d{
        width: 100%;
        height: 100%;
        margin: auto;
        margin-top: 0px;
    }
    .content-header{
        width: 100%;
        height: 100px;
        padding: 20px 0px 40px 0px;
    }
    .content-header img{
        display: none;
    }
    .blog-sider{
        display: none;
    }
    .b-title{
        margin: 0px 0px 10px 4px;
    }
}

.comment{
    border:1px solid #eee;
    margin-bottom:60px;
    border-radius:6px;
    padding:15px 12px;
    transition: all .3s;

}
.input-box{
    display: flex;
}
.input-box input{
    width:50%;
    height:38px;
    font-size:14px;
    padding-left:10px;
    margin-right:12px;
    -webkit-transition:all .3s;
    transition:all .3s;
    border:none;
    border-bottom:1px dashed #f0f0f0;
    outline:none;
}
.comment .textarea{
    width:95%;
    height:200px;
    margin:10px 0;
    color: #333;
    border:1px dashed #eee;
    -webkit-transition:all .3s;
    transition:all .3s;
    border-radius:4px;
    font-size:14px;
    padding:10px;
    outline:none;
    resize:none;
    background: url('../assets/plbj.png') no-repeat bottom right;

}
.comment-box{
    font-size: inherit;
    margin: 20px 0;
    
}
.comment .re-text{
    width:95%;
    height:20px;
    margin:10px 0;
    color: #333;
    border:1px dashed #eee;
    -webkit-transition:all .3s;
    transition:all .3s;
    border-radius:4px;
    font-size:14px;
    padding:10px;
    outline:none;
    resize:none;

}
.comment #notice{
    font-size: 12px;
    color: red;
}
.comment button{
    height:34px;
    line-height:36px;
    width:100px;
    font-size:14px;
    color:#5f5f5f;
    border-radius:6px;
    background:#eaeaea;
    cursor:pointer;
    outline:none;
    border:none;
    margin-right: 12px;
    transition:all .3s;
}
.comment button:hover{
    color: rgb(17, 144, 218);
    background: var(--colorActive);
}

.comment-list{
    padding: 20px;
    font-size: inherit;
    font-weight: inherit;
}

.comment-list .box-card{
    padding: 5px 0px 5px 0px;
    border-bottom: #eee 1px solid;

}
.comment-list .box-card .replay{
    margin: 15px;
    border: #eee 1px solid;
    padding: 8px;
}
.comment-list .box-card .replay p{
    margin: 2px;
}
.comment-list #comment-content{
    padding: 0px 0px 0px 15px;
}
.comment-list .comment-header{
    position: relative;
    color: lightslategrey;
}
.comment-header #name-tag{
    position: relative;
    font-size: 8px;
    color: aliceblue;
    background: darksalmon;
    border-radius: 5px;
    bottom: 2px;
}
.comment-list #comment-time{
    position:absolute;
    right: 2px;
    font-size: 12px;
}
.comment-list #re{
    display: none;
}

.comment-list .box-card:hover #re{
    display: inline;
    position:absolute;
    right: 100px;
    font-size: 12px;
    
}
.comment-list #re:hover{
    color: red;
    border-bottom: dashed rgb(255, 0, 0) 1px;
}
</style>
