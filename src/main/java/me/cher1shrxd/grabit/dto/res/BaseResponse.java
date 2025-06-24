package me.cher1shrxd.grabit.dto.res;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseResponse<T> {
    private T data;
    private String message;
    private int status;

    public static <T> BaseResponse<T> of(T data, String message, int status) {
        return new BaseResponse<>(data, message, status);
    }
}
