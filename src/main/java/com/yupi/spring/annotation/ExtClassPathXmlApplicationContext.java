package com.yupi.spring.annotation;

import com.github.lordrex34.reflection.util.ClassPathUtil;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述：手撕SpringIOC注解版本全局上下文
 *
 * @author Yupi Li
 * @date 2018/8/28 21:24
 */
public class ExtClassPathXmlApplicationContext {

    // 扫包范围（包名）
    private String basePackage;
    // 容器表
    private ConcurrentHashMap<String, Object> beans = null;

    public ExtClassPathXmlApplicationContext(String basePackage) throws IOException, InstantiationException, IllegalAccessException {
        this.beans = new ConcurrentHashMap<String, Object>();
        this.basePackage = basePackage;
        initBeans();
    }

    // 初始化Bean对象
    public void initBeans() throws IOException, IllegalAccessException, InstantiationException {
        // 扫包，获取指定包下的class
        FluentIterable<Class<?>> allClasses = ClassPathUtil.getAllClasses(basePackage);
        ImmutableList<Class<?>> classes = allClasses.toList();
        // 判断类上是否有注解
        ConcurrentHashMap<String, Object> beanMap = hasAnnotation(classes);
        if (beanMap == null || beanMap.isEmpty()) {
            throw new RuntimeException("该包下没有任何带有SpringBean注解的类");
        }
    }

    // 判断类上是否有注解，创建bean实例，存入map
    public ConcurrentHashMap<String, Object> hasAnnotation(List<Class<?>> classes) throws InstantiationException, IllegalAccessException {
        for (Class<?> classInfo : classes) {
            SpringBean annotation = classInfo.getAnnotation(SpringBean.class);
            if (annotation != null) {
                // 获取类名作为bean名称
                String className = StringUtils.uncapitalize(classInfo.getSimpleName());
                Object bean = newInstance(classInfo);
                beans.put(className, bean);
            }
        }
        return beans;
    }

    // 获取类的对象
    public Object getBean(String beanId) throws IOException, InstantiationException, IllegalAccessException {
        if (StringUtils.isEmpty(beanId)) {
            throw new RuntimeException("beanId不能为空");
        }
        Object bean = beans.get(beanId);
        if (bean == null) {
            throw new RuntimeException("没有找到该bean对象");
        }
        return bean;
    }

    // 初始化对象
    public Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    // 依赖注入注解
    public void dependencyInject(Object object) throws IllegalAccessException {
        // 反射，获取该类所有属性
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            SpringResource resource = field.getAnnotation(SpringResource.class);
            if (resource != null) {
                // 获取属性名称
                String fieldName = field.getName();
                Object bean = beans.get(fieldName);
                if (bean == null) {
                    throw new RuntimeException("未找到该bean对象");
                }
                // 允许访问私有属性
                field.setAccessible(true);
                // 设置属性（将新属性赋值给已有对象）
                field.set(object,bean);
            }
        }
    }

    //
}
