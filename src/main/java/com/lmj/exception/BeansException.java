package com.lmj.exception;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 14:08 2019-08-07
 **/
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}