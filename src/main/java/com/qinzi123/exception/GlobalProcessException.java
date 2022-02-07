package com.qinzi123.exception;

/**
 * @title: GlobalProcessException
 * @package: com.qinzi123.exception
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class GlobalProcessException extends RuntimeException {

    private final String code;

    private final String message;

    private final Throwable cause;

    public GlobalProcessException() {
        super();

        message = null;
        cause = null;
        code = null;

    }

    public GlobalProcessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public GlobalProcessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
        code = null;
    }

    public GlobalProcessException(String message) {
        super(message);
        this.message = message;
        cause = null;
        code = null;
    }

    public GlobalProcessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        cause = null;
    }

    public GlobalProcessException(Error error) {
        super(error.msg());
        this.code = error.code();
        this.message = error.msg();
        cause = null;
    }

    public GlobalProcessException(Error error, Throwable throwable) {
        super(error.msg());
        this.code = error.code();
        this.message = error.msg();
        cause = throwable;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

}
