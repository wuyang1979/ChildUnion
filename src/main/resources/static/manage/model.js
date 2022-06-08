/**
 * 界面空间核心处理类
 */
var vm = new Vue({
    el: '#app',
    data: {
        dialogFormVisible: false, // 初始是否显示弹出框
        dialogForAdd: true,    // 是否是增加记录
        isShowTable: false,  // 初始化是否显示表格
        pageSize: 20,         // 表格显示记录数
        activeTabsName: 'first',  // 激活的tab页

        searchValue: '',     // 搜索框绑定数据
        currentPageNo: 0,    // 当前页
        currentRows: [],     // 当前选中的表格行
        currentUrl: '',      // 当前菜单
        showColumns: [],     // 表格显示列
        manage: '/manage',

        menuList: menu,       // 菜单元数据
        tableUrl: "",         // 原型URL
        tableMeta: {},        // 模型元数据
        optional: [],         // 详情页数据
        columnTranslate: {}, // ID - Value 的翻译
        modelRefs: [],          // 模型关联
        modelRefsCount: 0,     // 模型关联数目
        dialogData: {},        // 弹出框数据模型
        tableData: [],        // 当前表格数据
        allLoadData: []       // 所有加载数据
    },
    created: function () {
        this.loadData();
        sessionStorage.setItem("columnIndex", "");
    },
    methods: {
        /***************************************************************************************
         基本方法
         ************************************************************************************** */
        // 检查结果
        checkCommon: function (checkResult) {
            if (!checkResult.result)
                alert(checkResult.msg);
            return checkResult.result;
        },
        // 设置当前页默认情况
        setCurrentPage: function (currentPage) {
            this.currentPageNo = this.currentPageNo == 0 ? 1 : this.currentPageNo;
            return this.currentPageNo;
        },
        // 获取每页的表格数据
        getPageData: function (array, currentPage) {
            if (this.showColumns.length == 0) this.fillTableShowColumns(this.tableMeta.columns);
            return array.slice((currentPage - 1) * this.pageSize, currentPage * this.pageSize);
        },
        // 表格元素格式化
        tableColumnFormmater: function (row, column, cellValue) {
            return this.formatter(row, column.property);
        },
        formatter: function (row, columnName) {
            return data2View(columnName, row[columnName]);
        },
        handleClose: function (done) {
            // 空方法阻塞弹出框的close事件
        },
        // 表格标题显示格式化
        headerFormatter: function (func, columnObject) {
            var result = columnObject.column.label;
            return formatterColumn(result);
        },
        getDescByName: function (name) {
            return getDescByName(name);
        },
        getValueByInit: function (name, value) {
            return getValueByInit(name, value);
        },
        getColumnMetaByName: function (columnName) {
            for (var index = 0; index < this.tableMeta.columns.length; index++)
                if (this.tableMeta.columns[index].name == columnName) {
                    return {
                        columnIndex: index,
                        data: this.tableMeta.columns[index]
                    }
                }
            console.log("cannot get column meta from " + columnName);
            return {columnIndex: -1};
        },
        fillTableShowColumns: function (columns) {
            for (var index = 0; index < columns.length; index++)
                this.showColumns.push(columns[index].name);
        },

        /***************************************************************************************
         事件响应
         ************************************************************************************** */

        // 分页点击
        handleCurrentChange: function (currentPage) {
            this.tableData = this.getPageData(this.allLoadData, currentPage);
            this.currentPageNo = currentPage;
        },
        // 分页数据改变
        handleSizeChange: function (currentPageSize) {
            this.pageSize = currentPageSize;
            this.currentPageNo = this.currentPageNo == 0 ? 1 : this.currentPageNo;
            //this.tableData = this.getPageData(this.allLoadData, this.currentPageNo == 0 ? 1 : this.currentPageNo);
            this.handleCurrentChange(this.setCurrentPage(this.currentPageNo));
        },
        indexMethod: function (index) {
            return (this.currentPageNo - 1) * this.pageSize + index;
        },
        // 表格选择
        handleSelectionChange: function (rows) {
            this.currentRows = rows;
        },
        // 排序选择
        columnSortChange: function (sortColumn) {
            this.allLoadData.sort(compareObject(sortColumn["prop"], sortColumn["order"]));
            this.handleCurrentChange(this.setCurrentPage(this.currentPageNo));
        },
        // 表格按钮点击
        handleTableClick: function (type) {
            let op = this;
            if (!this.checkCommon(checkTableClick(this.currentRows, type))) return;

            // 执行
            if (type == 0 || type == 1) {
                this.dialogFormVisible = true;
                this.dialogForAdd = type == 0 ? true : false;
                //if(type == 0){
                for (var index = 0; index < this.tableMeta.columns.length; index++)
                    if (isArray(this.tableMeta.columns[index].value))
                        this.tableMeta.columns[index].value = [];
                    else
                        this.tableMeta.columns[index].value = undefined;
                //}

                if (type == 0) load_init();
                if (type == 1) {
                    var oneData = this.currentRows[0];
                    update_init(oneData);
                    for (var key in oneData) {
                        var oneColumn = this.getColumnMetaByName(key);
                        /** 如果没有找到, 说明该字段不要显示 **/
                        if (oneColumn.columnIndex == -1) continue;
                        this.tableMeta.columns[oneColumn.columnIndex].value = translateDataBase2Js(oneColumn, oneData[key]);
                    }

                    //获取其他图片列表
                    if (this.tableUrl == "activity_base") {
                        let baseId = this.tableMeta.columns[0].value;
                        this.post("/base/pictureList", {id: baseId}, function (result) {
                            for (let i = 0; i < result.body.length; i++) {
                                for (let j = 0; j < op.tableMeta.columns.length; j++) {
                                    if (op.tableMeta.columns[j].name == "other_image") {
                                        op.tableMeta.columns[j].value.push(result.body[i].src);
                                    }
                                }
                            }
                        });
                        let typeStr;
                        for (let j = 0; j < op.tableMeta.columns.length; j++) {
                            if (op.tableMeta.columns[j].name == "topic_type_id") {
                                typeStr = this.tableMeta.columns[j].value;
                            }
                        }
                        let typeArr = [];
                        if (typeStr.indexOf("/") != -1) {
                            let typeArrTemp = typeStr.split("/");
                            for (let i = 0; i < typeArrTemp.length; i++) {
                                typeArr.push(parseInt(typeArrTemp[i]));
                            }
                        } else {
                            typeArr.push(parseInt(typeStr));
                        }
                        for (let k = 0; k < op.tableMeta.columns.length; k++) {
                            if (op.tableMeta.columns[k].name == "topic_type_id") {
                                op.tableMeta.columns[k].value = typeArr;
                            }
                        }
                    } else if (this.tableUrl == "wx_product") {
                        let productId = this.tableMeta.columns[0].value;
                        this.post("/campaign/pictureList", {id: productId}, function (result) {
                            for (let i = 0; i < result.body.length; i++) {
                                for (let j = 0; j < op.tableMeta.columns.length; j++) {
                                    if (op.tableMeta.columns[j].name == "other_image") {
                                        op.tableMeta.columns[j].value.push(result.body[i].url);
                                    }
                                }
                            }
                        });
                    } else if (this.tableUrl == "product_info") {
                        let productId = this.tableMeta.columns[0].value;
                        this.post("/product/pictureList", {id: productId}, function (result) {
                            for (let i = 0; i < result.body.length; i++) {
                                for (let j = 0; j < op.tableMeta.columns.length; j++) {
                                    if (op.tableMeta.columns[j].name == "other_image") {
                                        op.tableMeta.columns[j].value.push(result.body[i].url);
                                    }
                                }
                            }
                        });
                    } else if (this.tableUrl == "enterprise_info") {
                        let ennterpriseId = this.tableMeta.columns[0].value;
                        this.post("/enterprise/pictureList", {id: ennterpriseId}, function (result) {
                            for (let i = 0; i < result.body.length; i++) {
                                for (let j = 0; j < op.tableMeta.columns.length; j++) {
                                    if (op.tableMeta.columns[j].name == "other_image") {
                                        op.tableMeta.columns[j].value.push(result.body[i].url);
                                    }
                                }
                            }
                        });
                        let typeStr;
                        for (let j = 0; j < op.tableMeta.columns.length; j++) {
                            if (op.tableMeta.columns[j].name == "type") {
                                typeStr = this.tableMeta.columns[j].value;
                            }
                        }
                        let typeArr = [];
                        if (typeStr.indexOf("/") != -1) {
                            let typeArrTemp = typeStr.split("/");
                            for (let i = 0; i < typeArrTemp.length; i++) {
                                typeArr.push(typeArrTemp[i]);
                            }
                        } else {
                            typeArr.push(typeStr);
                        }
                        for (let k = 0; k < op.tableMeta.columns.length; k++) {
                            if (op.tableMeta.columns[k].name == "type") {
                                op.tableMeta.columns[k].value = typeArr;
                            }
                        }
                    }
                }
            } else {
                var r = confirm("确认删除这些数据?");
                if (r == true) {
                    this.deleteData();
                }
            }

        },
        // 表格行点击
        handleRowClick: function (row, event, column) {
            if (!!optionalModel) {
                var data = {};
                data[optionalModel.key] = row[optionalModel.key];
                if (optionalModel.model == 'salary') {
                    data['dateString'] = this.searchValue;
                }
                this.loadOptional(optionalModel.model, data, function (response) {
                    var result = response.body;
                    if (optionalModel.model == 'salary') {
                        vm.optional = result;
                        return;
                    }
                    result = !!result && result.length > 0 ? result[0] : {};
                    vm.optional = [];
                    for (var prop in result)
                        if (prop != optionalModel.key)
                            vm.optional.push({key: prop, value: data2View(prop, result[prop])});//!!row[prop] ? data2View(prop, row[prop]) : result[prop]
                });
                this.activeTabsName = "second";
            }
        },
        // 弹出框点击
        handleDialogClick: function () {
            if (!this.checkCommon(checkDialogCommon(this.tableMeta))) return;
            this.dialogForAdd ? this.addData() : this.updateData();
        },
        // 搜索按钮点击
        search: function () {
            if (this.searchValue == undefined || this.searchValue.length == 0) return;
            this.searchData(this.tableUrl, {key: this.searchValue}, function (response) {
                vm.allLoadData = response.body;
                vm.allLoadData.reverse();
                vm.tableData = vm.getPageData(vm.allLoadData, 1);
            });
        },
        // 下拉框控制 - 走control处理
        selectChange: function (event) {
            bind_select_change(event);
        },
        // 单选框控制 - 走control处理
        radioChange: function (label) {
            bind_radio_change(label);
        },
        // 菜单点击
        handleMenuClick: function (index, path) {
            this.activeTabsName = 'first';
            this.searchValue = '';
            this.showColumns = [];
            if (index == this.currentUrl) {
                return;
            }
            this.isShowTable = true;
            removeJs(this.currentUrl);
            this.currentUrl = index + ".js";
            loadJs(this.currentUrl, function () {
                vm.refresh();
                load_extra_data();
            });
        },
        handleTabsClick: function (tab, event) {
            //console.log(tab, event);
        },
        // 主题图片上传的事件
        handleTopicAvatarSuccess: function (res, file) {
            var oneColumn = this.getColumnMetaByName(res.field);
            this.tableMeta.columns[oneColumn.columnIndex].value = res.url;
        },
        // 多张图片上传的事件
        handleAvatarSuccess: function (res, file) {
            var oneColumn = this.getColumnMetaByName(res.field);
            sessionStorage.setItem("columnIndex", oneColumn.columnIndex + "");
            this.tableMeta.columns[oneColumn.columnIndex].value.push(res.url);
        },
        //删除上传的图片
        deleteSelf: function (index) {
            if (this.tableUrl == "activity_base" || this.tableUrl == "wx_product" || this.tableUrl == "enterprise_info" || this.tableUrl == "product_info") {
                for (let i = 0; i < this.tableMeta.columns.length; i++) {
                    if (this.tableMeta.columns[i].name == "other_image") {
                        this.tableMeta.columns[i].value.splice(index, 1);
                    }
                }
            } else {
                var columnIndex = parseInt(sessionStorage.getItem("columnIndex"))
                this.tableMeta.columns[columnIndex].value.splice(index, 1);
            }
        },
        beforeAvatarUpload: function (file) {
            return this.checkCommon(checkPhoto(file));
        },

        // 把翻译表的字段变成标准的描述
        fillTranslateData: function (data, refs) {
            return data.map(function (oneData) {
                return {value: oneData[refs.key], desc: oneData[refs.value]};
            })
        },
        refresh: function () {
            // 初始化原始数据
            this.tableMeta = model;
            this.tableUrl = modelName;

            // 初始化字段翻译和界面显示部分
            var columns = this.tableMeta.columns;
            this.modelRefs = [];
            for (var index = 0; index < columns.length; index++) {
                !isEmptyObj(columns[index].refs) ? this.modelRefs.push({
                        colIndex: index,
                        name: columns[index].name,
                        refs: columns[index].refs
                    })
                    : this.columnTranslate[columns[index].name] = {translate: columns[index].initData};
            }

            this.loadData();
            this.modelRefsCount = this.modelRefs.length;
            for (var index = 0; index < this.modelRefs.length; index++) {
                this.loadRefs(this.modelRefs[index].refs, function (response, index) {
                    var data = vm.fillTranslateData(response.body, vm.modelRefs[index].refs);
                    vm.tableMeta.columns[vm.modelRefs[index].colIndex].initData = data;
                    vm.columnTranslate[vm.modelRefs[index].name] = {translate: data};
                    vm.modelRefsCount--;
                    if (this.modelRefsCount == 0) {
                        vm.tableData = vm.getPageData(vm.allLoadData, 1);
                        vm.modelRefs = [];
                    }
                }, index);
            }
        },

        /***************************************************************************************
         数据处理,前后台交互
         ************************************************************************************** */
        // 填写表单数据
        makeDialogData: function () {
            var dialogData = {};
            for (var index = 0; index < this.tableMeta.columns.length; index++) {
                var oneColumn = this.tableMeta.columns[index];
                var value
                if (oneColumn.name == "deadline_time" || oneColumn.name == "create_time" || oneColumn.name == "pay_time") {
                    value = formatDate(oneColumn.value);
                } else {
                    value = oneColumn.value instanceof Date ? oneColumn.value.format("yyyy-MM-dd") : (
                        isArray(oneColumn.value) ? oneColumn.value.join(",") : oneColumn.value);
                }
                dialogData[oneColumn.name] = value;
            }
            return dialogData;
        },
        // 发送请求到服务端
        post: function (url, data, func, localData) {
            var pUrl = this.manage + url;
            this.$http.post(pUrl, data).then(
                function (response) {
                    if (response.headers.get("content-type") == "text/html") {
                        window.location.href = "/manage/home.html";
                        location.replace('/manage/home.html');
                    }
                    func(response, localData);
                },
                function (response) {
                    var message = response.body.message;
                    var alertMessage = message.split("###");
                    alertMessage.length > 1 ? alert(alertMessage[1]) : alert(message);
                    console.log(response);
                });
        },
        // 翻译请求 - 从另外的模型加载数据
        loadRefs: function (data, func, index) {
            this.post('/showSpecialService', data, func, index);
        },
        // 获取详情数据
        loadOptional: function (model, data, func) {
            this.post('/' + model + '/findEntity', data, func, undefined);
        },
        // 搜索数据
        searchData: function (model, data, func) {
            this.post('/' + model + '/search', data, func, undefined);
        },
        // 查询数据
        loadData: function () {
            if (!this.tableUrl) return;
            this.post('/' + this.tableUrl + '/showEntitys', {}, function (response) {
                vm.allLoadData = response.body;
                vm.allLoadData.reverse()
                vm.tableData = vm.getPageData(vm.allLoadData, 1);
            });
        },
        // 数据操作
        setData: function (url, data) {
            this.dialogFormVisible = false;
            if (!this.tableUrl) return;
            this.post(url, data, function (response) {
                vm.loadData();
            });
        },
        addData: function () {
            this.setData('/' + this.tableUrl + '/addEntity', this.makeDialogData());
        },
        updateData: function () {
            this.setData('/' + this.tableUrl + '/updateEntity', this.makeDialogData());

        },
        deleteData: function () {
            this.setData('/' + this.tableUrl + '/deleteEntity', this.currentRows);
        }
    }
});