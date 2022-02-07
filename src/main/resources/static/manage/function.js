/**
 * 工具类
 */
(function (global) {

    String.prototype.padLeft = function (len, c) {
        var result = this;
        var leftLen = len - result.length;
        if (leftLen > 0)
            result = new Array(leftLen + 1).join(c) + result;
        return result;
    }

    Date.prototype.format = function (formatString) {
        var date = this;
        return formatString.replace(new RegExp("[Yy]{4}", "gi"), date.getFullYear()).replace(new RegExp("[Mm]{2}", "gi"), new String(date.getMonth() + 1).padLeft(2, "0")).replace(new RegExp("[Dd]{2}", "gi"), new String(date.getDate()).padLeft(2, "0")).replace(new RegExp("[Hh]{2}", "gi"), new String(date.getHours()).padLeft(2, "0")).replace(new RegExp("[Mm][Ss]", "gi"), new String(date.getMinutes()).padLeft(2, "0")).replace(new RegExp("[Ss]{2}", "gi"), new String(date.getSeconds()).padLeft(2, "0"));
    }

    function isEmptyObj(obj) {
        if (!obj) return true;
        for (var t in obj)
            return false;
        return true;
    }

    function compareObject(prop, order) {
        order = !!order ? order : "ascending";
        return function (o, p) {
            var a, b;
            if (typeof o === "object" && typeof p === "object" && o && p) {
                a = o[prop];
                b = p[prop];
                if (a === b) return 0;
                if (typeof a === typeof b) return (order == "ascending" ? a < b : a > b) ? -1 : 1;
                return (order == "ascending" ? typeof a < typeof b : typeof a > typeof b) ? -1 : 1;
            } else
                return 0;
        }
    }

    function isArray(obj) {
        return Object.prototype.toString.call(obj) == '[object Array]';
    }

    function loadJs(url, callback) {
        var script = document.createElement("script")
        script.type = "text/javascript";
        script.id = url;
        if (script.readyState) {
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    callback();
                }
            };
        } else {
            script.onload = function () {
                callback();
            };
        }
        script.src = url;
        document.getElementsByTagName("body")[0].appendChild(script);
    }

    function removeJs(id) {
        var element = document.getElementById(id);
        if (!!element)
            element.parentNode.removeChild(element);
        //document.body.removeChild();
    }

    function formatDate(date) {
        var date = new Date(date);
        var YY = date.getFullYear() + '-';
        var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
        var hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return YY + MM + DD + " " + hh + mm + ss;
    }

    function checkPhoto(file) {
        var isJPG = file.type === 'image/jpeg';
        var isLt2M = file.size / 1024 / 1024 < 2;
        var errMsg = "";

        if (!isJPG) {
            errMsg = '上传头像图片只能是 JPG 格式!';
        }
        if (!isLt2M) {
            errMsg = '上传头像图片大小不能超过 2MB!';
        }
        return {result: isJPG && isLt2M, msg: errMsg};
    }

    function checkTableClick(rows, type) {
        var errMsg = "";
        if (type == 1 || type == 2) {
            if (rows.length == 0) {
                return {
                    result: false,
                    msg: "请选择表格行"
                }
            }
            if (type == 1) {
                if (rows.length > 1) {
                    return {
                        result: false,
                        msg: "只能选择一行修改"
                    }
                }
            }
        }
        return {result: true, msg: ""};
    }

    function checkDialogCommon(tableMeta) {
        for (var index = 0; index < tableMeta.columns.length; index++) {
            if (tableMeta.columns[index].name == tableMeta.key) continue;

            if (!!tableMeta.columns[index].needInput && tableMeta.columns[index].value == undefined)
                return {
                    result: false,
                    msg: tableMeta.columns[index].desc + "必须输入"
                }
        }
        return {result: true, msg: ""};
    }

    global.loadJs = loadJs;
    global.removeJs = removeJs;
    global.isEmptyObj = isEmptyObj;
    global.compareObject = compareObject;
    global.isArray = isArray;
    global.checkPhoto = checkPhoto;
    global.checkTableClick = checkTableClick;
    global.checkDialogCommon = checkDialogCommon;
    global.formatDate = formatDate;

})(this);