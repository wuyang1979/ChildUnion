/**
 * 产品信息
 */
(function (global) {

    global.modelName = "shop_info";

    global.model = {
        desc: "小店信息",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "小店编号",
                type: 0,
                needInput: true,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "card_id",
                desc: "店主",
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
                name: "company_id",
                desc: "所属机构",
                type: 4,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {
                    model: "buss_league_info",
                    key: "id",
                    value: "company"
                },
                value: '0'
            },
            {
                name: "name",
                desc: "小店名称",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "logopic",
                desc: "logo",
                type: 6,
                needInput: false,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "account_name",
                desc: "账户名称",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "bank_name",
                desc: "开户行",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "bank_account",
                desc: "银行账号",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "can_withdrawal_money",
                desc: "可提现金额",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "already_withdrawal_money",
                desc: "已提现金额",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "progress_withdrawal_money",
                desc: "提现中金额",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "shop_type",
                desc: "小店类型",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '供应商小店'}, {value: 1, desc: '分销商小店'}],
                refs: {},
                value: 0
            },
            {
                name: "type",
                desc: "小店类目",
                type: 2,
                needInput: true,
                initData: [{value: 0, desc: '普通小店'}, {value: 1, desc: '独立小店'}],
                refs: {},
                value: 0
            },
            {
                name: "visit_count",
                desc: "访问次数",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            }
        ]
    };

    global.optionalModel = undefined;

})(this);