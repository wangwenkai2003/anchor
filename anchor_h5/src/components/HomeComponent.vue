<template>
    <div class="body">
        <div id="loading-box">
            <div class="loading-left-bg"></div>
            <div class="loading-right-bg"></div>
            <div class="spinner-box">
                <div class="loader">
                    <div class="inner one"></div>
                    <div class="inner two"></div>
                    <div class="inner three"></div>
                    <div class="inner four"></div>
                </div>
                <div class="loading-word">
                    <p class="loading-title" id="loading-title">Anchorの主页</p>
                    <span id="loading-text">加载中</span>
                </div>
            </div>
        </div>
        <!-- 鼠标指针
    <div id="g-pointer-1"></div>
    <div id="g-pointer-2"></div> -->
        <!-- 主体内容 -->
        <!-- 遮罩层 -->
        <div v-if="showModal" class="modal-overlay" :style="{ backgroundColor: menuBackgroundColor }"
            @click="closeModal"></div>

        <!-- 模态框 -->
        <div v-if="showModal" class="modal" :style="{ backgroundColor: menuBackgroundColor }">
            <div class="modal-content poetry-form-container" ref="formContainer">
                <span class="close" @click="closeModal">&times;</span>
                <form @submit.prevent="submitPoetry" class="poetry-form">
                    <div class="form-group">
                        <label :style="{ color: menuTextColor }" for="poetry-line">诗句:</label>
                        <input :style="{ color: menuTextColor }" type="text" id="poetry-line" v-model="verse"
                            class="input-field">
                    </div>

                    <div class="form-group">
                        <label :style="{ color: menuTextColor }" for="author">作者:</label>
                        <input :style="{ color: menuTextColor }" type="text" id="author" v-model="author"
                            class="input-field">
                    </div>

                    <div class="form-group">
                        <label :style="{ color: menuTextColor }" for="poem-title">诗名:</label>
                        <input :style="{ color: menuTextColor }" type="text" id="poem-title" v-model="poemTitle"
                            class="input-field">
                    </div>

                    <button style="margin-left: auto;margin-right: auto"
                        :style="{ backgroundColor: menuTextColor, color: menuActiveTextColor }" type="submit"
                        class="submit-button">提交</button>
                </form>
            </div>
        </div>
        <main id="main" class="main" :style="{ backgroundImage: `url(${currentImagePath})` }">

            <!--基本信息-->
            <div class="main-left" style="width: 45%;">
                <!--Logo-->
                <div class="main-img">
                    <img id="logo-img" src="@/assets/yumaoqiu.jpg" alt="img">
                    <div style="display: flex;flex-direction: column; justify-content: center;">
                        <div>
                            <span class="img-title-big" id="logo-text-1">WangWK</span>
                            <span class="img-text" id="logo-text-2">.top</span>
                        </div>
                        <div style="margin-left: 30px">
                            <span class="signature">
                                Love can offset the long years
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-right" style="width: 55%; margin-top: 20px;">
                <!-- 功能区 -->
                <div style="width: 100%;display: flex; ">
                    <div class="col hitokotos" style="width: 50%;">

                        <!--一言-->
                        <div class="hitokoto cards" id="hitokoto">
                            <!--切换音乐-->
                            <div class="open-music fixed-top" id="open-music">
                                <i class="fa-solid fa-compact-disc"></i>
                                <span>&nbsp;打开音乐播放器</span>
                            </div>

                            <!--切换音乐结束-->
                            <div class="hitokoto-all" @mouseover="showAddPoetryOption = true"
                                @mouseleave="showAddPoetryOption = false" @click="getPoem()">
                                <div class="hitokoto-text"><span id="hitokoto_text">{{ hitokoto }}</span>
                                </div>
                                <div class="hitokoto-from">-「&nbsp;<span id="from_text">{{ from }}</span>&nbsp;」
                                </div>
                                <div v-if="showAddPoetryOption" class="add-poetry-option-container">
                                    <div class="add-poetry-option" @click.prevent.stop="addPoetry()">
                                        添加诗词到库
                                    </div>
                                </div>


                            </div>
                        </div>
                        <!--音乐-->
                        <div class="music" id="music">
                            <div class="music-all">
                                <div class="music-button">
                                    <div id="music-open">音乐列表</div>
                                    <div id="music-close">回到一言</div>
                                </div>
                                <div class="music-control">
                                    <i class="fa-solid fa-backward-step" id="last"></i>
                                    <div id="play">
                                        <i class="fa-solid fa-play"></i>
                                    </div>
                                    <i class="fa-solid fa-forward-step" id="next"></i>
                                </div>
                                <div class="music-menu">
                                    <div class="music-text">
                                        <span id="music-name">未播放音乐</span>
                                    </div>
                                    <div class="music-volume" style="display: none;">
                                        <div id="volume-ico">
                                            <i class="fa-solid fa-volume-low"></i>
                                        </div>
                                        <input type="range" min="0" max="1" step="0.01" id="volume">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col times" style="width: 45%;">

                        <!--时间-->
                        <div class="time cards" id="upWeather">
                            <div class="timeshow" id="time">
                                {{ weatherDate.year }}&nbsp;年&nbsp;{{ weatherDate.month }}&nbsp;月&nbsp;{{
                                weatherDate.day
                                }}&nbsp;日&nbsp;<span class="weekday">星期{{ weatherDate.weekday }}</span><br><span
                                    class="time-text">{{ weatherDate.formattedTime }}</span>
                            </div>
                            <div class="weather">
                                <span id="city_text">{{ city }}</span>&nbsp;
                                <span id="wea_text">{{ weather.text }}</span>&nbsp;
                                <span id="tem_text">{{ weather.temp }}°C</span>
                                <span id="win_text " style="margin-left: 5px;">{{ weather.windDir }}</span>
                                <span id="win_speed">{{ weather.windScale }}级</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="message" style="width: 97.5%; margin-top: 20px;"> <meting-js server="netease"
                        type="playlist" id="471074269" theme="#ada6a8" autoplay="false" loop="all" order="list"
                        preload="auto" list-folded="true" list-max-height="300px" lrc-type=""></meting-js></div>
                <div style="width: 100%;">
                    <div style="display: flex;margin-top: 20px;margin-bottom: 20px;">
                        <img src="@/static/img/icon/lianjie.png" style="width: 20px;height: 20px;" alt="">
                        <span style="color: #e7eced;">网站导航</span>
                    </div>
                    <div style="width: 100%;display: flex; flex-wrap: wrap;">
                        <div class="Web_page_item" @click="gotoUrl('bilibili')">
                            <img class="page_img" src="@/static/img/icon/bilibili.png" alt="">
                            <div class="page_item">bilibili</div>
                        </div>
                        <div class="Web_page_item" @click="gotoUrl('qianwen')">
                            <img class="page_img" src="@/static/img/icon/qianwen.png" alt="">
                            <div class="page_item">千问</div>
                        </div>
                        <div class="Web_page_item" @click="gotoUrl('gpt')">
                            <img class="page_img" src="@/static/img/icon/gpt.png" alt="">
                            <div class="page_item">GPT</div>
                        </div>
                        <div class="Web_page_item" @click="gotoUrl('github')">
                            <img class="page_img" src="@/static/img/icon/github(2).png" alt="">
                            <div class="page_item">GitHub</div>
                        </div>
                        <div class="Web_page_item" @click="gotoUrl('cainiao')">
                            <img class="page_img" src="@/static/img/icon/cailiao.png" alt="">
                            <div class="page_item">菜鸟</div>
                        </div>
                        <div class="Web_page_item" @click="gotoUrl('biji')">
                            <img class="page_img" src="@/static/img/icon/sparrow.png" alt="">
                            <div class="page_item">语雀</div>
                        </div>
                    </div>
                </div>
            </div>


        </main>
    </div>
</template>

<script>
import poemService from '../api/poem.js'
export default {
    name: 'App',

    components: {
    },
    data() {
        return {
            backgroundImage: `url(${this.currentImagePath})`,
            currentImagePath: require('@/static/img/background1.webp'),
            imagePaths: [
                require('@/static/img/background1.webp'),
                require('@/static/img/bgc2.webp'),
                require('@/static/img/bgc3.webp'),
],
            city: '',
            weather: {},
            windDirection: '',
            windScale: '',
            weatherDate: {
                year: null,
                month: null,
                day: null,
                hours: null,
                minutes: null,
                seconds: null,
                weekday: null,
                formattedTime: null
            },
            verse: '',
            author: '',
            poemTitle: '',
            hitokoto: '人生是旷野，不是轨道，你有权决定一种生活方式',
            from: '无名',
            showAddPoetryOption: false,
            showModal: false,
            menuColorMap: {
                0: {
                    backgroundColor: 'rgba(117, 133, 189, 0.5)',
                    textColor: 'rgba(163, 184, 227)',
                    activeTextColor: 'rgba(73, 96, 141)'
                },
                2: {
                    backgroundColor: 'rgba(89, 138, 154, 0.5)',
                    textColor: 'rgba(223, 255, 255)',
                    activeTextColor: 'rgba(78, 108, 135)'
                },
                1: {
                    backgroundColor: 'rgba(98, 137, 187, 0.5)',
                    textColor: 'rgba(88, 128, 177)',
                    activeTextColor: 'rgba(48, 92, 143)'
                }
                // ... 添加其他索引对应的颜色
            },
            bgcIndex: '' , 
        };
    },
    computed: {
        menuBackgroundColor() {
            return this.menuColorMap[this.bgcIndex]?.backgroundColor || '#7585BD';
        },
        menuTextColor() {
            return this.menuColorMap[this.bgcIndex]?.textColor || '#A3B8E3';
        },
        menuActiveTextColor() {
            return this.menuColorMap[this.bgcIndex]?.activeTextColor || '#49608D';
        },
    },
    mounted() {
        const loadingBoxDom = document.getElementById('loading-box');
        
        this.getWeather()
        // this.getHoko()
        this.getPoem();
        this.getMusic()

        setInterval(() => {
            this.getTime();
        }, 1000)
        setTimeout(() => {
            loadingBoxDom.style.display = 'none';
        }, 200)
    },
    created() { 
        this.getBgImg()
    },
    methods: {
        gotoUrl(type) {
            if (type === 'biji') {
                window.open('https://www.yuque.com/dashboard', '_blank');
            }
            if (type === 'qianwen') {
                window.open('https://tongyi.aliyun.com/qianwen', '_blank');
            }
            if (type === 'gpt') {
                window.open('http://47.100.118.226:3000/#/', '_blank');
            }
            if (type === 'github') {
                window.open('https://github.com/', '_blank');
            }
            if (type === 'bilibili') {
                window.open('https://www.bilibili.com/', '_blank');
            }
            if (type === 'cainiao') {
                window.open('https://www.cainiaojc.com/', '_blank');
            }
        },
        getBgImg() {
            // 获取一个随机索引
            const randomIndex = Math.floor(Math.random() * this.imagePaths.length);
            // 设置当前背景图片路径
            this.currentImagePath = this.imagePaths[randomIndex];
            this.bgcIndex = randomIndex
            console.log(randomIndex);
            // 触发自定义事件，将随机索引传递给父组件
            // this.$emit('randomIndexUpdated', randomIndex);
            this.$store.commit('SET_BGC_INDEX', randomIndex);
        },
        getWeather() {
            const add_id = "rvjnn0fyqo0kstgn"; // app_id
            const app_secret = "ytQ289YY6NFYVHgN5QOQHchCSoz7kCyO"; // app_secret
            const key = "39380313fb85496b8269140866349c1d" // key
            fetch("https://www.mxnzp.com/api/ip/self?app_id=" + add_id + "&app_secret=" + app_secret)
                .then(response => response.json())
                .then(data => {
                    this.city = data.data.city.replace(/市/g, '');
                    fetch("https://geoapi.qweather.com/v2/city/lookup?location=" + this.city + "&number=1&key=" + key)
                        .then(response => response.json())
                        .then(location => {
                            const id = location.location[0].id;
                            fetch("https://devapi.qweather.com/v7/weather/now?location=" + id + "&key=" + key)
                                .then(response => response.json())
                                .then(weatherData => {
                                    this.weather = weatherData.now;
                                    this.windDirection = weatherData.now.windDir;
                                    this.windScale = weatherData.now.windScale;
                                })
                        })
                        .catch(console.error);
                })
                .catch(console.error);
        },
        getTime() {
            const date = new Date();
            // 获取年、月、日、小时、分钟、秒和星期几
            const year = date.getFullYear();
            const month = ("0" + (date.getMonth() + 1)).slice(-2); // 月份从0开始计数，所以需要加1
            const day = ("0" + date.getDate()).slice(-2);
            const hours = ("0" + date.getHours()).slice(-2);
            const minutes = ("0" + date.getMinutes()).slice(-2);
            const seconds = ("0" + date.getSeconds()).slice(-2);
            const weekday = ["日", "一", "二", "三", "四", "五", "六"][date.getDay()];
            // 拼接成需要的格式 
            const formattedTime = `${hours}:${minutes}:${seconds}`;
            this.weatherDate.year = year;
            this.weatherDate.month = month;
            this.weatherDate.day = day;
            this.weatherDate.hours = hours;
            this.weatherDate.minutes = minutes;
            this.weatherDate.seconds = seconds;
            this.weatherDate.weekday = weekday;
            this.weatherDate.formattedTime = formattedTime;
        },
        addPoetry() {
            this.showModal = true;
        },
        closeModal() {
            this.showModal = false;
        },
        submitPoetry() {
            // 处理提交逻辑
            console.log(this.verse, this.author, this.poemTitle);
            poemService.save(this.verse, this.author, this.poemTitle).then(res => {
                console.log(res);
                this.$message.success(res)
                this.closeModal();
            })
        },
        setRandomFormColors() {
            const selectedColors = this.formColors[this.bgcIndex];
            console.log(selectedColors);
        },
        getHoko() {
            fetch('https://v1.hitokoto.cn?max_length=24', { mode: 'cors' })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    this.hitokoto = data.hitokoto;
                    this.from = data.from;
                    console.log(data);
                })
                .catch(error => {
                    console.error('An error occurred:', error);
                });
        },
        getMusic() {
            // const ap = new APlayer({
            // container: document.getElementById('aplayer'),
            // audio: [{
            //     name: 'name',
            //     artist: 'artist',
            //     url: 'url.mp3',
            //     cover: 'cover.jpg'
            // }]
            // });
            // console.log(ap);
        },
        getPoem() {
            poemService.get().then(res => {
                this.hitokoto = res.verse
                this.from = res.poemTitle
                console.log(res, 'poem');
            })
        }

    }
}



</script>


<style scoped src="@/static/css/loading.css"></style>
<style scoped src="@/static/css/animation.css"></style>
<style scoped src="@/static/css/lantern.css"></style>
<style scoped src="@/static/css/mobile.css"></style>
<style scoped src="@/static/css/style.css"></style>
<style>
* {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

.page_img {
    width: 30px;
    height: 30px;
    margin-right: 5px;
}

.page_item {
    font-size: 20px;
}

.Web_page_item {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 31.3%;
    height: 17vh;
    margin-left: 10px;
    margin-bottom: 10px;
    background: rgb(0 0 0 / 25%);
    backdrop-filter: blur(10px);
    /*margin: 0.5rem;*/
    padding: 1rem;
    border-radius: 6px;
    /* max-width: 450px; */
    cursor: pointer;
}

.Web_page_item:hover {
    transform: scale(1.01);
    transition: 0.5s;
}

.line {
    margin: 1rem 0.25rem;
    margin-top: 2rem;
    font-size: 1.10rem;
    display: flex;
    align-items: center;
}

span.line-text {
    font-size: 1.2rem;
    margin: 0px 6px;
}


/* “添加诗词”选项容器显示时的样式 */
.hitokoto-all:hover .add-poetry-option-container {
    opacity: 1;
    pointer-events: auto;
    transform: translateY(0);
}

/* “添加诗词”选项样式 */
.add-poetry-option {
    font-size: 16px;
    line-height: 1.⅔;
    color: #fff;
    text-align: center;
    cursor: pointer;
}

/* 可选：为“添加诗词”选项添加悬停效果 */
.add-poetry-option:hover {
    color: #ffcb05;
    text-decoration: underline;
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 1000;
}

.modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    z-index: 1001;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.modal-content {
    position: relative;
}

/* 表单样式 */
.poetry-form {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
    padding: 1rem;
    background-color: var(--form-background-color);
    /* 使用CSS变量动态设置背景色 */
}

/* 输入框样式 */
.input-field {
    font-size: 1rem;
    padding: 0.5rem;
    border-radius: 4px;
    outline: none;
    border: 1px solid #ced4da;
    transition: border-color 0.2s ease;
}


/* 提交按钮样式 */
.submit-button {
    font-size: 1rem;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;

    transition: background-color 0.2s ease;

    /* 选中状态保持一致的背景颜色 */
    -webkit-tap-highlight-color: transparent;
}

.submit-button:hover {
    background-color: var(--form-button-hover-color);
}

.close {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 24px;
    cursor: pointer;
}


.form-group {
    display: flex;
    flex-direction: row;
    align-items: baseline;
    gap: 0.5rem;
}

.form-group label {
    font-size: 0.9rem;
    color: #6c757d;
    /* 或选择适合您网站主题的中灰色 */
}



.submit-button {
    font-size: 1rem;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    background-color: #0d6efd;
    color: #fff;
    cursor: pointer;

    transition: background-color 0.2s ease;

    /* 选中状态保持一致的背景颜色 */
    -webkit-tap-highlight-color: transparent;
}

.submit-button:hover {
    background-color: #0a58ca;
}




.aplayer {
    background: rgb(0 0 0 / 20%);
    font-family: Arial, Helvetica, sans-serif;
    /* margin: 5px; */
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .07), 0 1px 5px 0 rgba(0, 0, 0, .1);
    border-radius: 2px;
    overflow: hidden;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    line-height: normal;
    position: relative;
}

.aplayer .aplayer-list ol li {
    position: relative;
    height: 32px;
    line-height: 32px;
    padding: 0 15px;
    font-size: 12px;
    border-top: none;
    cursor: pointer;
    transition: all .2s ease;
    overflow: hidden;
    margin: 0;
}

.aplayer .aplayer-info .aplayer-controller .aplayer-bar-wrap {
    margin: 0 0 0 5px;
    padding: 10px 0;
    cursor: pointer !important;
    flex: 1;
}

.aplayer .aplayer-info {
    margin-left: 100px;
    padding: 14px 7px 0 10px;
    height: 100px;
    box-sizing: border-box;
}

.aplayer.aplayer-withlist .aplayer-info {
    border-bottom: none;
    margin-bottom: 1px;
}

.aplayer .aplayer-list ol li:hover {
    background-color: #ada6a8;
}

.aplayer .aplayer-pic {
    position: relative;
    float: left;
    height: 100px;
    width: 100px;
    background-size: cover;
    background-position: 50%;
    transition: all .3s ease;
    cursor: pointer;
}

.hitokoto-from[data-v-7ba5bd90] {
    margin-top: 10px;
    font-weight: bold;
    align-self: flex-end;
    font-size: 1.65rem;
}

.aplayer .aplayer-info .aplayer-music .aplayer-title {
    color: #fff;
}

.aplayer .aplayer-list ol li .aplayer-list-title {
    color: #fff;
}

.aplayer .aplayer-list ol li.aplayer-list-light {
    background: #ada6a8;
}

.aplayer .aplayer-info .aplayer-music .aplayer-author {
    font-size: 12px;
    color: #fff;
}
</style>