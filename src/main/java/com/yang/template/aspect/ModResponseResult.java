package com.yang.template.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ModResponseResult {

    @Pointcut(value = "execution(public * com.yang..activiti.web..*.*(..))")
    public void pointcut() {
    }

}
