/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "wx_banner";

    global.model = {
        desc: "广告栏管理",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "广告编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "link",
                desc: "链接文章地址",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "url",
                desc: "图片地址",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "isTab",
                desc: "是否Tab首页",
                type: 2,
                needInput: true,
                disabled: false,
                initData: [{value: 0, desc: '否'}, {value: 1, desc: '是'}],
                refs: {},
                value: 0
            },
            {
                name: "isLocal",
                desc: "是否小程序本地页面",
                type: 2,
                needInput: true,
                disabled: false,
                initData: [{value: 0, desc: '否'}, {value: 1, desc: '是'}],
                refs: {},
                value: 0
            },
            {
                name: "order_id",
                desc: "排序编号(从小到大)",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
        ]
    };

    global.optionalModel = undefined;

})(this);