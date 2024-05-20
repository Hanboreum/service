package org.delivery.api.common.error;

public interface ErrorCodeIfs {
//이 세가지는 반드시 정의하도록

    Integer getHttpStatusCode();

    Integer getErrorCode();

    String getDescription();
}
