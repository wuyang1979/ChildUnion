/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "wx_product";

    global.model = {
        desc: "活动管理",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "活动编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "company",
                desc: "公司",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "name",
                desc: "活动标题",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "main_image",
                desc: "主题图片",
                type: 6,
                needInput: true,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "other_image",
                desc: "其他图片",
                type: 12,
                needInput: false,
                initData: [],
                refs: {},
                value: []
            },
            {
                name: "detail",
                desc: "活动详情",
                type: 10,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "price",
                desc: "价格",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "channel_price",
                desc: "渠道价格",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "stock",
                desc: "库存数量",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "limit_stock",
                desc: "起购数量",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "deadline_time",
                desc: "报名截止时间",
                type: 7,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "status",
                desc: "是否上架",
                type: 2,
                needInput: true,
                disabled: false,
                initData: [{value: 0, desc: '下架'}, {value: 1, desc: '上架'}],
                refs: {},
                value: 0
            },
            {
                name: "currency",
                desc: "报名币种",
                type: 2,
                needInput: true,
                disabled: false,
                initData: [{value: 1, desc: '人民币'}, {value: 2, desc: '亲子币'}],
                refs: {},
                value: 0
            },
        ]
    };

    global.optionalModel = undefined;

})(this);