/**
 * 产品信息
 */
(function (global) {

    global.modelName = "product_info";

    global.model = {
        desc: "产品信息",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "产品编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "status",
                desc: "是否上架",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '下架'}, {value: 1, desc: '上架'}],
                refs: {},
                value: 0
            },
            {
                name: "card_id",
                desc: "发布人",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "card_info",
                    key: "id",
                    value: "realname"
                },
                value: '0'
            },
            {
                name: "name",
                desc: "产品名称",
                type: 1,
                needInput: true,
                disabled: false,
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
                name: "original_price",
                desc: "产品原价（元）",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "present_price",
                desc: "产品现价（元）",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "inventory",
                desc: "库存",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "repeat_purchase",
                desc: "是否允许重复购买",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '允许'}, {value: 1, desc: '不允许'}],
                refs: {},
                value: 0
            },
            {
                name: "once_max_purchase_count",
                desc: "单次最大购买数量（-1为不限）",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "phone",
                desc: "客服电话",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "deadline_time",
                desc: "产品截止时间",
                type: 7,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "introduce",
                desc: "产品介绍",
                type: 10,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
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
                name: "vedio_path",
                desc: "产品视频",
                type: 9,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: ''
            },
            {
                name: "instruction",
                desc: "购买须知",
                type: 10,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },

        ]
    };

    global.optionalModel = undefined;

})(this);