package com.qinzi123.dto;

/**
 * @title: LoginUser
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public enum LoginUser {

    DEFAULT("admin", "root0000", 0),
    YANCHEN("yanchen", "yanchen123", 329),
    NANJING("nanjing", "nanjing999", 220);

    private String user;
    private String pass;
    private int city;

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public int getCity() {
        return city;
    }

    LoginUser(String user, String pass, int city) {
        this.user = user;
        this.pass = pass;
        this.city = city;
    }

    public static LoginUser getLoginUser(String user, String pass) {
        for (LoginUser loginUser : LoginUser.values())
            if (loginUser.getUser().equals(user) && loginUser.getPass().equals(pass))
                return loginUser;
        //throw new GlobalProcessException("用户名或密码不正确");
        return null;
    }

    public static LoginUser getLoginUser(String user) {
        for (LoginUser loginUser : LoginUser.values())
            if (loginUser.getUser().equals(user))
                return loginUser;
        //throw new GlobalProcessException("用户名或密码不正确");
        return null;
    }
}
