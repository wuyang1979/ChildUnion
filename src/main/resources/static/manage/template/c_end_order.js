/**
 * 产品信息
 */
(function (global) {

    global.modelName = "c_end_order";

    global.model = {
        desc: "成长GO订单",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "订单id ",
                type: 0,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "order_no",
                desc: "订单编号",
                type: 1,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "product_id",
                desc: "产品/活动名称",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "product_info",
                    key: "id",
                    value: "name"
                },
                value: '0'
            },
            {
                name: "open_id",
                desc: "openId",
                type: 1,
                needInput: false,
                disabled: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "phone",
                desc: "手机号",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "price",
                desc: "单价",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "num",
                desc: "数量",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "total",
                desc: "合计",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "order_type",
                desc: "订单类型",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '产品订单'}, {value: 1, desc: '活动订单'}],
                refs: {},
                value: 0
            },
            {
                name: "standard_id",
                desc: "活动规格（产品不选）",
                type: 4,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {
                    model: "c_end_activity_standards",
                    key: "id",
                    value: "standard_desc"
                },
                value: '0'
            },
            {
                name: "pay_time",
                desc: "付款时间",
                type: 7,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "status",
                desc: "订单状态",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '待支付'}, {value: 1, desc: '已支付'}, {value: 2, desc: '已完成'}],
                refs: {},
                value: 0
            },
            {
                name: "receive_address",
                desc: "收货地址（活动不填）",
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