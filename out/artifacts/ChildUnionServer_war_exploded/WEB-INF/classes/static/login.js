$(function () {
    $('#submit').click(function () {
        var userNameValue = $('#username').val();
        var passwordValue = $('#password').val();
        if (!checkName(userNameValue) || !checkPassword(passwordValue)) return;

        $.ajax({
            url: '/loginPost',
            type: 'POST',
            dataType: "json",
            data: {
                user: userNameValue,
                password: passwordValue
            },
            error: function (result) {
                alert("登录失败");
            },
            success: function (result) {
                if (result.success == true) {
                    window.location.href = "/manage/home.html";
                    location.replace('/manage/home.html');
                } else {
                    if (result.message == "001")
                        alert("用户不存在");
                    else if (result.message == "011")
                        alert("密码错误");
                }
            }
        });

    });
});

function checkName(name) {
    if (name == null || name == "") {
        //提示错误
        alert("用户名不能为空");
        return false;
    }
    var reg = /^\w{3,10}$/;
    if (!reg.test(name)) {
        alert("输入3-10个字母或数字或下划线");
        return false;
    }
    return true;
}

function checkPassword(password) {
    if (password == null || password == "") {
        //提示错误
        alert("密码不能为空");
        return false;
    }
    var reg = /^\w{3,10}$/;
    if (!reg.test(password)) {
        alert("输入3-10个字母或数字或下划线");
        return false;
    }
    return true;
}