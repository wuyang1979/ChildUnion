/**
 * 界面空间核心处理类
 */
const lu = new Vue({
        el: '#app',
        data: {
            memberList: [],
            manage: '/manage',
            randomIndex: -1,
            clearTimeSet: null,
            startLuckDrawFlag: true,
            isConfirmShow: false,
        },
        created: function () {
            this.getLucyDrawMemberList();
        },
        methods: {
            getLucyDrawMemberList() {
                this.post('/luckDraw/getLuckDrawMemberList', {}, function (response) {
                    lu.memberList = response.body;
                });
            },
            // 发送请求到服务端
            post: function (url, data, func, localData) {
                var pUrl = this.manage + url;
                this.$http.post(pUrl, data).then(
                    function (response) {
                        if (response.headers.get("content-type") == "text/html") {
                            window.location.href = "/manage/lucy_draw.html";
                            location.replace('/manage/lucy_draw.html');
                        }
                        func(response, localData);
                    },
                    function (response) {
                        var message = response.body.message;
                        var alertMessage = message.split("###");
                        alertMessage.length > 1 ? alert(alertMessage[1]) : alert(message);
                        console.log(response);
                    });
            },
            startLuckDraw: function () {
                if (this.startLuckDrawFlag) {
                    this.setTime();
                    this.startLuckDrawFlag = false;
                } else {
                    this.clearTime();
                    this.startLuckDrawFlag = true;
                }
            },
            //设置定时器
            setTime() {
                let _this = this;
                this.clearTimeSet = setInterval(() => {
                    _this.randomNum();
                }, 50);
            },
            //清除定时器
            clearTime() {
                clearInterval(this.clearTimeSet);
            },
            randomNum() {
                let total = this.memberList.length;
                this.randomIndex = this.getRandomInt(0, total - 1);
            },
            getRandomInt(min, max) {
                if (!(/^\d+$/.test(min) && /^\d+$/.test(max) && max > min)) {
                    throw '参数错误!';
                }
                min = parseInt(min);
                max = parseInt(max);
                return Math.floor(Math.random() * (max - min + 1)) + min;
            }
        }
    })
;