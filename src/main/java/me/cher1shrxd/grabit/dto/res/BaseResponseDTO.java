package me.cher1shrxd.grabit.dto.res;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseResponseDTO<T> {
    private T data;
    private String message;
    private int status;

    public static <T> BaseResponseDTO<T> of(T data, String message, int status) {
        return new BaseResponseDTO<>(data, message, status);
    }
}
