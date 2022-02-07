/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "enterprise_consulting";

    global.model = {
        desc: "企业服务咨询",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "企业服务咨询编号",
                type: 0,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "title",
                desc: "企业服务标题",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
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
                name: "name",
                desc: "咨询人",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
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
                name: "read_status",
                desc: "是否已读",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "reserve_read_status",
                    key: "id",
                    value: "value"
                },
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
        ]
    };

    global.optionalModel = undefined;

})(this);