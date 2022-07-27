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
                name: "order_sale_type",
                desc: "订单销售类型",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '销售订单'}, {value: 1, desc: '分销订单'}],
                refs: {},
                value: 0
            },
            {
                name: "retail_commission",
                desc: "一级分销佣金（元，分销订单必填）",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "retail_commission_income",
                desc: "一级分销佣金实际所得（元，分销订单必填）",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "primary_distribution_shop_id",
                desc: "一级分销商小店id（-1：客户分销；0：平台分销；其他：商户分销）",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "primary_distribution_open_id",
                desc: "一级分销用户openid（客户分销必填）",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "platform_service_fee",
                desc: "平台服务费（元，分销订单必填）",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "standard_id",
                desc: "活动/产品规格",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: []
            },
            {
                name: "pay_time",
                desc: "付款时间",
                type: 7,
                needInput: false,
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
                desc: "收货地址（虚拟产品或活动不填）",
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