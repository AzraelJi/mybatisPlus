package com.yang.template.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.yang.template.annotation.JsonParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Controller方法参数前使用@JsonParam注解可以自动封装RequestBody数据
 * 可自动封装对象
 * 使用方法:
 * public String getDemo(@JsonParam Demo demo,@JsonParam String p){
 * return "demo"
 * }
 *
 * @author wangzhu
 * @since 2019-09-05
 */
@Slf4j
public class JsonPathArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) {
        String body = getRequestBody(webRequest);
        Object val = null;
        try {
            String value = Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).value();
            if ("".equals(value)) {
                value = parameter.getParameterName();
            }
            val = JsonPath.read(body, value);
            if (Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).required() && val == null) {
                throw new PathNotFoundException(Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).value() + "不能为空");
            }
            if (val instanceof Map) {
                val = JSONObject.parseObject(JSON.toJSONString(val), parameter.getParameterType());
            }
        } catch (PathNotFoundException e) {
            if (Objects.requireNonNull(parameter.getParameterAnnotation(JsonParam.class)).required()) {
                HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                assert servletRequest != null;
                log.error(servletRequest.getMethod() + " 方式请求url: " + servletRequest.getRequestURI());
                log.error("body: " + body);
                log.error(parameter.getParameterName() + " 不能为空");
                throw e;
            }
        } catch (IllegalArgumentException e) {
            HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            assert servletRequest != null;
            log.error(servletRequest.getMethod() + " 方式请求url: " + servletRequest.getRequestURI());
            log.error("body: " + body);
            throw e;
        }
        return val;
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        assert servletRequest != null;
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getInputStream());
                servletRequest.setAttribute(JSON_REQUEST_BODY, jsonBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;
    }

}
