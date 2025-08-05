package me.goldze.mvvmhabit.http;

/**
 * @author: Jun
 * @date: 2022/10/27
 */
public class APIException extends Exception{
    private String errorMsg;
    private int code;
    private String log;
    private Throwable throwable;

    public APIException(String errorMsg, int code) {
        this.errorMsg = errorMsg;
        this.code = code;
    }

    public APIException(String errorMsg, int code, String log, Throwable throwable) {
        this.errorMsg = errorMsg;
        this.code = code;
        this.log = log;
        this.throwable = throwable;
    }

    public APIException(String message,Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public APIException(String message, Throwable cause, String errorMsg, int code, String log, Throwable throwable) {
        super(message, cause);
        this.errorMsg = errorMsg;
        this.code = code;
        this.log = log;
        this.throwable = throwable;
    }

    public APIException(Throwable cause, String errorMsg, int code, String log, Throwable throwable) {
        super(cause);
        this.errorMsg = errorMsg;
        this.code = code;
        this.log = log;
        this.throwable = throwable;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
