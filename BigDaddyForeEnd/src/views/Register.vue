<template>
    <div style="float: right;color:aquamarine">
        <router-link to="/login" style="color:#2c3e50 ;">Login</router-link> |
        <router-link to="/register" style="color: #42b983;">Register</router-link>
    </div>
    <div class="register">
        <el-card>
            <span>注册</span>
            <br><br><br>
            <el-form >
                <el-form-item label="邮箱">
                    <el-input v-model="mailbox"></el-input>
                </el-form-item>
                <el-form-item label="密码 ">
                    <el-input v-model="password" show-password></el-input>
                </el-form-item>
                <el-form-item style="width: 50%;float:left;"  label="验证码">
                    <el-input v-model="identify"></el-input>
                </el-form-item>
                <el-from-item style="float:right;margin-right:10%" >
                    <el-button type="primary" @Click="GetIdentifyCode">
                      获取验证码
                    </el-button>
                </el-from-item>
                <br><br><br><br><br><br>
                <el-form-item>
                    <el-button type="success" @Click="RegisterWithIdentifyCode">
                        Submit
                    </el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>


<script>
    import axios from 'axios'
    import BaseURL from '../conf/global.js'
    import {
        ElMessage
    } from 'element-plus'
    axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

    export default {
        name: "Register",
        data() {
            return {
                identifyCodeURL: BaseURL.BaseURL + "/auth/identify",
                RegisterURL: BaseURL.BaseURL + "/auth/register",
                headers: {},
                mailbox: null,
                password: null,
                identify: null
            }
        },

        methods: {
            GetIdentifyCode: function() {
                var formData = new FormData();
                formData.append("mailbox", this.mailbox);
                axios.post(this.identifyCodeURL, formData)
                    .then(res => {
                        var result = res.data;
                        if (result.message == 'Succeed') {
                            ElMessage({
                                message: '获取验证码成功',
                                center: true,
                            });
                        } else {
                            ElMessage({
                                message: '请求太频繁啦！爱护点这个小东西嘛~',
                                center: true,
                            });
                        }
                    })
                    .catch(err => {
                        ElMessage({
                            message: '奇怪的错误 要不检查一下网络?',
                            center: true,
                        });
                        console.log(err);
                    })
            },
            RegisterWithIdentifyCode: function() {
                var formData = new FormData();
                formData.append("mailbox", this.mailbox);
                formData.append("password", this.password);
                formData.append("identify_code", this.identify);
                axios.post(this.RegisterURL, formData)
                    .then(res => {
                        var result = res.data;
                        if (result.message == 'Succeed') {
                            ElMessage({
                                message: '注册成功',
                                center: true,
                            });
                            this.$router.push("/login");
                        } else if (result.message == 'UserExist') {
                            ElMessage({
                                message: '邮箱已注册!',
                                center: true,
                            });
                        } else {
                            ElMessage({
                                message: '校验码错误!',
                                center: true,
                            });
                        }
                    })
                    .catch(err => {
                        ElMessage({
                            message: '验证码错误!',
                            center: true,
                        });
                        console.log(err);
                    })
            }
        },
    }
</script>


<style>
    .register {
        position: absolute;
        top: 30%;
        left: 30%;
        width: 40%;
    }
</style>