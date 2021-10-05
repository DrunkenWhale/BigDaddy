<template>
  <div class="TopDiv">
    <img class="BigDaddy" alt ="小宝贝助手！" src="../assets/logo.png" >
  </div>

  <div class="BottomDiv">
      <p style="margin:2.8%">大宝贝助手</p>
      <p style="font-size:medium;color:rgba(51, 187, 250, 0.747);margin-top:0">请选择要上传的文件</p>
        <el-upload ref="upload" :auto-upload="false" name="file" :headers="this.headers" :action="this.fileUploadURL" :on-success="this.FileUploadCallBack" multiply>
      <template #trigger>
        <button class="SelectButton">选择文件</button>
    </template>
<br>

<button class="UploadButton" @click="FileUpload">上传文件</button>

</el-upload>
</div>
</template>

<script>
    import BaseURL from '../conf/global.js'
    import {
        ElMessage
    } from 'element-plus'

    export default {
        name: 'Home',
        date() {
            return {
                headers: {},
                fileUploadURL: BaseURL.BaseURL + "/file/upload",
            }
        },
        created() {
            this.headers = {
                Authorization: "Bearer " + window.localStorage.getItem("token")
            };
            this.fileUploadURL = BaseURL.BaseURL + "/file/upload";
        },
        mounted() {},
        methods: {
            FileUpload: function() {

                if (window.localStorage.getItem("token") == null) {
                    this.$router.push("/login");
                } else {
                    this.$refs.upload.submit();
                }
            },
            FileUploadCallBack: function(res, file) {
                if (res.status == 0) {
                    ElMessage({
                        message: '啊呀，你的网络是不是有点问题,还是忘记登录了呢？',
                        center: true,
                    });
                } else {
                    ElMessage({
                        message: '上传成功!',
                        center: true,
                    });
                    console.log(file.name)
                    this.$notify({
                        title: '您的url到啦!',
                        dangerouslyUseHTMLString: true,
                        message: '文件 ' + file.name + '的url是 <strong>' + res.map.fileUrl + '</strong>',
                        duration: 0,
                    })
                }
            }
        },
    }
</script>

<style>
    .TopDiv {
        background-color: #303030;
        height: 50%;
    }
    
    .BottomDiv {
        align-items: center;
        height: 50%;
        display: flex;
        flex-direction: column;
        width: 100%;
    }
    
    .BigDaddy {
        margin-top: 10%;
    }
    
    .UploadButton {
        transition-duration: 0.7s;
        border-width: 1px;
        display: block;
        font-size: large;
        color: white;
        background-color: rgb(21, 235, 135);
        margin-top: 3vh;
        margin-bottom: 3vh;
        border-radius: 25px;
        height: 8vh;
        box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.281);
        width: 30vh;
    }
    
    .UploadButton:hover {
        background-color: rgb(22, 162, 228);
    }
    
    .UploadButton:active {
        transition: 0.1s;
        border-width: 3px;
        background-color: white;
        color: rgb(22, 162, 228);
    }
    
    .SelectButton {
        transition-duration: 0.7s;
        border-width: 1px;
        color: white;
        position: relative;
        font-size: large;
        background-color: rgb(22, 162, 228);
        margin-top: 3vh;
        border-radius: 25px;
        height: 8vh;
        box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.281);
        width: 30vh;
    }
    
    .SelectButton:hover {
        background-color: rgb(21, 235, 135);
    }
    
    .SelectButton:active {
        transition: 0.1s;
        border-width: 3px;
        background-color: white;
        color: aquamarine;
    }
</style>