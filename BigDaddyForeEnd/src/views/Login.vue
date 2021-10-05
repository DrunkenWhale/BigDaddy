<template>
    <div style="float: right;">
        <router-link to="/login" style="color: #42b983;">Login</router-link> |
        <router-link to="/register" style="color:#2c3e50">Register</router-link>
    </div>
    <div class="login">
        <el-card>
            <span>登陆</span>
            <br><br><br>
            <el-form >
                <el-form-item label="邮箱">
                    <el-input v-model="mailbox"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="password" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @Click="Submit">
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
        name: "Login",
        data() {
            return {
                LoginURL: BaseURL.BaseURL + "/auth/login",
                headers: {},
                mailbox: null,
                password: null,
            }
        },

        methods: {
            Submit: function() {
                var formData = new FormData();
                formData.append("mailbox", this.mailbox);
                formData.append("password", this.password);
                axios.post(this.LoginURL, formData)
                    .then(res => {
                        var result = res.data;
                        if (result.message == 'Succeed') {
                            window.localStorage.setItem("token", result.token);
                            ElMessage({
                                message: '登陆成功',
                                center: true,
                            });
                            this.$router.push("/");
                        } else {
                            ElMessage({
                                message: '密码错误！',
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
            }
        },
    }
</script>


<style>
    .login {
        position: absolute;
        top: 30%;
        left: 30%;
        width: 40%;
    }
</style>