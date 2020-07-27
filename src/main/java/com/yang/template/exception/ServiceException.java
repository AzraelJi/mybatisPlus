package com.yang.template.exception;

import lombok.Getter;

/**
 * @author jjyy
 * @apiNote 业务逻辑异常
 * @since 2019-8-30
 */
@Getter
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }


}
