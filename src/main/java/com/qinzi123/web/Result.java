package com.qinzi123.web;

import com.qinzi123.exception.Error;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

/**
 * @title: Serializable
 * @package: com.qinzi123.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class Result implements Serializable {

    private String code;

    private String msg;

    private Object data;

    private Integer pageNo;

    private Integer pageSize;

    private Long totalCount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static interface Page<T> {
        List<T> query();
    }

    public static Result ok() {
        Result result = new Result();
        result.setCode("00000000");
        result.setMsg("success");
        return result;
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode("00000000");
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result error(Object data) {
        Result result = new Result();
        result.setCode("FFFFFFFF");
        result.setMsg("fail");
        return result;
    }

    public static Result error(String code) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg("fail");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(Error e) {
        Result result = new Result();
        result.setCode(e.code());
        result.setMsg(e.msg());
        return result;
    }

    public static Result error(Error e, String... params) {
        Result result = new Result();
        result.setCode(e.code());
        result.setMsg(MessageFormat.format(e.msg(), params));
        return result;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Result setPageNo(Integer pageNo) {
        this.pageNo = pageNo;

        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Result setPageSize(Integer pageSize) {
        this.pageSize = pageSize;

        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Result setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
