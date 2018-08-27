package org.linker.foundation.JavaBean;

/**
 * @author RWM
 * @date 2018/8/16
 */
public class YtxResponse {

    // 返回码
    public String statusCode;

    // 返回码描述
    public String statusMsg;

    // 发送成功后返回的唯一标识
    public String requestId;

    @Override
    public String toString() {
        return "YtxResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
