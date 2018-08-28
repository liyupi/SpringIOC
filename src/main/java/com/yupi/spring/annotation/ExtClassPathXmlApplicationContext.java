package com.yupi.spring.annotation;

import com.github.lordrex34.reflection.util.ClassPathUtil;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
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
    private ConcurrentHashMap<String,Class<?>> beans = null;

    public ExtClassPathXmlApplicationContext(String basePackage) throws IOException {
        this.beans = new ConcurrentHashMap<String, Class<?>>();
        this.basePackage = basePackage;
        initBeans();
    }

    // 初始化Bean对象
    public void initBeans() throws IOException {
        // 扫包，获取指定包下的class
        FluentIterable<Class<?>> allClasses = ClassPathUtil.getAllClasses(basePackage);
        ImmutableList<Class<?>> classes = allClasses.toList();
        // 判断类上是否有注解
        ConcurrentHashMap<String,Class<?>> beanMap = hasAnnotation(classes);
        if (beanMap == null || beanMap.isEmpty()) {
            throw  new RuntimeException("该包下没有任何带有SpringBean注解的类");
        }
    }

    // 判断类上是否有注解
    public ConcurrentHashMap<String, Class<?>> hasAnnotation(List<Class<?>> classes) {
        for (Class<?> classInfo : classes) {
            SpringBean annotation = classInfo.getAnnotation(SpringBean.class);
            if (annotation != null) {
                // 获取类名
                String className = StringUtils.uncapitalize(classInfo.getSimpleName());
                beans.put(className,classInfo);
            }
        }
        return beans;
    }

    // 获取类的对象
    public Object getBean(String beanId) throws IOException, InstantiationException, IllegalAccessException {
        if (StringUtils.isEmpty(beanId)){
            throw new RuntimeException("beanId不能为空");
        }
        Class<?> classInfo = beans.get(beanId);
        if (classInfo == null){
            throw new RuntimeException("class not found");
        }
        return newInstance(classInfo);
    }

    // 初始化对象
    public Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    public static void main(String[] args) {
        try {
            new ExtClassPathXmlApplicationContext("com.yupi.spring.annotation").initBeans();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
