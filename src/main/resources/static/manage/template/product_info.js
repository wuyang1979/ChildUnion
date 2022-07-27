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
                initData: [{value: 0, desc: '下架'}, {value: 1, desc: '上架'}, {value: 2, desc: '审核中'}],
                refs: {},
                value: 0
            },
            {
                name: "is_hot",
                desc: "是否爆款",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '非爆款'}, {value: 1, desc: '爆款'}],
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
                name: "shop_id",
                desc: "发布小店",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "shop_info",
                    key: "id",
                    value: "name"
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
                name: "address",
                desc: "地址",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "address_name",
                desc: "地点",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "longitude",
                desc: "经度",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "latitude",
                desc: "纬度",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "product_type",
                desc: "发布类型",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '产品'}, {value: 1, desc: '活动'}],
                refs: {},
                value: 0
            },
            {
                name: "type",
                desc: "产品模式（活动不选）",
                type: 2,
                needInput: false,
                initData: [{value: 0, desc: '单一产品'}, {value: 1, desc: '联合产品'}],
                refs: {},
                value: '0'
            },
            {
                name: "product_style",
                desc: "产品类型（活动不选）",
                type: 2,
                needInput: false,
                initData: [{value: 0, desc: '虚拟产品'}, {value: 1, desc: '实体产品'}],
                refs: {},
                value: '0'
            },
            {
                name: "standardList",
                desc: "产品/活动规格",
                type: 13,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: []
            },
            {
                name: "wuyu_type",
                desc: "五育类型",
                type: 3,
                needInput: false,
                initData: [
                    {value: 1, desc: '徳'},
                    {value: 2, desc: '智'},
                    {value: 3, desc: '体'},
                    {value: 4, desc: '美'},
                    {value: 5, desc: '劳'}
                ],
                refs: {
                    model: "wu_yu_type",
                    key: "id",
                    value: "type_name"
                },
                value: []
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
                name: "is_allow_distribution",
                desc: "是否允许分销",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '不允许'}, {value: 1, desc: '允许'}],
                refs: {},
                value: '0'
            },
            {
                name: "repeat_purchase",
                desc: "是否允许重复购买",
                type: 2,
                needInput: true,
                initData: [{value: 1, desc: '不允许'}, {value: 0, desc: '允许'}],
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
                name: "introduce",
                desc: "产品/活动介绍",
                type: 10,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "vedio_path",
                desc: "产品/活动视频",
                type: 9,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: ''
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
                name: "instruction",
                desc: "购买须知",
                type: 10,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "buy_count",
                desc: "已售数量",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "deadline_time",
                desc: "截止时间",
                type: 7,
                needInput: true,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "qr_image",
                desc: "群二维码",
                type: 6,
                needInput: false,
                initData: [],
                refs: {},
                value: ""
            },
        ]
    };

    global.optionalModel = undefined;

})(this);