/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "activity_base_reserve";

    global.model = {
        desc: "活动基地预订",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "活动基地预订编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "activity_base_name",
                desc: "活动基地名称",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "card_name",
                desc: "预订人",
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
            }
        ]
    };

    global.optionalModel = undefined;

})(this);