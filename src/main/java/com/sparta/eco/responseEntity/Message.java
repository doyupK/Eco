package com.sparta.eco.responseEntity;

import lombok.Data;

@Data
public class Message {
    private String realName;
    private StatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.realName = null;
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}