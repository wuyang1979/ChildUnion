/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "card_info";

    global.model = {
        desc: "名片功能",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "名片编号",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "phone",
                desc: "电话",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "realname",
                desc: "姓名",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "company",
                desc: "公司",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "job",
                desc: "职位",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "headimgurl",
                desc: "头像url",
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
                name: "introduce",
                desc: "简介",
                type: 10,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "leaguer",
                desc: "会员",
                type: 2,
                needInput: false,
                disabled: false,
                initData: [{value: 0, desc: '非会员'}, {value: 1, desc: '会员'}],
                refs: {},
                value: 0
            },
            {
                name: "gender",
                desc: "性别",
                type: 2,
                needInput: false,
                disabled: false,
                initData: [{value: 0, desc: '男'}, {value: 1, desc: '女'}],
                refs: {},
                value: 0
            },
            {
                name: "score",
                desc: "积分",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "city",
                desc: "城市",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "wx_citys",
                    key: "city_code",
                    value: "city_name"
                },
                value: 220
            }
        ]
    };

    global.optionalModel = undefined;

})(this);