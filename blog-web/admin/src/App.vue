<template>
  <div id="app">
    <router-view :key="$route.fullPath" />
  </div>
</template>
<style lang="scss">
* {
  margin: 0;
  padding: 0;
  list-style: none;
  box-sizing: border-box;
}

#app {
  padding: 0 15px;
  border-radius: 10px;
  height: 100vh;
  width: 100vw;
  display: flex;
}
h2.tit {
  border-left: 2px solid #0084ff;
  padding-left: 16px;
  font-size: 18px;
  font-weight: 400;
  margin: 20px 0 30px;
  color: #0084ff;
}
.markdown-body * {
  list-style: initial;
}
@media screen and (max-width: 600px) {
  #app {
    padding: 0;
  }
  .el-message-box__wrapper .el-message-box {
    width: 90%;
  }
  .el-tooltip__popper {
    display: none !important;
  }
  h2.tit {
    font-size: 16px;
    margin: 10px 0 20px;
  }
}
</style>
