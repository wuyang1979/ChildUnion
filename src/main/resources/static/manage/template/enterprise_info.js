/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "enterprise_info";

    global.model = {
        desc: "企业服务",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "企业服务编号",
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
                name: "card_id",
                desc: "对接人",
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
                name: "phone",
                desc: "联系电话",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "type",
                desc: "企业分类",
                type: 3,
                needInput: true,
                initData: [{value: 'a', desc: '资格考证'}, {value: 'b', desc: '大巴租赁'}, {
                    value: 'c',
                    desc: '活动执行'
                }, {value: 'd', desc: '活动场地'}, {value: 'e', desc: '公众号运营'}, {value: 'f', desc: '抖音运营'}, {
                    value: 'g',
                    desc: '拍摄剪辑'
                }, {value: 'h', desc: '平面设计'}, {value: 'i', desc: '课程设计'}, {value: 'j', desc: '图文制作'}, {
                    value: 'k',
                    desc: '旅行社挂靠'
                }],
                refs: {
                    model: "enterprise_type",
                    key: "type_id",
                    value: "type_name"
                },
                value: []
            },
            {
                name: "isHot",
                desc: "是否热门",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [{value: 0, desc: '非热门'}, {value: 1, desc: '热门'}],
                refs: {
                    model: "enterprise_hot_type",
                    key: "id",
                    value: "hot"
                },
                value: 0
            },
            {
                name: "title",
                desc: "标题",
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
                name: "other_image",
                desc: "其他图片",
                type: 12,
                needInput: false,
                initData: [],
                refs: {},
                value: []
            },
            {
                name: "score",
                desc: "评分",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "introduce",
                desc: "详情",
                type: 10,
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