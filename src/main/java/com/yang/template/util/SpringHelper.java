package com.yang.template.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author jjyy
 * @implNote 用于无法使用自动注入，而又需要获取spring容器中的对象
 * @since 2019/9/6
 */
@Component
public class SpringHelper implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringHelper.environment = environment;
    }

    /**
     * 获取容器中的bean通过类型
     *
     * @param clazz 获取bean类型
     * @param <T>   类型
     * @return 容器中的bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取容器中的bean通过名称
     *
     * @param name 名称
     * @param <T>  类型
     * @return 容器中的bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取配置文件的环境变量
     *
     * @param key key
     * @return value
     */
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    /**
     * 获取配置文件的环境变量
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    /**
     * 获取配置文件的环境变量
     *
     * @param key        key
     * @param targetType 目标类
     * @param <T>        类型
     * @return value
     */
    public static <T> T getProperty(String key, Class<T> targetType) {
        return environment.getProperty(key, targetType);
    }

    /**
     * 获取配置文件的环境变量
     *
     * @param key          key
     * @param targetType   目标类
     * @param defaultValue 默认值
     * @param <T>          类型
     * @return value
     */
    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return environment.getProperty(key, targetType, defaultValue);
    }

}
