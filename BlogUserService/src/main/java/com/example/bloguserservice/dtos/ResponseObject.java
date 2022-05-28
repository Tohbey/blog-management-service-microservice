package com.example.bloguserservice.dtos;

import lombok.Data;

@Data
public class ResponseObject {
    protected Boolean valid;
    protected String message;
    protected Object data;
}
