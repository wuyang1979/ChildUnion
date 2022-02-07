/**
 * 班级管理模型
 */
(function (global) {

    global.modelName = "buss_league_info";

    global.model = {
        desc: "企业机构",
        key: "id",
        columns: [
            {
                name: "id",
                desc: "企业机构编号",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: 0
            },
            {
                name: "leaguetype",
                desc: "认证/会员",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "buss_league_type",
                    key: "id",
                    value: "value"
                },
                value: '0'
            },
            {
                name: "companydesc",
                desc: "企业介绍",
                type: 10,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "company",
                desc: "机构名称",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "logopic",
                desc: "机构Logo",
                type: 6,
                needInput: true,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "licensepic",
                desc: "营业执照",
                type: 6,
                needInput: false,
                initData: [],
                refs: {},
                value: ""
            },
            {
                name: "members",
                desc: "员工人数",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "buss_league_members",
                    key: "id",
                    value: "value"
                },
                value: '0'
            },
            {
                name: "companyaddr",
                desc: "地址",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "companytel",
                desc: "联系方式",
                type: 1,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "companyweb",
                desc: "机构官网",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "industry",
                desc: "所属行业",
                type: 4,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {
                    model: "buss_league_industry",
                    key: "id",
                    value: "value"
                },
                value: '0'
            },
            {
                name: "mainbussiness",
                desc: "主营业务",
                type: 10,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "maindemand",
                desc: "主要需求",
                type: 10,
                needInput: true,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "email",
                desc: "邮箱地址",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "contactname",
                desc: "负责人",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "contactduty",
                desc: "负责人职位",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "contacttel",
                desc: "负责人联系方式",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
            {
                name: "contactwx",
                desc: "负责人微信",
                type: 1,
                needInput: false,
                disabled: false,
                initData: [],
                refs: {},
                value: '0'
            },
        ]
    };

    global.optionalModel = undefined;

})(this);