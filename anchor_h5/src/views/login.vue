<template>
  <div class="body">
    <div class="login-container" v-if="!regster">
      <h2>登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <input style="margin-top: 10px;margin-bottom: 10px;" type="text" id="username" placeholder="账号"
            v-model="username" required>

          <input type="password" id="password" placeholder="密码" v-model="password" required>

          <!-- 验证码部分去除label并对齐 -->
          <div style="display: flex;width: 95%;"> <input type="text" id="captcha" placeholder="请输入验证码" v-model="captcha"
              required style="height: 40px; line-height: 40px;margin-right: 5px;">
            <img :src="'/api/sys/getCode'" @click="refreshCaptcha" ref="captchaImage" class="captcha-image"
              style="height: 40px; width: 100px;opacity: 0.8;">
          </div>

          <button style="width: 95%;" type="submit">登录</button>
          <span style="margin-left: auto; margin-right: 30px; margin-top: 5px;color: #007bff;cursor: pointer"
            @click="handleRegister">没有账号?去注册</span>
        </div>
      </form>
    </div>
    <div class="register-container" v-if="regster">
      <h1 style="margin-bottom: 5px">注册</h1>
      <form @submit.prevent="Register">
        <div class="form-group">
          <input type="text" id="nickName" placeholder="昵称" v-model="nickName" required>

          <input type="text" id="username" placeholder="账号" v-model="username" required>

          <input type="password" id="password" placeholder="密码" v-model="password" required>

          <!-- 验证码部分 -->
          <div style="display: flex; width: 95%;">
            <input type="text" id="captcha" placeholder="请输入验证码" v-model="captcha" required
              style="height: 40px; line-height: 40px; margin-right: 5px;">
            <img :src="'/api/sys/getCode'" @click="refreshCaptcha" ref="captchaImage" class="captcha-image"
              style="height: 40px; width: 100px;opacity: 0.8">
          </div>

          <!-- 确认密码输入框 -->
          <input type="password" id="confirmPassword" placeholder="确认密码" v-model="confirmPassword" required>

          <button style="width: 95%;" type="submit">注册</button>
          <span style="margin-left: auto; margin-right: 30px; margin-top: 5px;color: #007bff;cursor: pointer"
            @click="handleRegister">已有账号?去登录</span>
        </div>
      </form>
    </div>
  </div>

</template>
  
<script> 
import { mapState} from 'vuex'
import userSerevice from '../api/user.js'
const { getUser, login, existUser, register } = userSerevice;
  export default {
    computed: {
     ...mapState(['count']),  // 把store中的count映射到当前组件的计算属性
  },
    name: 'LoginVue',
    data() {
      return {
        username: '',
        password: '',
        nickName: '',
        confirmPassword:'',
        isAuthenticated: false,
        captcha:'',
        regster: false
      };
    },
  mounted() {
    
    if (this.$store.state.token) {
      this.$router.push('/home')
    }
    console.log(this.$store.state.token);
  },
    
    methods: {  
      handleLogin() {
        login(this.username,this.password,this.captcha).then(
          res => { 
            if (res === 406) { 
              this.$message.error('验证码错误了哦0.o，再试试吧');
              this.refreshCaptcha()
              return;
            }
            console.log(res.body,'res')
            this.$store.commit('SET_TOKEN', res.body.token);
            this.getUser(this.username)
          }
        )
      },
      refreshCaptcha() {
        this.$refs.captchaImage.src = '/api/sys/getCode' + '?' + Date.now(); // 添加时间戳防止缓存
      // 同时清空已输入的验证码
      this.captcha = '';
    },
      getUser(username){
        getUser(username).then(
          res => {
            this.$store.commit('SET_USER', res);
            this.$router.push('/home')
        }
        )
      },
      handleRegister() {
        this.regster = !this.regster;
        this.refreshCaptcha();
      },
      Register() { 
        existUser(this.username).then(res => { 
          if (res > 0) {
            this.$message.error('账号已存在，再想一个账号吧~');
              this.refreshCaptcha();
            return;
          }
          if (this.password !== this.confirmPassword) {
            this.$message.error('两次输入的密码不一致，请重新输入~');
            this.refreshCaptcha();
            return
          }
          register(this.username, this.password, this.nickName, this.captcha).then(
            res => {
              if (res === 200) {
                this.$message.success('注册成功！');
              }
            }
          )
        })
      }
    },
  };
  </script>
 <style scoped>
 * {
   padding: 0;
   margin: 0;
   box-sizing: border-box;
 }

 .body {
   font-family: Arial, sans-serif;
   display: flex;
   justify-content: center;
   align-items: center;
   height: 100vh;
   margin: 0;
 }
input::placeholder {
  color: #000;
  font-style: italic;
}
 
.form-group { /* 创建一个新的样式类用于包裹账号、密码和验证码输入区域 */
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.input-with-label { /* 创建一个新的样式类用于账号、密码和验证码及其对应的label */
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.input-with-label > label {
  margin-right: 0.5rem;
  min-width: 80px; /* 根据实际需要调整label宽度 */
}

.input-with-label > input {
  flex-grow: 1; /* 让输入框自适应宽度 */
}

    h2 {
      font-size: 24px;
      color: #333;
    }

 /* .login-container {
   padding: 2rem;
   background-color: rgba(255, 255, 255, 0.9);
   border-radius: 10px;
   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
   color: #333;
   text-align: center;
   width: 25vw;
 } */
 .login-container {
   padding: 2rem;
   background-size: cover;
   background-position: center;
   background-color: rgba(#b5d3bf, 0.5);
   background-blend-mode: multiply;
   border-radius: 10px;
   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
   color: #333;
   text-align: center;
   width: 25vw;
 }
form > label,
form > input[type="text"],
form > input[type="password"] {
  display: inline-block;
  vertical-align: middle;
}

form > label {
  margin-right: 0.5rem; /* 可按需调整间距 */
}

 input[type="text"], input[type="password"] {
   width: 95%;
   padding: 0.75rem;
   margin-bottom: 1rem;
   border: 1px solid rgba(0, 0, 0, 0.2); /* 边框颜色变淡 */
   border-radius: 5px;
   outline: none;
   background-color: transparent; /* 去掉背景颜色，使输入框融入整体背景 */
   transition: border-color 0.3s ease-in-out;
 }

 input[type="text"]:focus, input[type="password"]:focus {
   border-color: #007BFF; /* 输入框获得焦点时显示淡蓝色边框 */
 }

 button {
   cursor: pointer;
   background-color: #007BFF;
   color: white;
   padding: 0.75rem 1.5rem;
   border: none;
   border-radius: 5px;
   text-transform: uppercase;
   font-weight: bold;
   transition: background-color 0.3s ease-in-out;
 }

 button:hover {
   background-color: #0056b3;
 }
 /* 先确保输入框和验证码图片父容器的align-items属性设置为center以实现垂直居中对齐 */
.captcha-container {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

/* 给输入框和验证码图片设置相同的高度 */
#captcha, .captcha-image {
  height: 40px;
  /* 如果需要，可以设置相同的行高以确保内容垂直居中 */
  line-height: 40px;
}

/* 对于验证码图片，还需确保宽度适中 */
.captcha-image {
  width: 100px; /* 根据实际情况调整 */
}
.register-container {
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: #333;
  text-align: center;
  width: 25vw;
}
</style>