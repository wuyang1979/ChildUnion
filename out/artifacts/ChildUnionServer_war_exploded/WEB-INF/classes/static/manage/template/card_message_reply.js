/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "card_message_reply";

    global.model = {
        desc: "合作消息回复",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "回复编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "message_id",
                desc: "消息",
                type: 4,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {
                    model: "card_message",
                    key: "id",
                    value: "title"
                },
                value: '0'
            },
            {
                name: "card_id",
                desc: "发布者",
                type: 4,
                needInput: true,
                disabled: true,
                initData: [],
                refs: {
                    model: "card_info",
                    key: "id",
                    value: "realname"
                },
                value: '0'
            },
            {
                name: "reply_id",
                desc: "回复编号",
                type: 1,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "reply_message",
                desc: "回复详情",
                type: 10,
                needInput: true,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "create_time",
                desc: "创建时间",
                type: 7,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "verify",
                desc: "审核情况",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '待审批'}, {value: 1, desc: '审批通过'}, {value: 2, desc: '审批不通过'}],
                refs: {},
                value: 0
            }
        ]
    };

    global.optionalModel = undefined;

})(this);