package org.linker.foundation.dto.exception;

/**
 * @author RWM
 * @date 2018/4/2
 */
public enum MessageCode implements IMessageCode {
    ;

    private final String code;
    private final String msg;

    MessageCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
