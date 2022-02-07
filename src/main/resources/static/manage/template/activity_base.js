/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "activity_base";

    global.model = {
        desc: "活动基地",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "活动基地编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "name",
                desc: "名称",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "topic_type_id",
                desc: "主题类型",
                type: 3,
                needInput: true,
                initData: [{value: 1, desc: '历史遗迹'}, {value: 2, desc: '文博场馆'}, {value: 3, desc: '红色旅游'}, {
                    value: 4,
                    desc: '宗教胜迹'
                }, {value: 5, desc: '美丽乡村'}, {value: 6, desc: '南京新景'}, {value: 7, desc: '亲子研学'}, {
                    value: 8,
                    desc: '其它景点'
                }],
                refs: {
                    model: "scenic_spot_type",
                    key: "id",
                    value: "type_name"
                },
                value: []
            },
            {
                name: "leaguetype",
                desc: "会员等级",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "activity_base_league",
                    key: "id",
                    value: "value"
                },
                value: 0
            },
            {
                name: "price",
                desc: "票价",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "open_time",
                desc: "开放时间",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "level",
                desc: "景区等级",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [{value: 0, desc: '无'}, {value: 1, desc: '5A'}, {value: 2, desc: '4A'}, {
                    value: 3,
                    desc: '3A'
                }, {value: 4, desc: '2A'}],
                refs: {
                    model: "scenic_spot_level",
                    key: "id",
                    value: "level"
                },
                value: 0
            },
            {
                name: "traffic",
                desc: "交通",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "workaddress",
                desc: "地址",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "phone",
                desc: "联系方式",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "city",
                desc: "城市",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "district",
                desc: "行政区",
                type: 4,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {
                    model: "scenic_spot_area",
                    key: "id",
                    value: "area"
                },
                value: 0
            },
            {
                name: "official_account_name",
                desc: "公众号名称",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "introduce",
                desc: "详情介绍",
                type: 10,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "card_id",
                desc: "对接人",
                type: 4,
                needInput: false,
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
            }
        ]
    };

    global.optionalModel = undefined;

})(this);