/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "card_message";

    global.model = {
        desc: "合作消息",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "消息编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "card_id",
                desc: "发布者",
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
                name: "form_id",
                desc: "发送表单编号",
                type: 0,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "title",
                desc: "消息标题",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "message_type",
                desc: "消息类型",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '赞助招商'}, {value: 1, desc: '项目合作'}, {value: 2, desc: '招租转让'},
                    {value: 3, desc: '资源互换'}, {value: 4, desc: '求职招聘'}, {value: 5, desc: '其它信息'}],
                refs: {},
                value: 0
            },
            {
                name: "message",
                desc: "消息内容",
                type: 10,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "update_time",
                desc: "更新时间",
                type: 7,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "read_count",
                desc: "阅读次数",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "give_like",
                desc: "点赞次数",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "source_type",
                desc: "资源类型",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '图片'}, {value: 1, desc: '视频'}],
                refs: {},
                value: 0
            },
            {
                name: "source_path",
                desc: "资源路径",
                type: 9,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: ''
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