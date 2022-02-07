/**
 * 界面空间核心处理类
 */
const lu = new Vue({
    el: '#app',
    data: {
        memberList: [],
        manage: '/manage',
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

        }
    }
});