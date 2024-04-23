package com.ProjectAPI.APIRestUsers.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorHandler {
    private String errorMessage;
    private int errorCode;
}
