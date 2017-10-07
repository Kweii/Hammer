package com.hammer.rpc.exception;

/**
 * @Author 桂列华
 * @Date 2017/10/6 9:03.
 * @Email guiliehua@163.com
 */
public class RegistryException extends RuntimeException {
    public RegistryException() {
    }

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }

    public RegistryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
