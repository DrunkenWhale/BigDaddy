# 工程简介

## API
无特殊说明均使用表单提交(JSON再见)
### 注册

method : POST  

    url: http:81.68.100.77:7777/api/auth/register
    
    params:  
        mailbox : String  = 用户邮箱  
        password : String = 用户密码  
        identify_code:Int = 用户验证码  
  
return:  
    

### 获取验证码

method : POST

    url: http:81.68.100.77:7777/api/auth/identify


    params:  
        mailbox : String  = 用户邮箱   

return:

### 登陆

method : POST

    url: http:81.68.100.77:7777/api/auth/login  
    
    params:  
        mailbox : String  = 用户邮箱  
        password : String = 用户密码

return:

### 文件上传

method : POST

    url: http:81.68.100.77:7777/api/file/upload  
    
    headers:
        Authorization : Bearer + token
      
    params:  
        file : File

return:

### 文件下载

method : GET

    url: http:81.68.100.77:7777/api/file/download/{file}  
    
    params:  
        file: 文件名

return:  
    

