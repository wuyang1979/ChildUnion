/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "enterprise_comment";

    global.model = {
        desc: "企业服务评价",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "企业服务评价编号",
                type: 0,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "enterprise_id",
                desc: "企业名称",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "enterprise_info",
                    key: "id",
                    value: "name"
                },
                value: '0'
            },
            {
                name: "card_id",
                desc: "评价人",
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
                name: "message",
                desc: "评价内容",
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