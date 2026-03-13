package com.example.blog.global.dto;

public class ApiResponse<T> {

    private final int status;
    private final String msg;
    private final T data;

    public ApiResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "ok", data);
    }

    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(400, msg, null);
    }

    public int getStatus() { return status; }
    public String getMsg() { return msg; }
    public T getData() { return data; }
}
