package com.yang.template.controller.base;

import com.alibaba.fastjson.JSON;
import com.yang.template.exception.ServiceException;
import com.yang.template.model.common.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jjyy
 * @apiNote 全局异常处理
 * @since 2019-9-4
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * @param request  http req
     * @param response http rsp
     * @param e        业务异常
     * @apiNote 业务异常处理函数
     */
    @ExceptionHandler(value = {ServiceException.class})
    @ResponseBody
    public void serviceException(HttpServletRequest request, HttpServletResponse response, ServiceException e) {
        log.error(String.format("业务异常 url：%s，method：%s", request.getRequestURI(), request.getMethod()), e);
        AppResponse rep = AppResponse.builder()
                .code(e.getCode())
                .status(500)
                .msg(e.getLocalizedMessage())
                .build();
        exceptionProcess(response, e, rep);
    }

    /**
     * @param request  http req
     * @param response http rsp
     * @param e        验证异常
     * @apiNote 参数验证异常处理函数
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public void validException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        log.error(String.format("验证失败 url：%s，method：%s", request.getRequestURI(), request.getMethod()), e);
        AppResponse rep = AppResponse.builder()
                .code(1)
                .status(500)
                .msg(e.getBindingResult().getAllErrors())
                .build();
        exceptionProcess(response, e, rep);
    }

    /**
     * @param request  http req
     * @param response http rsp
     * @param t        其他异常
     * @apiNote 其他异常处理函数
     */
    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    public void throwException(HttpServletRequest request, HttpServletResponse response, Throwable t) {
        log.error(String.format("系统异常 url：%s，method：%s", request.getRequestURI(), request.getMethod()), t);
        AppResponse rep = AppResponse.builder()
                .msg("fail 系统错误")
                .code(1)
                .status(500)
                .build();
        exceptionProcess(response, t, rep);
    }

    /**
     * @param response http rsp
     * @param e        异常
     * @param rep      返回前端对象
     * @apiNote 将封装后的返回对象写到http rep中去
     */
    private void exceptionProcess(HttpServletResponse response, Throwable e, AppResponse rep) {
        String s = JSON.toJSONString(rep, false);
        try {
            response.setStatus(500);
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.getWriter().write(s);
            response.flushBuffer();
        } catch (IOException ex) {
            log.error("发送失败", e);
        }
    }

}
