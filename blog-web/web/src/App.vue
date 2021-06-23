<template>
  <div class="container">
    <el-backtop></el-backtop>
    <div class="header">
      <div class="header-nav">
        <router-link to="/" class="logo">Autwind</router-link>
        <router-link to="/" class="nav el-icon-house" >首页</router-link>
        <router-link to="/tag" class="nav el-icon-price-tag">标签</router-link>
        <router-link to="/archive" class="nav el-icon-notebook-2">归档</router-link>
        <router-link to="/about" class="nav el-icon-male">关于</router-link>

        <span class="el-icon-s-fold" id="button" @click="drawer = true" style="font-size: 28px"></span>

      </div>
      <el-drawer
          title="导航"
          v-model="drawer"
          :with-header="false"
          direction="ttb"
          size="20%">
          
          <router-link to="/" class="el-icon-house" @click="drawer = false">首页</router-link>
          <router-link to="/tag" class="el-icon-price-tag" @click="drawer = false">标签</router-link>
          <router-link to="/archive" class="el-icon-notebook-2" @click="drawer = false">归档</router-link>
          <router-link to="/about" class="el-icon-male" @click="drawer = false">关于</router-link>
          
        </el-drawer>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
    <div class="fotter">@codeBy henji</div>
    
  </div>
</template>

<script>
export default {
  data() {
      return {
        drawer: false,
      };
    }
}
</script>

<style>
body{
  margin: 0px;
  background: rgb(233, 234, 237);
  font-family:Arial, Helvetica, sans-serif; 

}

.container{
  background: rgba(248, 250, 243, 0.651);
}

.header{
  background-color: rgba(1, 46, 43, 0.788);
	margin: auto;
  font-size: 14px;
  color: aliceblue;
  padding: 10px;
}

.header a{
  text-decoration: none;
}
.el-drawer{
  background: rgb(2, 76, 82) !important;
  
}
.el-drawer a{
  color: rgb(255, 255, 255);
  font-size: 16px;
  padding: 0px 3px 0px 3px;
  margin: 40px 0px 9px 5px;
}

.header .logo{
  font-size: 24px;
  color:white;
  font-weight:inherit;
  margin: 0px 80px 0px 0px;
}
.header #button{
  display: none;
}
.header .nav{
  line-height: 40px;
  margin: 0px 12px 0px 12px;
  color: white;
}
.header .nav:hover{
  color: rgb(0, 0, 0);
}
@media only screen and (max-width: 600px) {

  .header{
    background-color: rgba(1, 46, 43, 0.788);
    margin: auto;
    font-size: 16px;
    color: aliceblue;
    text-align: center;
  }
  .header .logo {
    font-size: 24px;
    color: white;
    font-weight: inherit;
    margin: 0px 0px 0px 0px;
}
  .header #button{
    line-height: 40px;
    display: inline;
    float: right;
    margin: 0px 10px 0px 2px;
  }
  .header .nav{
    display: none;
  }

}

.header-nav{
  max-width: 1060px;
	margin: auto;
}


.content{
  margin: auto;
  margin-top: 12px;
  margin-bottom: 12px;
  
}

.fotter{
  line-height: 100px;
  text-align: center;
  margin-top: 4px;
  height: 150px;
  background: slategrey;
}

</style>