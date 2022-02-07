/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "order_view";

    global.model = {
        desc: "订单查询",
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
                name: "payment",
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
                name: "company",
                desc: "公司",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "name",
                desc: "商品名称",
                type: 1,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
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