package org.linker.foundation.web;

import org.linker.foundation.dto.exception.AppException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RWM
 * @date 2018/6/7
 */
public abstract class WebConfigureSupport {

    /**
     * 全局异常处理
     **/
    @ControllerAdvice
    public static class GlobalExceptionHandle {
        @ExceptionHandler(value = Exception.class)
        @ResponseBody
        private Map<String, Object> exceptionHandle(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Exception e) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("result", false);
            modelMap.put("code", e instanceof AppException ? ((AppException) e).code.code() : "SE0001");
            modelMap.put("msg", e instanceof AppException ? ((AppException) e).code.msg() : "系统异常");
            modelMap.put("data", null);
            return modelMap;
        }
    }

    /**
     * 接口日志, 开启配置： api.log.enable=true
     **/
    @Bean
    @ConditionalOnProperty(prefix = "api.log", name = "enable", havingValue = "true")
    public ApiOperationInterceptor apiOperationInterceptor() {
        return new ApiOperationInterceptor();
    }
}
