/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "recharge_view";

    global.model = {
        desc: "充值查询",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "订单编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "order_no",
                desc: "支付编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "total",
                desc: "付款金额",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "status",
                desc: "是否支付",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '未支付'}, {value: 1, desc: '已支付'}],
                refs: {},
                value: 0
            },
            {
                name: "total",
                desc: "付款金额",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "card_id",
                desc: "订单人编号",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "realname",
                desc: "订单人",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "pay_time",
                desc: "支付时间",
                type: 7,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            }
        ]
    };

    global.optionalModel = undefined;

})(this);