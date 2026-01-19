package com.ssafy.projectree.global.api.code;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND_ERROR(DomainCode.USER, ExceptionCode.NOT_FOUND, "USER_NOT_FOUND_ERROR"),
    ;

    private DomainCode domainCode;
    private ExceptionCode exceptionCode;
    @Getter
    private String defaultMessage;

    public int status() {
        return domainCode.getValue() * 100 + exceptionCode.getValue();
    }


}
