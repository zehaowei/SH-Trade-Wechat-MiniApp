package com.shtrade.userservice.entity;

public class ErrorResult {
    /**
     * 错误内容
     */
    private String error;

    /**
     * 自定义错误码
     */
    private int code;


    public ErrorResult(String error, int code) {
        this.error = error;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public enum ErrorCode {

        // 用户不存在
        USER_NOT_FOUND(100),

        // 用户已存在
        USER_ALREADY_EXIST(101),

        // 用户密码错误
        USER_PASSWD_ERROR(102),

        // 内部错误
        INTERNAL_ERROR(500),
        ;

        private int code;

        public int getCode() {
            return code;
        }

        ErrorCode(int code) {
            this.code = code;
        }
    }
}
