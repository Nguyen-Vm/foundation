package org.linker.foundation.dto.exception;

/**
 * @author RWM
 * @date 2018/4/2
 */
public interface IMessageCode {

    String code();

    String msg();

    default String message() {
        return code() + " -> " + msg();
    }
}
