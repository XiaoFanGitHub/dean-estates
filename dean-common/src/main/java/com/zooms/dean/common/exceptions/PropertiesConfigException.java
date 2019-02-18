package com.zooms.dean.common.exceptions;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/4/17
 */
public class PropertiesConfigException extends RuntimeException {
    private static final long serialVersionUID = 6629908511615986511L;

    public PropertiesConfigException(String message) {
        super(message);
    }

    public PropertiesConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
