package com.mynotion.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse<T> {
    private String code;
    private T data;
    private String message;
}
