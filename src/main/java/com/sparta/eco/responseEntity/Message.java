package com.sparta.eco.responseEntity;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object result;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.result = null;
        this.message = null;
    }
}