package org.linker.foundation.dto.response;

import org.linker.foundation.dto.exception.IMessageCode;

public enum CommonCode implements IMessageCode {

    SUCCESS("200", "SUCCESS"),
    Unavailable("400", "请求数据无效"),
    NoAuthority("401", "未授权访问或授权码过期，请重新登录后再继续操作"),
    Forbidden("403", "您没有权限访问该服务"),
    NotFound("404", "资源未找到"),
    Unsafety("406", "请求数据验证失败（你的网络安全可能出现问题）"),
    NotGone("410", "服务不可用"),
    InvalidTime("412", "客户端时间异常"),
    SvError("500", "服务异常"),
    Timeout("513", "服务处理超时");

    private final String code;
    private final String desc;

    CommonCode(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return desc;
    }
}
