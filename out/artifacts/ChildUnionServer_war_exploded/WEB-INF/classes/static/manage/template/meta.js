/**
 * 元数据信息
 */
(function (global) {

    var i18n = {
        classId: "班级编号",
        className: "班级名称",
        classType: "班级类型",
        ognizationId: "教学点",
        kidsNumber: "人数",
        startBook: "初始教材",
        currentBook: "当前教材",
        teacherId: "授课老师",
        transportAllowance: "交通补贴",
        classNumber: "课时数",
        remainClassNumber: "剩余课时",
        teachingTime1: "上课时间1",
        classDuration1: "上课时间时长1（小时）",
        teachingTime2: "上课时间2",
        classDuration2: "上课时间时长2（小时）",
        startTime: "开班时间",
        address: "地址",
        teachingAssistant: "班主任"
    };

    var book = [{value: 0, desc: 'Fingerprints 1A'}, {value: 1, desc: 'Fingerprints 1B'}, {
        value: 2,
        desc: 'Fingerprints 2A'
    },
        {value: 3, desc: 'Fingerprints 2B'}, {value: 4, desc: 'Fingerprints 3A'}, {value: 5, desc: 'Fingerprints 3B'},
        {value: 6, desc: 'Phonics'}, {value: 7, desc: 'Nursery Rhyme'}];

    var init = {
        classType: [{value: 0, desc: '家庭'}, {value: 1, desc: '教学点'}],
        startBook: book,
        currentBook: book
    };

    var checkResult = {
        FP: {
            data: ['U1L6', 'U2L7', 'U3L7', 'U4L7', 'U5L7'],
            desc: '测评'
        },
        FF: {
            data: ['U5L7', 'U10L7'],
            desc: 'Review'
        }
    };

    function getDescByName(name) {
        return !!i18n[name] ? i18n[name] : name;
    }

    function getValueByInit(name, value) {
        var list = init[name];
        if (!!list)
            for (var index = 0; index < list.length; index++)
                if (list[index].value == value) return list[index].desc;
        return value;
    }

    global.i18n = i18n;
    global.init = init;
    global.checkResult = checkResult;
    global.getDescByName = getDescByName;
    global.getValueByInit = getValueByInit;

})(this);